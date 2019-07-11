package com.customer.main.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.base.utils.ImageUtil;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.response.general_response.GeneralResponse;
import com.customer.main.model.request.profile_request.ProfileRequest;
import com.customer.main.model.response.profile_response.ProfileResponse;
import com.customer.main.model.request.profile_update_request.ProfileUpdateRequest;
import com.customer.main.utils.AppConstants;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * @author karan
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private CircleImageView user_profile_photo;
    private EditText user_profile_name, user_profile_location, user_profile_phone,user_profile_address;
    private Button btnUpdate;
    private ProgressBar mProgressView;
    private RelativeLayout rlProfileUpdate;
    private String base64String, s;
    private Bitmap bitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlProfileUpdate = view.findViewById(R.id.rlProfileUpdate);
        user_profile_photo = view.findViewById(R.id.user_profile_photo);
        user_profile_name = view.findViewById(R.id.user_profile_name);
        user_profile_location = view.findViewById(R.id.user_profile_location);
        user_profile_phone = view.findViewById(R.id.user_profile_phone);
        user_profile_address = view.findViewById(R.id.user_profile_address);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        mProgressView = view.findViewById(R.id.mProgressView);

        user_profile_photo.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        fetchProfile(UrbanForestPrefrences.getInstance(getActivity()).getPhone());
    }

    public void fetchProfile(String phone) {
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setMobile(phone);
        RetrofitClient.getClient().create(IUsersApi.class).profile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {

                if (response.body() != null && response.body().getStatus() == 1) {

                    rlProfileUpdate.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                    user_profile_name.setText(response.body().getData().getName());
                    user_profile_phone.setText(response.body().getData().getPhone());
                    user_profile_address.setText(response.body().getData().getAddress());
                    user_profile_location.setText(response.body().getData().getLocation());

                    Glide.with(getActivity()).load(AppConstants.IMAGE_BASE_URL + response.body().getData().getImage()).placeholder(R.drawable.profile).into(user_profile_photo);

                    rlProfileUpdate.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);

                    UrbanForestPrefrences.getInstance(getActivity()).setVid(response.body().getData().getVid());
                    UrbanForestPrefrences.getInstance(getActivity()).setPhone(response.body().getData().getPhone());

                } else {
                    if (response.body() != null) {
                        rlProfileUpdate.setVisibility(View.VISIBLE);
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                "Some error occurred fetching profile details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                rlProfileUpdate.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);

                Toast.makeText(getActivity(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateProfile() {

        mProgressView.setVisibility(View.VISIBLE);
        rlProfileUpdate.setVisibility(View.GONE);
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setMobile(user_profile_phone.getText().toString().trim());
        profileUpdateRequest.setName(user_profile_name.getText().toString().trim());
        profileUpdateRequest.setAddress(user_profile_address.getText().toString().trim());
        profileUpdateRequest.setLocation(user_profile_location.getText().toString().trim());
        profileUpdateRequest.setGender("Male");
        profileUpdateRequest.setImage(base64String);


        RetrofitClient.getClient().create(IUsersApi.class).updateProfile(profileUpdateRequest).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {

                if (response.body() != null && response.body().getStatus() == 1) {

                    rlProfileUpdate.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);

                    Toast.makeText(getActivity(),
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();



                } else {
                    if (response.body() != null) {
                        rlProfileUpdate.setVisibility(View.VISIBLE);
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                "Some error occurred updating profile details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {

                rlProfileUpdate.setVisibility(View.VISIBLE);
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
            case R.id.user_profile_photo:
                CropImage.activity()
                        .setOutputCompressQuality(40)
                        .setMinCropWindowSize(400, 600)
                        .start(getContext(), this);
                break;
            case R.id.btnUpdate:
                updateProfile();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri imageUri = result.getUri();


                bitmap = ImageUtil.getBitmap(getActivity(), imageUri);
                base64String = ImageUtil.convert(bitmap);

                Glide.with(getActivity()).load(ImageUtil.convert(base64String)).into(user_profile_photo);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
