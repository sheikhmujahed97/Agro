package in.ac.vnrvjiet.convergence.presentation.splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;
import in.ac.vnrvjiet.convergence.presentation.admin.AdminActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.signup.PhoneNumberVerification;
import in.ac.vnrvjiet.convergence.presentation.signup.SignUpActivity;
import in.ac.vnrvjiet.convergence.presentation.util.BaseActivity;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceFirebaseUser;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceFirebaseUserCallBack;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceToast;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

public class SplashActivity extends BaseActivity implements ConvergenceFirebaseUserCallBack {
    PersistentDeviceStorage persistentDeviceStorage;
    ConvergenceFirebaseUser convergenceFirebaseUser;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        FirebaseApp.initializeApp(getContext());
        Log.i(TAG, "onCreate: token = " + FirebaseInstanceId.getInstance().getToken());
        setContentView(R.layout.activity_splash);
        persistentDeviceStorage = PersistentDeviceStorage.getInstance(getContext());
        convergenceFirebaseUser = new ConvergenceFirebaseUser(getContext());
        gotoMainScreen();
    }

    private void gotoMainScreen() {
        String userType = persistentDeviceStorage.getUserType();
        Log.i(TAG, "gotoMainScreen: userType" + userType);
        Class targetClass;
        if (userType.equalsIgnoreCase("admin")) {
            targetClass = AdminActivity.class;
            Log.i(TAG, "gotoMainScreen: admin class");
        } else {
            Log.i(TAG, "gotoMainScreen: user class");
            targetClass = MainActivity.class;
        }
        Intent mainScreenIntent = new Intent(getContext(), targetClass);
        mainScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreenIntent);
    }

    public Context getContext() {
        return SplashActivity.this;
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        gotoMainScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDataError() {
        finish();
    }

    @Override
    public void onDataSuccess() {

    }
}