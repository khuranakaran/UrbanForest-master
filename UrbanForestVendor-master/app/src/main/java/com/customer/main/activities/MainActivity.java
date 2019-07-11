package com.customer.main.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.customer.R;
import com.customer.base.preference.UrbanForestPrefrences;
import com.customer.base.retrofit.RetrofitClient;
import com.customer.main.api.IUsersApi;
import com.customer.main.fragments.CategoriesFragment;
import com.customer.main.fragments.ChangePasswordFragment;
import com.customer.main.fragments.ContactUsFragment;
import com.customer.main.fragments.MyCartFragment;
import com.customer.main.fragments.MyOrdersFragments;
import com.customer.main.fragments.ProfileFragment;
import com.customer.main.model.request.profile_request.ProfileRequest;
import com.customer.main.model.response.profile_response.ProfileResponse;
import com.customer.main.utils.AppConstants;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author karan
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    private View navHeader;
    private ImageView nav_header_image;
    private TextView navHeaderName, navHeaderPhone;
    Fragment fragment;
    private ProgressBar mProgressViewHeader;
    private LinearLayout llHeader;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        navHeader = navigationView.getHeaderView(0);
        mProgressViewHeader = navHeader.findViewById(R.id.mProgressViewHeader);
        llHeader = navHeader.findViewById(R.id.llHeader);
        nav_header_image = navHeader.findViewById(R.id.nav_header_image);
        navHeaderName = navHeader.findViewById(R.id.navHeaderName);
        navHeaderPhone = navHeader.findViewById(R.id.navHeaderPhone);

        fragment = new CategoriesFragment();
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        navHeader.setOnClickListener(view -> {

            fragment = new ProfileFragment();
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }

            drawer.closeDrawer(GravityCompat.START);
        });


        fetchProfile(UrbanForestPrefrences.getInstance(MainActivity.this).getPhone());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            if (fragment != new CategoriesFragment()) {
                fragment = new CategoriesFragment();
                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            }
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_dashboard:
                fragment = new CategoriesFragment();
                break;
            case R.id.nav_contact_us:
                fragment = new ContactUsFragment();
                break;
            case R.id.nav_change_password:
                fragment = new ChangePasswordFragment();
                break;
            case R.id.nav_logout:
                UrbanForestPrefrences.getInstance(this).logOut(this);
                finish();
                break;
            case R.id.nav_my_orders:
                fragment = new MyOrdersFragments();
                break;
            case R.id.nav_my_cart:
                fragment = new MyCartFragment();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void fetchProfile(String phone) {

        mProgressViewHeader.setVisibility(View.VISIBLE);
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setMobile(phone);
        RetrofitClient.getClient().create(IUsersApi.class).profile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                mProgressViewHeader.setVisibility(View.GONE);
                llHeader.setVisibility(View.VISIBLE);
                if (response.body() != null && response.body().getStatus() == 1) {
                    navHeaderName.setText(response.body().getData().getName());
                    navHeaderPhone.setText(response.body().getData().getPhone());

                    Glide.with(MainActivity.this).load(AppConstants.IMAGE_BASE_URL + response.body().getData().getImage()).placeholder(R.drawable.profile).into(nav_header_image);
                    UrbanForestPrefrences.getInstance(MainActivity.this).setVid(response.body().getData().getVid());
                    UrbanForestPrefrences.getInstance(MainActivity.this).setPhone(response.body().getData().getPhone());

                } else {

                    if (response.body() != null) {
                        Toast.makeText(MainActivity.this,
                                "Some error occurred fetching profile details",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                Log.e("KKK", "FAIL");
                mProgressViewHeader.setVisibility(View.GONE);
                llHeader.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    /**
     * Called when a drawer has settled in a completely open state.
     */
    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

        fetchProfile(UrbanForestPrefrences.getInstance(this).getPhone());
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        fetchProfile(UrbanForestPrefrences.getInstance(MainActivity.this).getPhone());
    }
}
