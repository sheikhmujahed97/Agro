package in.ac.vnrvjiet.convergence.presentation.signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;
import in.ac.vnrvjiet.convergence.databinding.ActivitySignUpBinding;
import in.ac.vnrvjiet.convergence.presentation.util.BaseActivity;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceFirebaseUser;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceFirebaseUserCallBack;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceToast;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;
import in.ac.vnrvjiet.convergence.presentation.util.ViewModelFactory;

public class SignUpActivity extends BaseActivity {

    private SignUpViewModel signUpViewModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ActivitySignUpBinding activitySignUpBinding;
    ConvergenceFirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
    private String email;
    private PersistentDeviceStorage persistentDeviceStorage;
    private final int GOOGLE_SIGN_IN = 100;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = obtainViewModel(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = new ConvergenceFirebaseUser(getContext());
        databaseReference = firebaseDatabase.getReference();
        activitySignUpBinding.setSignUpViewModel(signUpViewModel);
        persistentDeviceStorage = PersistentDeviceStorage.getInstance(getContext());
        signUpViewModel.getGoogleSignInClickEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.i(TAG, "onChanged: ");
                startGoogleSignIn();
            }
        });
    }

    private void startGoogleSignIn() {
        if (Utils.hasActiveInternetConnection(SignUpActivity.this)) {
            progressDialog = Utils.showLoadingDialog(SignUpActivity.this, false);
            try {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder().setIsSmartLockEnabled(false).setLogo(AuthUI.NO_LOGO)
                                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .setAllowNewEmailAccounts(true)
                                .build(),
                        GOOGLE_SIGN_IN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "No Internet\nPlease try again", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_SIGN_IN) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    List<UserInfo> userInfos = new ArrayList<>();
                    userInfos.addAll(firebaseUser.getProviderData());
                    JSONObject jsonObject;
                    try {
                        Gson gson = new Gson();
                        jsonObject = new JSONObject(gson.toJson(userInfos.get(1)));
                        String emailJson = jsonObject.getString("zzlnj");
                        JSONObject emailObject = new JSONObject(emailJson);
                        email = emailObject.getString("email");
                        persistentDeviceStorage.setEmail(email);
                        persistentDeviceStorage.setName(firebaseUser.getDisplayName());
                        if (firebaseUser.getPhotoUrl() != null) {
                            if (persistentDeviceStorage == null)
                                persistentDeviceStorage = PersistentDeviceStorage.getInstance(getContext());
                            persistentDeviceStorage.setPic(firebaseUser.getPhotoUrl().toString());
                        }
                        gotoPhoneVerificationScreen();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Some error occured, Please try again", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(getContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void gotoPhoneVerificationScreen() {
        Intent phoneNumberVerificationIntent = new Intent(getContext(), PhoneNumberVerification.class);
        phoneNumberVerificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(phoneNumberVerificationIntent);
    }

    @NonNull
    public static SignUpViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(SignUpViewModel.class);
    }

    public Context getContext() {
        return SignUpActivity.this;
    }
}