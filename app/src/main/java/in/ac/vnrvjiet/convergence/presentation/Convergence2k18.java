package in.ac.vnrvjiet.convergence.presentation;

import android.app.Application;
import android.content.IntentFilter;

import in.ac.vnrvjiet.convergence.presentation.util.NetworkChangeReceiver;

/**
 * Created by venkat on 1/1/18.
 */

public class Convergence2k18 extends Application {
    private static Convergence2k18 mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(new NetworkChangeReceiver(), intentFilter);
    }

    public static synchronized Convergence2k18 getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(NetworkChangeReceiver.ConnectivityReceiverListener listener) {
        NetworkChangeReceiver.connectivityReceiverListener = listener;
    }
}
