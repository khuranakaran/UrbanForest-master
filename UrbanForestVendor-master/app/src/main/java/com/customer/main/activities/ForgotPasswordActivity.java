package com.customer.main.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.forgot_password_request.ForgotPasswordRequest;
import com.customer.main.model.response.general_response.GeneralResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNewPassword, edtOldPassword;
    private Button btnForgotPassword;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        mProgressView = findViewById(R.id.mProgressView);

        findViewById(R.id.btnForgotPassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnForgotPassword:

                String phone = edtOldPassword.getText().toString().trim();
                String email = edtNewPassword.getText().toString().trim();

                if (TextUtils.isEmpty(phone)){
                    edtOldPassword.setError(getString(R.string.error_phone));
                    break;
                }

                if (TextUtils.isEmpty(email)){
                    edtNewPassword.setError(getString(R.string.error_invalid_email));
                    break;
                }
                forgotPassword(phone, email);
        }
    }

    private void forgotPassword(String phone, String email) {
        mProgressView.setVisibility(View.VISIBLE);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setMobile(phone);
        forgotPasswordRequest.setEmail(email);
        RetrofitClient.getClient().create(IUsersApi.class).forgotPassword(forgotPasswordRequest).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {

                if (response.body() != null && response.body().getStatus() == 1) {


                    mProgressView.setVisibility(View.GONE);

                    Toast.makeText(ForgotPasswordActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                } else {
                    if (response.body() != null) {

                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Some error occurred fetching profile details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {


                mProgressView.setVisibility(View.GONE);

                Toast.makeText(ForgotPasswordActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
