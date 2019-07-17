package com.customer.main.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.get_my_orders.GetMyOrders;
import com.customer.main.model.response.my_cart_response.MyCartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karan on 22/6/19.
 */
public class MyCartFragment  extends Fragment implements View.OnClickListener {

    private RecyclerView rvVendorOrders;

    private MyCartAdapter mAdapter;
    private View mProgressView;

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

        fetchOrders(UrbanForestPrefrences.getInstance(getActivity()).getVid());

        view.findViewById(R.id.tvContinueShopping).setOnClickListener(this);
        view.findViewById(R.id.tvProceedToPayment).setOnClickListener(this);
    }

    public void fetchOrders(String vendorId) {
        mProgressView.setVisibility(View.VISIBLE);
        GetMyOrders myOrders = new GetMyOrders();
        myOrders.setCustomerid(vendorId);

        RetrofitClient.getClient().create(IUsersApi.class).getCart(myOrders).enqueue(new Callback<MyCartResponse>() {
            @Override
            public void onResponse(Call<MyCartResponse> call, Response<MyCartResponse> response) {
                Log.e("KKK", "Success");
                mProgressView.setVisibility(View.GONE);
                if (response.body().getStatus() == 1 ) {

                    if (response.body().getStatus()==  1){
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
        switch (v.getId()){
            case R.id.tvProceedToPayment:
                FragmentTransaction t = this.getFragmentManager().beginTransaction();
                Fragment mFrag = new CategoriesFragment();
                t.add(R.id.content_frame, mFrag);
                t.commit();
                break;
            case R.id.tvContinueShopping:
                FragmentTransaction t1 = this.getFragmentManager().beginTransaction();
                Fragment mFrag1 = new CategoriesFragment();
                t1.add(R.id.content_frame, mFrag1);
                t1.commit();
                break;
        }
    }
}

