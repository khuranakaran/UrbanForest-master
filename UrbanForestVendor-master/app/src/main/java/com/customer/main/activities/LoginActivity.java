package com.customer.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.base.ui.BaseActivity;
import com.customer.main.api.IUsersApi;
import com.customer.main.model.request.login_request.LoginRequest;
import com.customer.main.model.response.user_response.UserResponse;
import com.customer.main.utils.PrefrenceConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 * @author Karan
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {



    // UI references.
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvSignUpLink, tvForgotPassword;
    private String phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mPhoneView = findViewById(R.id.edtPhone);
        tvSignUpLink = findViewById(R.id.tvSignUpLink);
        mLoginFormView = findViewById(R.id.mLoginFormView);
        mProgressView = findViewById(R.id.mProgressView);

        mPasswordView = findViewById(R.id.edtPassword);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        tvSignUpLink.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        findViewById(R.id.tvForgotPassword).setOnClickListener(this);
        Button mEmailSignInButton = findViewById(R.id.btnSignIn);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid phone, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        mProgressView.setVisibility(View.VISIBLE);
        phone = mPhoneView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();

        if (validate()){
            login();
        }

    }

    private boolean validate() {

        if (TextUtils.isEmpty(phone)){
            showSnackBar(this, mPhoneView, getString(R.string.error_phone));
            return false;
        }

        if (TextUtils.isEmpty(password)  || password.length() < 4){
            showSnackBar(this, mPasswordView, getString(R.string.error_password));
            return false;
        }

        return true;
    }

    private void login() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobile(mPhoneView.getText().toString().trim());
        loginRequest.setPassword(mPasswordView.getText().toString().trim());
        loginRequest.setType("1");


        RetrofitClient.getClient().create(IUsersApi.class).login(loginRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try{
                    mProgressView.setVisibility(View.GONE);
                    if (response.body() != null && response.body().getStatus() == 1) {

                        Toast.makeText(LoginActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("phone", response.body().getData().getPhone()).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        UrbanForestPrefrences.getInstance(LoginActivity.this).setLoggedIn(true);
                        UrbanForestPrefrences.getInstance(LoginActivity.this).setString(PrefrenceConstants.PHONE, response.body().getData().getPhone());
                        UrbanForestPrefrences.getInstance(LoginActivity.this).setVid(response.body().getData().getVid());
                        UrbanForestPrefrences.getInstance(LoginActivity.this).setPhone(response.body().getData().getPhone());
                        UrbanForestPrefrences.getInstance(LoginActivity.this).setName(response.body().getData().getName());

                        finish();
                    }  else {
                        if (response .body() != null) {
                            Toast.makeText(LoginActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(String phone) {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(phone)){
            showSnackBar(this, mPhoneView, getString(R.string.error_phone));
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(password) || password.length() < 4){
            showSnackBar(this, mPasswordView, getString(R.string.error_password));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }
}

