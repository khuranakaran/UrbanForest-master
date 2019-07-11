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
import com.customer.main.adapter.MyProductsAdapter;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.response.my_products_request.MyProductsRequest;
import com.customer.main.model.response.my_products_response.MyProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Karan
 * @date 2/5/19
 */
public class MyProductsFragment extends Fragment {

    private RecyclerView rvMyProducts;

    private MyProductsAdapter mAdapter;
    private View mProgressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_my_products, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvMyProducts = view.findViewById(R.id.rvMyProducts);
        mProgressView = view.findViewById(R.id.mProgressView);

        fetchMyCart(UrbanForestPrefrences.getInstance(getActivity()).getVid());
    }

    public void fetchMyCart(String vendorId){
        mProgressView.setVisibility(View.VISIBLE);
        MyProductsRequest myProductsRequest = new MyProductsRequest();
        myProductsRequest.setVendorid(vendorId);

        RetrofitClient.getClient().create(IUsersApi.class).myProducts(myProductsRequest).enqueue(new Callback<MyProductsResponse>() {
            @Override
            public void onResponse(Call<MyProductsResponse> call, Response<MyProductsResponse> response) {
                Log.e("KKK", "Success");
                mProgressView.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() == 1) {

                   if(response.body().getData() != null){
                       mAdapter = new MyProductsAdapter(getActivity(), response.body().getData(), response.body().getUrl());

                       LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                       DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMyProducts.getContext(),
                               mLayoutManager.getOrientation());
                       rvMyProducts.addItemDecoration(dividerItemDecoration);
                       rvMyProducts.setLayoutManager(mLayoutManager);
                       rvMyProducts.setItemAnimator(new DefaultItemAnimator());
                       rvMyProducts.setAdapter(mAdapter);
                   }
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
            public void onFailure(Call<MyProductsResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

