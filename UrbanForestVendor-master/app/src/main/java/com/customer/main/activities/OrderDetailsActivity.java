package com.customer.main.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.base.ui.BaseActivity;
import com.customer.main.adapter.OrderDetailsAdapter;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.response.OrderDetailsResponse.OrderDetailsResponse;
import com.customer.main.model.request.orders_request.OrdersRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private TextView tvOrderNumber, phone, name, address, amount;
    private Button btnAccept, btnDecline;
    private String orderId;
    private View mProgressView;
    private RecyclerView rvOrderDetails;
    private OrderDetailsAdapter orderDetailsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_order_details);
        phone = findViewById(R.id.phone);
        mProgressView = findViewById(R.id.mProgressView);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        amount = findViewById(R.id.amount);

        rvOrderDetails = findViewById(R.id.rvOrderDetails);
        phone.setOnClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvOrderDetails.setLayoutManager(mLayoutManager);
        rvOrderDetails.setItemAnimator(new DefaultItemAnimator());


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvOrderDetails.getContext(),
                mLayoutManager.getOrientation());
        rvOrderDetails.addItemDecoration(dividerItemDecoration);
        findViewById(R.id.btnDecline).setOnClickListener(this);
        findViewById(R.id.btnAccept).setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            orderId = getIntent().getStringExtra("orderid");
            fetchOrderDetails(orderId);
        }
    }

    public void fetchOrderDetails(String orderId) {
        mProgressView.setVisibility(View.VISIBLE);
        OrdersRequest orderRequest = new OrdersRequest();
        orderRequest.setOrderid(Integer.parseInt(orderId));

        RetrofitClient.getClient().create(IUsersApi.class).orderDetails(orderRequest).enqueue(new Callback<OrderDetailsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<OrderDetailsResponse> call, @NonNull Response<OrderDetailsResponse> response) {
                mProgressView.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() == 1) {

                    tvOrderNumber.setText("Order No : " + response.body().getOrder().get(0).getOid());
                    name.setText(response.body().getCustomerName());
//                    phone.setText(response.body().getOrder().get(0).getOmobile());
//                    address.setText(response.body().getOrder().get(0).getOaddress());
                    amount.setText(" ₹ " + response.body().getOrder().get(0).getTotalamount());

                    orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, response.body().getOrderDetails(), response.body().getOrder().get(0).getTotalamount());


                    rvOrderDetails.setAdapter(orderDetailsAdapter);
                } else {
                    if (response.body() != null) {
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(OrderDetailsActivity.this,
                                "Some error occurred fetching order details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(OrderDetailsActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void call(String phoneNumber) {
        int permissionCheck = ContextCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OrderDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone:
                if (phone.getText().toString().trim() != null) {
                    call(phone.getText().toString());
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

}

