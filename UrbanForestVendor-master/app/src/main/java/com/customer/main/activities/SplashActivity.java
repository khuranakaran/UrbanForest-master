package com.customer.main.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;

/**
 * @author Karan
 */
public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 10;
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

//        Objects.requireNonNull(getSupportActionBar()).hide();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               if (UrbanForestPrefrences.getInstance(SplashActivity.this).isLoggedIn()){
                   Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                   startActivity(mainIntent);
                   finish();
               } else {
                   Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                   startActivity(mainIntent);
                   finish();
               }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkPermission() {
        int result = ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermission();
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SplashActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}
