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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karan on 2/5/19.
 */
public class MyOrdersFragments extends Fragment {

    private RecyclerView rvVendorOrders;

    private MyOrdersAdapter mAdapter;
    private View mProgressView;

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

        fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());
    }

    public void fetchOrders(String vendorId) {
        mProgressView.setVisibility(View.VISIBLE);
        GetMyOrders myOrders = new GetMyOrders();
        myOrders.setCustomerid(vendorId);

        RetrofitClient.getClient().create(IUsersApi.class).getMyOrders(myOrders).enqueue(new Callback<MyOrdersResponse>() {
            @Override
            public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {
                Log.e("KKK", "Success");
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
}

