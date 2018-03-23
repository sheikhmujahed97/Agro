package in.ac.vnrvjiet.convergence.presentation.signup;

import android.os.Bundle;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.presentation.util.ActivityUtils;
import in.ac.vnrvjiet.convergence.presentation.util.BaseActivity;

public class PhoneNumberVerification extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        setUpFragmentView();
    }

    private void setUpFragmentView() {
        PhoneNumberFragment phoneNumberFragment = (PhoneNumberFragment) getSupportFragmentManager().findFragmentById(R.id.phone_number_fragment_holder);
        if (phoneNumberFragment == null) {
            phoneNumberFragment = PhoneNumberFragment.getInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), phoneNumberFragment, R.id.phone_number_fragment_holder);
        }
    }
}
