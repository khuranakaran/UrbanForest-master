package com.customer.main.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.adapter.OrdersAdapter;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.response.VendorOrdersResponse.VendorOrdersResponse;
import com.customer.main.model.request.orders_request.OrdersRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan
 */
public class VendorOrdersActivity extends Fragment {

    private RecyclerView rvVendorOrders;

    private OrdersAdapter mAdapter;
    private View mProgressView;
    private TextView tvNoOrdersFound;

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
        tvNoOrdersFound = view.findViewById(R.id.tvNoOrdersFound);

        fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());
    }

    public void fetchOrders(String vendorId){
        mProgressView.setVisibility(View.VISIBLE);
        OrdersRequest orderRequest = new OrdersRequest();
        orderRequest.setVendorid(Integer.parseInt(vendorId));

        RetrofitClient.getClient().create(IUsersApi.class).vendorOrders(orderRequest).enqueue(new Callback<VendorOrdersResponse>() {
            @Override
            public void onResponse(Call<VendorOrdersResponse> call, Response<VendorOrdersResponse> response) {
                Log.e("KKK", "Success");
                mProgressView.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() == 1 ) {

                    if (response.body().getData() !=  null){
                        mAdapter = new OrdersAdapter(getActivity(), response.body());

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
            public void onFailure(Call<VendorOrdersResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
