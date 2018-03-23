package in.ac.vnrvjiet.convergence.presentation.util;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by venkat on 15/1/18.
 */

public class ConvergenceNotificationService extends FirebaseMessagingService {

    private static final String TAG = "ConvergenceNotification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived() called with: remoteMessage = [" + remoteMessage + "]");
        if(remoteMessage.getData().size()>0) {
            Map<String,String> notificationMap = remoteMessage.getData();
            for(String key:notificationMap.keySet()) {
                Log.i(TAG, "onMessageReceived: "+key+" "+notificationMap.get(key));
            }
        }
        if(remoteMessage.getNotification() !=null ) {
            Log.i(TAG, "onMessageReceived: "+remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages() {
        Log.i(TAG, "onDeletedMessages: ");
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        Log.i(TAG, "onMessageSent: "+s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        Log.i(TAG, "onSendError: " + s);
        e.printStackTrace();
    }
}
