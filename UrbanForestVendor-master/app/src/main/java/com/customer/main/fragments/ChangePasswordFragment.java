package com.customer.main.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.change_password_request.ChangePasswordRequest;
import com.customer.main.model.response.general_response.GeneralResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edtNewPassword, edtConfirmNewPassword, edtOldPassword;
    private Button btnChangePassword;
    private ProgressBar mProgressView;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtConfirmNewPassword = view.findViewById(R.id.edtConfirmNewPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        mProgressView = view.findViewById(R.id.mProgressView);

        btnChangePassword.setOnClickListener(view1 -> {
            String opw = edtOldPassword.getText().toString().trim();
            String pw = edtNewPassword.getText().toString().trim();
            String cpw = edtConfirmNewPassword.getText().toString().trim();

            if (TextUtils.isEmpty(opw)){
                Toast.makeText(getActivity(), "Old Password Can't be Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pw)){
                Toast.makeText(getActivity(), "New Password Can't be Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(cpw)){
                Toast.makeText(getActivity(), "Confirm Password Can't be Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pw.equals(cpw)){
                changePassword(pw, cpw);
            } else {
                Toast.makeText(getActivity(), "Confirm Password isn't same as password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword(String oldPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setMobile(UrbanForestPrefrences.getInstance(getActivity()).getPhone());
        changePasswordRequest.setOldpassword(oldPassword);
        changePasswordRequest.setNewpassword(newPassword);
        RetrofitClient.getClient().create(IUsersApi.class).changePassword(changePasswordRequest).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {

                if (response.body() != null && response.body().getStatus() == 1) {


                    mProgressView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                    Fragment fragment;

                    fragment = new CategoriesFragment();
                    //replacing the fragment
                    if (fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }

                } else {
                    if (response.body() != null) {
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {

                mProgressView.setVisibility(View.GONE);

                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
