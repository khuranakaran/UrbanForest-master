package com.customer.main.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.api.IUsersApi;
import com.customer.main.fragments.DatePickerFragment;
import com.customer.main.model.request.order_request.OrderRequest;
import com.customer.main.model.response.order_response.OrderResponse;
import com.customer.main.model.request.profile_request.ProfileRequest;
import com.customer.main.model.response.profile_response.ProfileResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan
 */
public class RevenueActivity extends Fragment
        implements View.OnClickListener {

    private OrderRequest orderRequest;

    private View navHeader;
    private TextView navHeaderName, navHeaderPhone, tvFromDate, tvToDate, tvTotalOrders, tvOrderCompleted, tvCash, tvCard, tvPaymentReceived, tvToDatePayment, tvFromDatePayment, tvPaymentPending;
    private ProgressBar mProgressView;
    private ConstraintLayout clDashboard;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int day;

    String vendorId;

    int cur = 0;

    static final int DATE_PICKER_TO = 1;
    static final int DATE_PICKER_FROM = 2;
    static final int PAYMENT_DATE_PICKER_TO = 3;
    static final int PAYMENT_DATE_PICKER_FROM = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file 
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_revenue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressView = view.findViewById(R.id.mProgressView);

        orderRequest = new OrderRequest();

        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        tvTotalOrders = view.findViewById(R.id.tvTotalOrders);
        tvOrderCompleted = view.findViewById(R.id.tvOrderCompleted);
        tvCash = view.findViewById(R.id.tvCash);
        tvCard = view.findViewById(R.id.tvCard);
        clDashboard = view.findViewById(R.id.clDashboard);

        tvFromDatePayment = view.findViewById(R.id.tvFromDatePayment);
        tvToDatePayment = view.findViewById(R.id.tvToDatePayment);
        tvPaymentReceived = view.findViewById(R.id.tvPaymentReceived);
        tvPaymentPending = view.findViewById(R.id.tvPaymentPending);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        tvFromDatePayment.setOnClickListener(this);
        tvToDatePayment.setOnClickListener(this);

        view.findViewById(R.id.tvFetchOrders).setOnClickListener(this);
        view.findViewById(R.id.tvFetchPaymentDetails).setOnClickListener(this);

        vendorId = UrbanForestPrefrences.getInstance(getActivity()).getVid();

        mProgressView.setVisibility(View.VISIBLE);

        fetchProfile(UrbanForestPrefrences.getInstance(getActivity()).getPhone());
    }

    public void fetchProfile(String phone) {
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setMobile(phone);
        RetrofitClient.getClient().create(IUsersApi.class).profile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.body() != null && response.body().getStatus() == 1) {
                    fetchOrders(response.body().getData().getVid());

                    UrbanForestPrefrences.getInstance(getActivity()).setVid(response.body().getData().getVid());

                } else {
                    if (response.body() != null) {
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                "Some error occurred fetching profile details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchOrders(String vid) {
        orderRequest.setFrom("");
        orderRequest.setTo("");
//        orderRequest.setVendorid(vid);
        orderRequest.setVendorid("9");

        mProgressView.setVisibility(View.VISIBLE);
        clDashboard.setVisibility(View.GONE);


        RetrofitClient.getClient().create(IUsersApi.class).orders(orderRequest).enqueue(new Callback<OrderResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {
                if (response.body() != null && response.body().getStatus() == 1) {
                    int totalRevenue = 0;
                    tvTotalOrders.setText("Total Orders   :   " + response.body().getTotalOrders());
                    tvOrderCompleted.setText("Orders Completed   :   " + response.body().getCompletedOrders());
                    tvCash.setText("Cash Orders  :   " + response.body().getCashOrders());
                    tvCard.setText("Card Orders  :  " + response.body().getCardOrders());

                    if (response.body().getData() != null){
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            totalRevenue = totalRevenue + Integer.parseInt(response.body().getData().get(i).getTotalamount());
                        }

                        tvPaymentReceived.setText("Total Revenue    :   " + totalRevenue);
                        tvPaymentReceived.setText("Payment Received    :   " + totalRevenue);
                    } /*else {
                        Toast.makeText(getActivity(),
                                "No revenue data found!!",
                                Toast.LENGTH_SHORT).show();
                    }*/

                    mProgressView.setVisibility(View.GONE);
                    clDashboard.setVisibility(View.VISIBLE);
                } else {
                    if (response.body() != null) {
                        Toast.makeText(getActivity(),
                                "Some error occurred fetching order details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvFromDate:
                cur = DATE_PICKER_FROM;
                showDatePicker(DATE_PICKER_FROM);
                break;
            case R.id.tvToDate:
                cur = DATE_PICKER_TO;
                showDatePicker(DATE_PICKER_TO);
                break;
            case R.id.tvFromDatePayment:
                cur = PAYMENT_DATE_PICKER_FROM;
                showDatePicker(PAYMENT_DATE_PICKER_FROM);
                break;
            case R.id.tvToDatePayment:
                cur = PAYMENT_DATE_PICKER_TO;
                showDatePicker(PAYMENT_DATE_PICKER_TO);
                break;
            case R.id.tvFetchOrders:
                orderRequest.setFrom(tvFromDate.getText().toString().trim());
                orderRequest.setTo(tvToDate.getText().toString().trim());
                fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());
                break;
            case R.id.tvFetchPaymentDetails:
                orderRequest.setFrom(tvFromDate.getText().toString().trim());
                orderRequest.setTo(tvToDate.getText().toString().trim());
                fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());
                break;
        }
    }


    private String getMonthName(int i) {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat monthDate = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH, i);

        return monthDate.format(cal.getTime());
    }


    private void showDatePicker(int datePickerId) {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            if (cur == DATE_PICKER_TO) {
                tvToDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(year));
            } else if (cur == DATE_PICKER_FROM) {
                tvFromDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(year));
            } else if (cur == PAYMENT_DATE_PICKER_FROM) {
                tvFromDatePayment.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(year));
            } else if (cur == PAYMENT_DATE_PICKER_TO) {
                tvToDatePayment.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(year));
            }
        }
    };
}
