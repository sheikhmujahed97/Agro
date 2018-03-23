package in.ac.vnrvjiet.convergence.presentation.util;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by venkat on 15/1/18.
 */

public class ConvergenceInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "ConvergenceInstanceID";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "onTokenRefresh: "+refreshedToken);
        super.onTokenRefresh();
    }
}
