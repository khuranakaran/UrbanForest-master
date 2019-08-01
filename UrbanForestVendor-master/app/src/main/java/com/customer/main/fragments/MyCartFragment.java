package com.customer.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.get_my_orders.GetMyOrders;
import com.customer.main.model.response.my_cart_response.MyCartResponse;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karan on 22/6/19.
 */
public class MyCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvVendorOrders;

    private MyCartAdapter mAdapter;
    private View mProgressView;

    String txnId, phone, firstName, email, productName, name;
    int amount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvVendorOrders = view.findViewById(R.id.rvVendorOrders);
        mProgressView = view.findViewById(R.id.mProgressView);

        fetchCart(UrbanForestPrefrences.getInstance(getActivity()).getVid());

        view.findViewById(R.id.tvContinueShopping).setOnClickListener(this);
        view.findViewById(R.id.tvProceedToPayment).setOnClickListener(this);
    }

    public void fetchCart(String vendorId) {
        mProgressView.setVisibility(View.VISIBLE);
        GetMyOrders myOrders = new GetMyOrders();
        myOrders.setCustomerid(vendorId);

        RetrofitClient.getClient().create(IUsersApi.class).getCart(myOrders).enqueue(new Callback<MyCartResponse>() {
            @Override
            public void onResponse(Call<MyCartResponse> call, Response<MyCartResponse> response) {
                Log.e("KKK", "Success");
                mProgressView.setVisibility(View.GONE);
                if (response.body().getStatus() == 1) {

                    if (response.body().getStatus() == 1) {
                        mAdapter = new MyCartAdapter(getActivity(), response.body());

                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvVendorOrders.getContext(),
                                mLayoutManager.getOrientation());
                        rvVendorOrders.addItemDecoration(dividerItemDecoration);
                        rvVendorOrders.setLayoutManager(mLayoutManager);
                        rvVendorOrders.setItemAnimator(new DefaultItemAnimator());
                        rvVendorOrders.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(getActivity(),
                                "No Items are available in cart.",
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
            public void onFailure(Call<MyCartResponse> call, Throwable t) {
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
        switch (v.getId()) {
            case R.id.tvProceedToPayment:
                launchPayUMoneyFlow();
                break;
            case R.id.tvContinueShopping:
                FragmentTransaction t1 = this.getFragmentManager().beginTransaction();
                Fragment mFrag1 = new CategoriesFragment();
                t1.add(R.id.content_frame, mFrag1);
                t1.commit();
                break;
        }
    }

    private void launchPayUMoneyFlow() {

        name = UrbanForestPrefrences.getInstance(getActivity()).getName();
        phone = UrbanForestPrefrences.getInstance(getActivity()).getPhone();
        txnId = "123";
        email = "kk@gmail.com";

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e

        payUmoneyConfig.setDoneButtonText("Continue");
        payUmoneyConfig.setPayUmoneyActivityTitle("Add Money");


        String productName = "1";
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount("100")                          // Payment amount
                .setTxnId(txnId)                                             // Transaction ID
                .setPhone("8860914345")                                           // User Phone number
                .setProductName(productName)                   // Product Name or description
                .setFirstName("kk")                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.google.com/")                    // Success URL (surl)
                .setfUrl("https://www.google.com/")                     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey("rjQUPktU")                        // Merchant key
                .setMerchantId("6767076");


        String hashSequence = "rjQUPktU" + "|" + txnId + "|" + "100" + "|" + productName + "|" + "kk" + "|"
                + email  + "|||||||||||" + "e5iIg1jwi8";

        Log.e("KKK", hashSequence);
        String serverCalculatedHash = hashCal("SHA-512", hashSequence.trim());

        Log.e("KKK", "My Hash " + serverCalculatedHash);

        PayUmoneySdkInitializer.PaymentParam paymentParam;
        try {
            paymentParam = builder.build();
            paymentParam.setMerchantHash(serverCalculatedHash);

            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, getActivity(), R.style.AppTheme_default, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

