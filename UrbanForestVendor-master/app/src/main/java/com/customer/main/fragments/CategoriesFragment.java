package com.customer.main.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.adapter.CategoriesAdapter;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.response.categories_response.CategoriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karan on 21/6/19.
 */
public class CategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCategories;
    private ProgressBar mProgressView;
    private RelativeLayout rlMenu;
    private CategoriesAdapter categoriesAdapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategories = view.findViewById(R.id.rvCategories);
        mProgressView = view.findViewById(R.id.mProgressView);
        rlMenu = view.findViewById(R.id.rlMenu);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCategories.setLayoutManager(mLayoutManager);
        rvCategories.setItemAnimator(new DefaultItemAnimator());


        fetchCategories();


    }

    private void fetchCategories() {

        mProgressView.setVisibility(View.VISIBLE);
        RetrofitClient.getClient().create(IUsersApi.class).getCategories().enqueue(new Callback<CategoriesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {


                if (response.body() != null && response.body().getStatus() == 1) {

                    mProgressView.setVisibility(View.GONE);
                    rlMenu.setVisibility(View.VISIBLE);
                    categoriesAdapter = new CategoriesAdapter(getActivity(), response.body().getData(), response.body().getUrl());

                    rvCategories.setAdapter(categoriesAdapter);
                } else {
                    if (response.body() != null) {
                        Toast.makeText(getActivity(),
                                "Some error occurred fetching order details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
