package com.customer.main.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.adapter.MyOrdersAdapter;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.get_my_orders.GetMyOrders;
import com.customer.main.model.response.customer_my_orders.MyOrdersResponse;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karan on 2/5/19.
 */
public class MyOrdersFragments extends Fragment implements View.OnClickListener {

    private RecyclerView rvVendorOrders;

    private MyOrdersAdapter mAdapter;
    private View mProgressView;
    String  txnId, phone, firstName, email, productName, name;
    int amount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_vendor_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvVendorOrders = view.findViewById(R.id.rvVendorOrders);
        mProgressView = view.findViewById(R.id.mProgressView);

        view.findViewById(R.id.tvContinueShopping).setOnClickListener(this);
        view.findViewById(R.id.tvProceedToPayment).setOnClickListener(this);

        fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());
    }

    public void fetchOrders(String vendorId) {
        mProgressView.setVisibility(View.VISIBLE);
        GetMyOrders myOrders = new GetMyOrders();
        myOrders.setCustomerid(vendorId);

        RetrofitClient.getClient().create(IUsersApi.class).getMyOrders(myOrders).enqueue(new Callback<MyOrdersResponse>() {
            @Override
            public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {
                Log.e("KKK", response.body().getData() + "");
                mProgressView.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() == 1 ) {

                    if (response.body().getData() !=  null){
                        mAdapter = new MyOrdersAdapter(getActivity(), response.body());

                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvVendorOrders.getContext(),
                                mLayoutManager.getOrientation());
                        rvVendorOrders.addItemDecoration(dividerItemDecoration);
                        rvVendorOrders.setLayoutManager(mLayoutManager);
                        rvVendorOrders.setItemAnimator(new DefaultItemAnimator());
                        rvVendorOrders.setAdapter(mAdapter);

                        for (int i = 0; i < response.body().getData().size(); i ++){
                            amount = amount + Integer.parseInt(response.body().getData().get(i).getTotalamount());
                        }
                    } else {
                        Toast.makeText(getActivity(),
                                "No Orders available",
                                Toast.LENGTH_SHORT).show();
                    }



                } else {
                    if (response.body() != null) {
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                "Some error occurred fetching orders",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyOrdersResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvProceedToPayment:
                launchPayUMoneyFlow();
                break;
        }

    }

    private void launchPayUMoneyFlow() {

            String merchantKey = "*******";
            String merchantID = "*******";
            name = UrbanForestPrefrences.getInstance(getActivity()).getName();
            phone = UrbanForestPrefrences.getInstance(getActivity()).getPhone();
            txnId = UrbanForestPrefrences.getInstance(getActivity()).getPhone() + System.currentTimeMillis();
            email = "kk@gmail.com";

            PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

            payUmoneyConfig.setDoneButtonText("Continue");
            payUmoneyConfig.setPayUmoneyActivityTitle("Add Money");


            String productName = "Order Payment - " + UrbanForestPrefrences.getInstance(getActivity()).getPhone();

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(String.valueOf(amount))                          // Payment amount
                .setTxnId(txnId)                                             // Transaction ID
                .setPhone(phone)                                           // User Phone number
                .setProductName(productName)                   // Product Name or description
                .setFirstName(firstName)                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.google.com/")                    // Success URL (surl)
                .setfUrl("https://www.google.com/")                     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
//                .setUdf6(udf6)
//                .setUdf7(udf7)
//                .setUdf8(udf8)
//                .setUdf9(udf9)
//                .setUdf10(udf10)
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey("drCzfSLX")                        // Merchant key
                .setMerchantId("6767076");

        String hashSequence = "drCzfSLX" + "|" + txnId + "|" + amount +"|" + productName +"|" + name + "|"
                + email + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "||||||" +  "XYTcG1HvG";
        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam paymentParam = null;
        try {
            paymentParam = builder.build();
            paymentParam.setMerchantHash(serverCalculatedHash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, getActivity(), R.style.AppTheme_default, false );
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

}

