package in.ac.vnrvjiet.convergence.presentation.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.presentation.Convergence2k18;

/**
 * Created by venkat on 1/1/18.
 */

public class NoInternetActivity extends AppCompatActivity implements NetworkChangeReceiver.ConnectivityReceiverListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }

    @Override
    protected void onResume() {
        Convergence2k18.getInstance().setConnectivityListener(this);
        super.onResume();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if(Utils.hasActiveInternetConnection(NoInternetActivity.this)) {
            super.onBackPressed();
        } else {
            ConvergenceToast.create(NoInternetActivity.this, R.drawable.ic_error_outline_black_24dp, "No Internet\nPlease try again", Toast.LENGTH_SHORT);
        }
    }
}
