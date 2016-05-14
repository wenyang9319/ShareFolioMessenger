package com.example.wenyang.sharefoliomessenger.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenyang.sharefoliomessenger.R;
import com.example.wenyang.sharefoliomessenger.app.MyApplication;
import com.example.wenyang.sharefoliomessenger.engine.AccountEngine;
import com.example.wenyang.sharefoliomessenger.event_bus.LoginEvent;
import com.example.wenyang.sharefoliomessenger.gcm.GcmIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by WenYang on 12/05/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static String TAG = LoginActivity.class.getSimpleName();

    GoogleCloudMessaging gcm;

    String regid;

    AutoCompleteTextView tvEmail;

    EditText tvPassword;

    Context mContext;

    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mContext = this;

        MyApplication.getInstance().getPrefManager().clear();

        tvEmail = (AutoCompleteTextView) findViewById(R.id.email);
        tvPassword = (EditText) findViewById(R.id.password);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void login(View view) {
        showProgressBar("Loading...");

        AccountEngine accountEngine = new AccountEngine(mContext);

        accountEngine.register(mContext ,tvEmail.getText().toString(),tvPassword.getText().toString());
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent){
        startActivity(new Intent(mContext, MainActivity.class));
        hideProgressBar();
        finish();
    }

    void showProgressBar(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        });

    }

    void hideProgressBar() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

//    private boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil
//                .isGooglePlayServicesAvailable(this);
//
//        if (resultCode != ConnectionResult.SUCCESS) {
//
//            //google play services app not available
//
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                Log.i(TAG, "No Google Play Services...Get it from the store.");
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//
//        //google play services app are available, return true for the checkPlayServices() method
//        return true;
//    }
//
//
//    void startRegistration() {
//
//        if (checkPlayServices()) {
//            // If this check succeeds, proceed with normal processing.
//            // Otherwise, prompt user to get valid Play Services APK.
//            Log.i(TAG, "Google Play Services OK");
//            gcm = GoogleCloudMessaging.getInstance(this);
//            regid = ""; //change later
//            System.out.println(regid);
//            if (regid.isEmpty()) {
//                registerInBackground();
//            } else {
//
//                //registration id still exists in sharedPreferences
//                Log.i(TAG, "Reg ID Not Empty");
//            }
//        } else {
//            Log.i(TAG, "No valid Google Play Services APK found.");
//        }
//
//    }
}
