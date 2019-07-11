package com.customer.main.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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
import com.customer.main.model.request.order_request.OrderRequest;
import com.customer.main.model.request.profile_request.ProfileRequest;
import com.customer.main.model.response.order_response.OrderResponse;
import com.customer.main.model.response.profile_response.ProfileResponse;
import com.customer.main.model.response.user_response.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan khurana
 */
public class DashboardFragment extends Fragment
        implements View.OnClickListener {

    private Bundle bundle;
    private Data data;
    private OrderRequest orderRequest;

    private String name, address, vid, location, phone, image, gender, type;
    private View navHeader;
    private TextView navHeaderName, navHeaderPhone, tvFromDate, tvToDate, tvTotalOrders, tvOrderCompleted, tvCash, tvCard;
    private ProgressBar mProgressView;
    private ConstraintLayout clDashboard;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int day;

    int cur = 0;

    static final int DATE_PICKER_TO = 1;
    static final int DATE_PICKER_FROM = 2;
    DatePickerDialog.OnDateSetListener fromDatelistener, toDatelistener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        return inflater.inflate(R.layout.content_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);

        view.findViewById(R.id.tvFetchOrders).setOnClickListener(this);

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
        orderRequest.setVendorid("9");

        mProgressView.setVisibility(View.VISIBLE);
        clDashboard.setVisibility(View.GONE);

        RetrofitClient.getClient().create(IUsersApi.class).orders(orderRequest).enqueue(new Callback<OrderResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {


                if (response.body() != null && response.body().getStatus() == 1) {


                    tvTotalOrders.setText("Total Orders   :   " + response.body().getTotalOrders());
                    tvOrderCompleted.setText("Orders Completed   :   " + response.body().getCompletedOrders());
                    tvCash.setText("Cash Orders  :   " + response.body().getCashOrders());
                    tvCard.setText("Card Orders  :  " + response.body().getCardOrders());

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
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
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
//                getActivity().showDialog(DATE_PICKER_TO);


                break;
            case R.id.tvToDate:
                showDatePicker(DATE_PICKER_TO);
                cur = DATE_PICKER_TO;

//                getActivity().showDialog(DATE_PICKER_FROM);
                break;
            case R.id.tvFetchOrders:
                orderRequest.setFrom(tvFromDate.getText().toString().trim());
                orderRequest.setTo(tvToDate.getText().toString().trim());
                fetchOrders("9");
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_PICKER_TO:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_PICKER_TO;
                // set date picker as current date
                return new DatePickerDialog(getActivity(), datePickerListener, year, month,
                        day);
            case DATE_PICKER_FROM:
                cur = DATE_PICKER_FROM;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(getActivity(), datePickerListener, year, month,
                        day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if (cur == DATE_PICKER_TO) {
                // set selected date into textview
                tvFromDate.setText(new StringBuilder().append(getMonthName(month))
                        .append("-").append(day).append("-").append(year)
                        .append(" "));
            } else {
                tvToDate.setText(new StringBuilder().append(getMonthName(month))
                        .append("-").append(day).append("-").append(year)
                        .append(" "));
            }

        }
    };


    private String getMonthName(int i){
        Calendar cal=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat monthDate = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH,i);

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

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            if (cur == DATE_PICKER_TO) {
                tvToDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                        + "-" + String.valueOf(year));
            } else {
                tvFromDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                        + "-" + String.valueOf(year));
            }
        }
    };
}
