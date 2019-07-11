package com.customer.main.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balsikandar.crashreporter.CrashReporter;
import com.theartofdev.edmodo.cropper.CropImage;
import com.customer.R;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.base.ui.BaseActivity;
import com.customer.base.utils.ImageUtil;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.signup_request.Document;
import com.customer.main.model.request.signup_request.SignupRequest;
import com.customer.main.model.response.user_response.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Karan
 */
public class SignupActivity extends BaseActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 11;
    private SignupRequest signupRequest;
    private List<Document> documentList;
    private Document document1, document2;
    private EditText edtName, edtAddress, edtPhone, edtPassword, edtConfirmPassword;
    private RadioGroup rgGender;
    private TextView tvDocumentName, tvSelectDocument;
    private Button btnRegister;
    private ProgressBar mProgressView;
    private String base64String, s;
    private Bitmap bitmap;

    String name, add, phone, password, confirmPassword, gender, documentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setUpViews();

        document1 = new Document();

        signupRequest = new SignupRequest();
        documentList = new ArrayList<>();

        try {
            // Do your stuff
        } catch (Exception e) {
            CrashReporter.logException(e);
        }

    }

    private void register() {


        document1.setDdata(getBase64String());
        document1.setDname("Document-" + phone);

        documentList.add(document1);

        signupRequest.setName(name);
        signupRequest.setGender(gender);
        signupRequest.setPassword(password);
        signupRequest.setAddress(add);
        signupRequest.setImage("");
        signupRequest.setMobile(phone);
        signupRequest.setType(1);
        signupRequest.setDocuments(documentList);
        signupRequest.setLocation("");


        RetrofitClient.getClient().create(IUsersApi.class).signUp(signupRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                mProgressView.setVisibility(View.GONE);
                Log.e("KKK", "Success");
                if (response.body() != null && response.body().getStatus() == 1) {

                    Toast.makeText(SignupActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                    finish();
                } else {
                    if (response.body() != null) {
                        Toast.makeText(SignupActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(SignupActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void setUpViews() {
        mProgressView = findViewById(R.id.mProgressView);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        tvDocumentName = findViewById(R.id.tvDocumentName);
        tvSelectDocument = findViewById(R.id.tvSelectDocument);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        tvSelectDocument.setOnClickListener(this);

        findViewById(R.id.tvSignUpLink).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                signUp();
                break;
            case R.id.tvSignUpLink:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
            case R.id.tvSelectDocument:
                if (checkPermission()) {
                    CropImage.activity()
                            .setOutputCompressQuality(40)
                            .setMinCropWindowSize(400, 600)
                            .start(SignupActivity.this);
                } else {
                    requestPermission();
                }

                break;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "SignupActivity{" +
                "name='" + name + '\'' +
                ", add='" + add + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", gender='" + gender + '\'' +
                ", documentName='" + documentName + '\'' +
                '}';
    }

    private void signUp() {
        name = edtName.getText().toString().trim();
        add = edtAddress.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        confirmPassword = edtConfirmPassword.getText().toString().trim();
//        gender = getGender(rgGender.getCheckedRadioButtonId());
        documentName = tvDocumentName.getText().toString().trim();
        gender = "Male";

        if (validate()) {
            register();
        }
        Log.e("Data : ", toString());
    }

    private String getGender(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case 1:
                return "Male";
            case 2:
                return "Female";
            case 3:
                return "Other";

        }
        return "";
    }

    private boolean validate() {
        if (TextUtils.isEmpty(name)) {
            /*showSnackBar(this, edtName, getString(R.string.error_name));*/
            edtName.setError(getString(R.string.error_name));
            return false;
        }
        if (TextUtils.isEmpty(add)) {
            /*showSnackBar(this, edtAddress, getString(R.string.error_add));*/
            edtAddress.setError(getString(R.string.error_add));
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            /*showSnackBar(this, edtPhone, getString(R.string.error_phone));*/
            edtPhone.setError(getString(R.string.error_phone));
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4) {
            /*showSnackBar(this, edtPassword, getString(R.string.error_password));*/
            edtPassword.setError(getString(R.string.error_password));
            return false;
        }

        if (!password.equals(confirmPassword)) {
            /*showSnackBar(this, edtPassword, getString(R.string.error_incorrect_password));*/
            edtConfirmPassword.setError(getString(R.string.error_incorrect_password));
            return false;
        }

//        if (TextUtils.isEmpty(gender)){
//            showSnackBar(this, edtPassword, getString(R.string.error_gender));
//            return false;
//        }

        mProgressView.setVisibility(View.VISIBLE);

        return true;
    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    Uri pickedImage = data.getData();
//                    // Let's read picked image path using content resolver
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
//                    cursor.moveToFirst();
//
//                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
//
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//
//                    int size = bitmap.getRowBytes() * bitmap.getHeight();
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//                    bitmap.copyPixelsToBuffer(byteBuffer);
//                    byte[] byteArray = byteBuffer.array();
//
//                    tvDocumentName.setText("1 Document Selected");
////                    document1.setDdata(Arrays.toString(byteArray));
//
//                    tvDocumentName.setVisibility(View.VISIBLE);
//                    Log.e("BYTE : ", Arrays.toString(byteArray));
//
//                    cursor.close();
//                }
//
//                break;
//            case 0:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = data.getData();
////                    imageview.setImageURI(selectedImage);
//                }
//                break;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri imageUri = result.getUri();


                bitmap = ImageUtil.getBitmap(SignupActivity.this, imageUri);
//                base64String = ImageUtil.convert(ImageUtil.getBitmap(SignupActivity.this, ));

                document1.setDdata(base64String);
                document1.setDname("Document");

//                Glide.with(getActivity()).load(ImageUtil.convert(base64String)).into(user_profile_photo);

                tvDocumentName.setText("1 Document Selected");
                tvDocumentName.setVisibility(View.VISIBLE);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SignupActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermission();
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SignupActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SignupActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


    private String getBase64String() {
        return "data:image/png;base64,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";
    }
}
