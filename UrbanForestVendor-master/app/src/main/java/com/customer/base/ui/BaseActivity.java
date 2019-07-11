package com.customer.base.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.customer.R;
import com.customer.base.utils.LoggerUtil;
import com.customer.base.utils.PermissionUtil;

import java.text.DecimalFormat;

public class BaseActivity extends AppCompatActivity  {

    protected String screenName;
    private long startTimeStamp;
    private PopupWindow popupWindow;
    private boolean isProgressAdded;
    private ProgressDialog progressDialog;
    public ImageView searchButton;
    public AutoCompleteTextView giftCardAutoComplete;
//    private OperatorInfoDialog dialog;
    private boolean isWalletBalanceAvailable = false;
    public boolean visiblePayLater=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        ThemeUtil.applyTheme(this);
        super.onCreate(savedInstanceState);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            //inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

//    public String getUserFullName() {
//        String name = "";
//        name = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.NAME) + " " + UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.LAST_NAME);
//        return name;
//    }

    public String parseAmount(String amount) {
        try {
            return String.valueOf(Integer.parseInt(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    public void replaceFragment(Fragment oldFragment, Fragment newFragment, Bundle bundle, boolean isAddToBackStack) {

        String tag = newFragment.getClass().getSimpleName();
        if (bundle != null) {
            newFragment.setArguments(bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // ft.setCustomAnimations(android.R.anim.slide_in_left,
        // android.R.anim.slide_out_right);

//        ft.replace(R.id.container, newFragment, tag);
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
//        ft.add(R.id.container, newFragment, tag);
        newFragment.setRetainInstance(true);
        if (isAddToBackStack) {
            ft.addToBackStack(tag);
        }
        try {
            ft.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
//            ft.commitAllowingStateLoss();
        }
    }

//    public Fragment getCurrentFragment() {
//        return getSupportFragmentManager().findFragmentById(R.id.container);
//    }

//    public synchronized void showProgressDialog() {
//        if (oxigenProgressDialog == null) {
//            oxigenProgressDialog = OxigenProgressDIalog.getInstance();
//        }
//        if (isProgressAdded) {
//            return;
//        }
//        if (!isFinishing() && !oxigenProgressDialog.isAdded())
//            oxigenProgressDialog.show(getSupportFragmentManager(), OxigenProgressDIalog.TAG);
//
//        isProgressAdded = true;
//    }
//
//    public void hideProgressDialog() {
//        if (oxigenProgressDialog == null) {
//            return;
//        }
//        if (oxigenProgressDialog.isVisible())
//            oxigenProgressDialog.dismissAllowingStateLoss();
//        isProgressAdded = false;
//    }

    public void showProgressdialog(String message) {
        showProgressdialog(message, false);
    }

    public void showProgressdialog() {
        showProgressdialog("Please Wait..", false);
    }

    public synchronized void showProgressdialog(String message, boolean isCancelable) {

        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(isCancelable);
            }
//        progressDialog.setProgress(0);
            progressDialog.setMessage(message);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        LoggerUtil.d("--------", "hide called");
        try {
            if (progressDialog != null) {
                progressDialog.cancel();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.with(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /**
     * usage ex : PackageManager.FEATURE_CAMERA_ANY
     *
     * @param requiredFeature
     * @return
     */
    public boolean isFeatureAvailable(String requiredFeature) {
        return getPackageManager().hasSystemFeature(requiredFeature);
    }

    public void initialiseToolBar(boolean isBackEnabled, String title, boolean isSearchEnable) {
//        initialiseToolBar(isBackEnabled, title, false, isSearchEnable);
    }

    public void initialiseToolBar(boolean isBackEnabled, String title) {
//        initialiseToolBar(isBackEnabled, title, false, false);
    }


    public String rupeetoPaise(String amount) {
        return String.valueOf((int) (Double.parseDouble(amount) * 100));
    }

    public String paisetoRupee(String amount) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Double convertedString=Double.parseDouble(amount) / 100;
        String numberAsString = decimalFormat.format(convertedString);
        return numberAsString;
        //return String.valueOf(Double.parseDouble(amount) / 100);
    }

    public String addAmount(String amount, String surcharge) {
        return String.valueOf((Integer.parseInt(amount) + Integer.parseInt(surcharge)));
    }

    public String subAmount(String amount, String surcharge) {
        return String.valueOf((Integer.parseInt(amount) - Integer.parseInt(surcharge)));
    }

    public String subdoubleAmount(String amount, String surcharge) {
        if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(surcharge)) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            //String amounts=decimalFormat.format(amount);
            return decimalFormat.format(Double.parseDouble(amount) - Double.parseDouble(surcharge));
        } else {
            return "";
        }

    }

    public String twoDecimalFormat(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            //String amounts=decimalFormat.format(amount);
            return decimalFormat.format(Double.parseDouble(amount));
        } else {
            return "";
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public ClickableSpan getReportingClickableSpan(final TextView reportError, final String subject) {
        final ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            /*String[] TO = {"grover.priya0018@gmail.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OTP not received");*/
//                String msg = "\n\n\nMobile No: " + UrbanForestPrefrences.getInstance(BaseActivity.this).getString(PrefrenceConstants.MOBILE_NO) + "\n";
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
//                msg += "Screen Resolution: " + width + "*" + height + "px\n";
//                DisplayMetrics metrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(metrics);
//                int screenDensity = metrics.densityDpi;
//                msg += "Screen Density: " + screenDensity + "dpi\n";
//                msg += "Device Type: Android\n";
//                msg += "Model: " + Build.MANUFACTURER
//                        + " " + Build.MODEL + " " + Build.VERSION.RELEASE
//                        + " " + Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName() + "\n";

//                String uriText = "mailto:care@oxigenwallet.com" +
//                        "?subject=" + Uri.encode(subject) +
//                        "&body=" + Uri.encode(msg);

//                Uri uri = Uri.parse(uriText);

//                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
//                sendIntent.setData(uri);
                try {
//                    startActivity(Intent.createChooser(sendIntent, "Send mail..."));

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(BaseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
//                ds.setColor(ContextCompat.getColor(BaseActivity.this, R.color.blue));
                reportError.setMovementMethod(LinkMovementMethod.getInstance());
                reportError.setHighlightColor(Color.TRANSPARENT);

            }
        };
        return clickableSpan1;
    }




//    public void showNetworkErrorDialog() {
//        showNetworkErrorDialog(false);
//    }

//    public void showNetworkErrorDialog(boolean closeActivity) {
//        if (dialog == null) {
//            dialog = new OperatorInfoDialog(getResources().getString(R.string.network_not_available), getResources().getString(R.string.cb_snooze_network_error), this, R.drawable.insufficient_funds, getResources().getString(R.string.ok_capitalize), AppConstants.DIALOG_STATE.NORMAL, this);
//            dialog.setCloseActivity(closeActivity);
//            dialog.showDialog();
//        }
//    }

//    public String getCustCareMessage() {
//        String s = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.CUSTOMER_CARE_MSG);
//        return TextUtils.isEmpty(s) ? "" : ", " + s;
//    }


//    public String getMDNwithoutPrefix() {
//        String s = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.MOBILE_NO);
//        return !TextUtils.isEmpty(s) ? s.substring(2) : "";
//    }

    /*public void showInfoDialog(int module) {
        String maxLimit = "";
        String moduleName = "";
        switch (module) {
            case AppConstants.INFO_FOR.BANK_IFSC:
                maxLimit = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.MAX_P2A_TRANSACTION_LIMIT);
                moduleName = "to Bank";
                break;
            case AppConstants.INFO_FOR.BANK_MMID:
                maxLimit = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.MAX_P2A_TRANSACTION_LIMIT);
                moduleName = "to Bank";
                break;
            case AppConstants.INFO_FOR.P2P:
                maxLimit = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.P2P_MAX_LIMIT);
                moduleName = "to Mobile";
                break;
        }

        HashMap hashMap = new HashMap<>();
        hashMap.put(NetCoreConstants.ParameterName.SCREEN_NAME, "Send money " + moduleName);
        AnalyticsManager.registerNetCoreEvent(this, 134, hashMap);

        OperatorInfoDialog info_dialog = new OperatorInfoDialog(CommonFunctionsUtil.getInfoTextForModule(module, maxLimit), getString(R.string.information), this, R.drawable.info_popup, getResources().getString(R.string.ok_capitalize));
        info_dialog.showDialog();
    }*/

//    public void showInfoDialog(int module) {
//        String infoText = "";
//        String moduleName = "";
//        switch (module) {
//            case AppConstants.INFO_FOR.BANK_IFSC:
//                infoText = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.INFO_P2A);
//                break;
//            case AppConstants.INFO_FOR.BANK_MMID:
//                infoText = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.INFO_P2A);
//                break;
//            case AppConstants.INFO_FOR.P2P:
//                infoText = UrbanForestPrefrences.getInstance(this).getString(PrefrenceConstants.INFO_P2P);
//                break;
//            case AppConstants.INFO_FOR.P2M:
//                String maxLimit =  CommonFunctionsUtil.getLimit(this, AppConstants.TYPE_OF_LIMIT.P2P, AppConstants.EXTRAS.MAX);
//                moduleName = "to Mobile";
//                infoText = CommonFunctionsUtil.getInfoTextForModule(module, maxLimit);
//                break;
//        }
//
//        HashMap hashMap = new HashMap<>();
//        hashMap.put(NetCoreConstants.ParameterName.SCREEN_NAME, "Send money " + moduleName);
//        AnalyticsManager.registerNetCoreEvent(this, 134, hashMap);
//
//        OperatorInfoDialog info_dialog = new OperatorInfoDialog(infoText + "", getString(R.string.information), this, R.drawable.info_popup, getResources().getString(R.string.ok_capitalize));
//        info_dialog.showDialog();
//    }


    public void overrideEnterkey(EditText editText) {
        editText.setSingleLine(true);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
                    // Perform action on Enter key press
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

    }

    public void sendMail(String email, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void showSnackBar(Context context, View parentLayout, String message) {
        Snackbar sbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        sbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        sbar.show();
    }
}
