package in.ac.vnrvjiet.convergence.presentation.signup;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import in.ac.vnrvjiet.convergence.presentation.util.SingleLiveEvent;

/**
 * Created by pinna on 12/29/2017.
 */

public class SignUpViewModel extends AndroidViewModel {
    private static final String TAG = "SignUpViewModel";

    private SingleLiveEvent googleSignInClickEvent = new SingleLiveEvent();
    private SingleLiveEvent phoneNumberNextClickSignal = new SingleLiveEvent();
    private SingleLiveEvent checkPayment = new SingleLiveEvent();
    private SingleLiveEvent<String> checkUserType = new SingleLiveEvent<String>();

    public SingleLiveEvent<String> getCheckUserType() {
        return checkUserType;
    }

    public SingleLiveEvent getPhoneNumberNextClickSignal() {
        return phoneNumberNextClickSignal;
    }

    public SingleLiveEvent getGoogleSignInClickEvent() {
        return googleSignInClickEvent;
    }

    public SingleLiveEvent getCheckPayment() {
        return checkPayment;
    }

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    public void phoneNumberNextButton() {
        phoneNumberNextClickSignal.call();
    }

    public void googleSignUpClick() {
        googleSignInClickEvent.call();
    }

    void getBasicDetails() {
        Log.i(TAG, "getBasicDetails: ");
        checkUserType.call();
    }
}
