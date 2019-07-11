package com.customer.main.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.base.utils.ImageUtil;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.delete_my_product_request.DeleteMyProductRequest;
import com.customer.main.model.response.general_response.GeneralResponse;
import com.customer.main.model.response.my_products_response.Datum;
import com.customer.main.model.request.update_my_product_request.UpdateMyProductRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan
 */
public class MyProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView productImage;
    private EditText edtProductName, edtProductPrice, edtProductSize, edtDiscountPrice, edtProductPotCategory, edtProductPotSize, edtProductPotPrice, edtProductDescription;
    private LinearLayout llAddImage;
    private String base64String;
    private Bitmap bitmap;
    private UpdateMyProductRequest updateMyProductRequest;
    private ProgressBar mProgressView;
    private CardView cvAddProduct;
    Datum data;
    private String url, productId;

    URL imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        cvAddProduct = findViewById(R.id.cvAddProduct);
        productImage = findViewById(R.id.productImage);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductSize = findViewById(R.id.edtProductSize);
        edtDiscountPrice = findViewById(R.id.edtDiscountPrice);
        edtProductPotCategory = findViewById(R.id.edtProductPotCategory);
        edtProductPotSize = findViewById(R.id.edtProductPotSize);
        edtProductPotPrice = findViewById(R.id.edtProductPotPrice);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        mProgressView = findViewById(R.id.mProgressView);
        llAddImage = findViewById(R.id.llAddImage);
        productImage.setOnClickListener(this);

        findViewById(R.id.btnUpdateProduct).setOnClickListener(this);
        findViewById(R.id.btnDeleteProduct).setOnClickListener(this);

        if (getIntent() != null){
            data = getIntent().getExtras().getParcelable("data");
            url = getIntent().getStringExtra("url");

            productId = data.getPid();

            Log.e("KKKK", data.getPsize());

            Glide.with(this).load(url + data.getPimage()).into(productImage);
            edtProductName.setText((data.getPname() != null ) ? data.getPname() : "");

            edtProductPrice.setText((data.getPrice() != null || !data.getPrice().equals("") || !data.getPrice().equals("0")) ? data.getPrice() : "");

            edtProductSize.setText((data.getSize() != null ) ? data.getSize() : "");

            edtDiscountPrice.setText((data.getDprice() != null || !data.getDprice().equals("") || !data.getDprice().equals("0")) ? data.getDprice() : "");

            try {
                imageUrl = new URL(url + data.getPimage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            edtProductDescription.setText((data.getPdescription() != null ) ? data.getPdescription() : "");

            llAddImage.setVisibility(View.GONE);

            cvAddProduct.setVisibility(View.VISIBLE);
            mProgressView.setVisibility(View.GONE);
        }
    }

    private void addDetailsToModel(String productId, Bitmap bitmap) {


        if (validate()) {
            mProgressView.setVisibility(View.VISIBLE);
            cvAddProduct.setVisibility(View.GONE);
            updateMyProductRequest = new UpdateMyProductRequest();
            updateMyProductRequest.setProductId(productId);
            updateMyProductRequest.setProductname(edtProductName.getText().toString().trim());
            updateMyProductRequest.setProductprice(edtProductPrice.getText().toString().trim());
            updateMyProductRequest.setProductsize(edtProductSize.getText().toString().trim());
            updateMyProductRequest.setProductdiscountprice(edtDiscountPrice.getText().toString().trim());
            updateMyProductRequest.setCategory(edtProductPotCategory.getText().toString().trim());
            updateMyProductRequest.setPotsize(edtProductPotSize.getText().toString().trim());
            updateMyProductRequest.setPotprice(edtProductPotPrice.getText().toString().trim());
            updateMyProductRequest.setDescription(edtProductDescription.getText().toString().trim());
            updateMyProductRequest.setSubcategory("");
            updateMyProductRequest.setPotcategory("");
            updateMyProductRequest.setVendor(UrbanForestPrefrences.getInstance(this).getVid());
            if (base64String == null) {
                updateMyProductRequest.setImage(ImageUtil.convert(bitmap));
            } else {
                updateMyProductRequest.setImage(base64String);
            }

            bitmap.recycle();
            updateMyProductDetails(updateMyProductRequest);
        }
    }

    private void updateMyProductDetails(UpdateMyProductRequest updateMyProductRequest) {

        RetrofitClient.getClient().create(IUsersApi.class).updateMyProduct(updateMyProductRequest).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                mProgressView.setVisibility(View.GONE);
                cvAddProduct.setVisibility(View.VISIBLE);

                if (response.body() != null && response.body().getStatus() == 1) {
                    Toast.makeText(MyProductDetailsActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MyProductDetailsActivity.this, MainActivity.class));

                    finish();

                } else {
                    if (response.body() != null) {
                        Toast.makeText(MyProductDetailsActivity.this,
                                "Some error occurred deleting product",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {

                mProgressView.setVisibility(View.GONE);
                cvAddProduct.setVisibility(View.VISIBLE);
                Log.e("KKK", "FAIL");
                Toast.makeText(MyProductDetailsActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMyProduct(String productId) {
        DeleteMyProductRequest deleteMyProductRequest = new DeleteMyProductRequest();
        deleteMyProductRequest.setProductId(productId);

        RetrofitClient.getClient().create(IUsersApi.class).deleteMyProduct(deleteMyProductRequest).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                mProgressView.setVisibility(View.GONE);
                cvAddProduct.setVisibility(View.VISIBLE);

                if (response.body() != null && response.body().getStatus() == 1) {
                    Toast.makeText(MyProductDetailsActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MyProductDetailsActivity.this, MainActivity.class));
                    finish();

                } else {
                    if (response.body() != null) {
                        Toast.makeText(MyProductDetailsActivity.this,
                                "Some error occurred updating product details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {

                mProgressView.setVisibility(View.GONE);
                cvAddProduct.setVisibility(View.VISIBLE);
                Log.e("KKK", "FAIL");
                Toast.makeText(MyProductDetailsActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkIfEmpty(String s) {
        return TextUtils.isEmpty(s);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri imageUri = result.getUri();

                Glide.with(this).load(imageUri).into(productImage);

                bitmap = ImageUtil.getBitmap(this, imageUri);
                base64String = ImageUtil.convert(bitmap);

                llAddImage.setVisibility(View.GONE);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean validate() {
        if (TextUtils.isEmpty(edtProductName.getText().toString().trim())) {
            edtProductName.setError("Name can't be empty");
            return false;
        }

        if (TextUtils.isEmpty(edtProductPrice.getText().toString().trim())) {
            edtProductPrice.setError("Price can't be empty");
            return false;
        }

        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "Please select a product image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateProduct:
                addDetailsToModel(productId, bitmap);
                break;
            case R.id.btnDeleteProduct:
                deleteMyProduct(productId);
                break;
            case R.id.productImage:
                CropImage.activity()
                        .setOutputCompressQuality(40)
                        .setMinCropWindowSize(400, 600)
                        .start(this);
                break;
        }
    }
}
