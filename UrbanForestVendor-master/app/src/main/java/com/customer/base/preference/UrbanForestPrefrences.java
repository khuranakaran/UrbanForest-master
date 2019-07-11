package com.customer.base.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.customer.main.activities.LoginActivity;
import com.customer.main.activities.MainActivity;
import com.customer.main.model.response.user_response.Data;
import com.customer.main.utils.PrefrenceConstants;

public class UrbanForestPrefrences {

    private static UrbanForestPrefrences urbanForestPrefrences;
    private SharedPreferences sharedPreferences;
    private Context mContext;
    private String name, address, vid, location, phone, image, gender, type;


    private UrbanForestPrefrences() {
        // Do nothing
    }

    private UrbanForestPrefrences(Context context) {
        mContext = context.getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
    }

    public static UrbanForestPrefrences getInstance(Context context) {
        if (urbanForestPrefrences == null) {
            urbanForestPrefrences = new UrbanForestPrefrences(context);
        }
        return urbanForestPrefrences;
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setLong(String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void saveData(Data data){
        sharedPreferences.edit().putString(PrefrenceConstants.USER_NAME, data.getName()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.ADDRESS, data.getAddress()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.LOCATION, data.getLocation()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.PHONE, data.getPhone()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.TYPE, data.getType()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.IMAGE, data.getImage()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.GENDER, data.getGender()).apply();
        sharedPreferences.edit().putString(PrefrenceConstants.VID, data.getVid()).apply();
    }

    public void removeKey(String[] keyList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : keyList) {
            editor.remove(key);
        }
        editor.apply();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }


    public void logOut(Context context) {
        String[] prefsList = {
                PrefrenceConstants.MOBILE_NO,
                PrefrenceConstants.USER_NAME,
                PrefrenceConstants.EMAIL,
                PrefrenceConstants.ACCESS_TOKEN,
                PrefrenceConstants.PHONE,
                PrefrenceConstants.ADDRESS,
                PrefrenceConstants.LOCATION,
                PrefrenceConstants.GENDER,
                PrefrenceConstants.IMAGE,
                PrefrenceConstants.TYPE,
                PrefrenceConstants.IS_LOGGED_IN
        };

        removeKey(prefsList);

        // user is not logged in redirect him to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities from stack
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

        clearAllPref();

    }


    public void clearAllPref() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setName(String name) {
        sharedPreferences.edit().putString(PrefrenceConstants.USER_NAME, name).apply();
    }

    public void setAddress(String address) {
        sharedPreferences.edit().putString(PrefrenceConstants.ADDRESS, address).apply();
    }

    public void setVid(String vid) {
        sharedPreferences.edit().putString(PrefrenceConstants.VID, vid).apply();
    }

    public void setLocation(String location) {
        sharedPreferences.edit().putString(PrefrenceConstants.LOCATION, location).apply();
    }

    public void setPhone(String phone) {
        sharedPreferences.edit().putString(PrefrenceConstants.PHONE, phone).apply();
    }

    public void setImage(String image) {
        sharedPreferences.edit().putString(PrefrenceConstants.IMAGE, image).apply();
    }

    public void setGender(String gender) {
        sharedPreferences.edit().putString(PrefrenceConstants.GENDER, gender).apply();
    }

    public void setType(String type) {
        sharedPreferences.edit().putString(PrefrenceConstants.TYPE, type).apply();
    }

    public String getName() {
        return sharedPreferences.getString(PrefrenceConstants.USER_NAME, "");
    }

    public String getAddress() {
        return sharedPreferences.getString(PrefrenceConstants.ADDRESS, "");
    }


    public String getVid() {
        return sharedPreferences.getString(PrefrenceConstants.VID, "");
    }

    public String getLocation() {
        return sharedPreferences.getString(PrefrenceConstants.LOCATION, "");
    }

    public String getPhone() {
        return sharedPreferences.getString(PrefrenceConstants.PHONE, "");
    }

    public String getImage() {
        return sharedPreferences.getString(PrefrenceConstants.IMAGE, "");
    }

    public String getGender() {
        return sharedPreferences.getString(PrefrenceConstants.GENDER, "");
    }

    public String getType() {
        return sharedPreferences.getString(PrefrenceConstants.TYPE, "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PrefrenceConstants.IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(PrefrenceConstants.IS_LOGGED_IN, true).apply();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(Context context){
        // Check login status
        if(isLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, MainActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }
}
