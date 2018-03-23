package in.ac.vnrvjiet.convergence.presentation.signup;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.FragmentPhoneNumberBinding;
import in.ac.vnrvjiet.convergence.presentation.util.ActivityUtils;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceToast;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

/**
 * Created by pinna on 12/29/2017.
 */

public class PhoneNumberFragment extends Fragment {

    private FragmentPhoneNumberBinding fragmentPhoneNumberBinding;
    private SignUpViewModel signUpViewModel;

    private static final String TAG = "PhoneNumberFragment";

    public static PhoneNumberFragment getInstance() {
        return new PhoneNumberFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPhoneNumberBinding = FragmentPhoneNumberBinding.inflate(inflater, container, false);
        signUpViewModel = SignUpActivity.obtainViewModel(getActivity());
        fragmentPhoneNumberBinding.setSignUpViewModel(signUpViewModel);
        signUpViewModel.getPhoneNumberNextClickSignal().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                setUpOTPFragment();
            }
        });
        fragmentPhoneNumberBinding.phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    signUpViewModel.getPhoneNumberNextClickSignal().call();
                    return true;
                }
                return false;
            }
        });
        return fragmentPhoneNumberBinding.getRoot();
    }

    private void setUpOTPFragment() {
        String phoneNumber = fragmentPhoneNumberBinding.phoneNumberEditText.getText().toString();
        if (phoneNumber.length() == 10) {
            if (Utils.hasActiveInternetConnection(getContext())) {
                OTPFragment otpFragment = OTPFragment.getInstance();
                Bundle otpBundle = new Bundle();
                Log.i(TAG, "setUpOTPFragment: phone number " + phoneNumber);
                otpBundle.putString("phoneNumber", phoneNumber);
                otpFragment.setArguments(otpBundle);
                if (getFragmentManager() != null) {
                    ActivityUtils.replaceFragmentInActivity(getFragmentManager(), otpFragment, R.id.phone_number_fragment_holder);
                }
            } else {
                ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "No Internet\nPlease try again", Toast.LENGTH_SHORT);
            }
        } else {
            Toast.makeText(getContext(), "Enter valid phone number", Toast.LENGTH_LONG).show();
        }
    }
}
