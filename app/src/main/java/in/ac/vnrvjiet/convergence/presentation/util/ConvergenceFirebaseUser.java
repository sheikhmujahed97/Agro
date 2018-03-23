package in.ac.vnrvjiet.convergence.presentation.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;

/**
 * Created by venkat on 7/1/18.
 */

public class ConvergenceFirebaseUser {

    private static final String TAG = "ConvergenceFirebaseUser";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PersistentDeviceStorage persistentDeviceStorage;
    private Context context;
    public ConvergenceFirebaseUser(Context context) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        persistentDeviceStorage = PersistentDeviceStorage.getInstance(context);
    }

    public void getBasicUserDetails(String phoneNumber, final ProgressDialog progressDialog, final ConvergenceFirebaseUserCallBack firebaseUserCallBack) {
        databaseReference.child("users")
                .child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null) {
                            for(DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                                Log.i(TAG, "onDataChange: "+dataSnapshot.getKey()+" "+dataSnapshot.getValue());
                                switch (userSnapShot.getKey()) {
                                    case "phoneNumber":
                                        persistentDeviceStorage.setPhoneNumber(userSnapShot.getValue().toString());
                                        break;
                                    case "hasPaidMoney":
                                        persistentDeviceStorage.setHasPaidMoney(Boolean.valueOf(userSnapShot.getValue().toString()));
                                        break;
                                    case "userType":
                                        persistentDeviceStorage.setUserType(userSnapShot.getValue().toString());
                                        break;
                                    case "email":
                                        persistentDeviceStorage.setEmail(userSnapShot.getValue().toString());
                                        break;
                                    case "eventName":
                                        persistentDeviceStorage.setMyEventName(userSnapShot.getValue().toString());
                                    default:
                                        break;
                                }
                            }
                            firebaseUserCallBack.onDataSuccess();
                            progressDialog.cancel();
                        } else {
                            Log.i(TAG, "onDataChange: dataSnapshot is null");
                            Log.i(TAG, "onDataChange: Some error occured, Contact registration desk");
                            ConvergenceToast.create(progressDialog.getContext(), R.drawable.ic_error_outline_black_24dp,"Some error occured\nContact registration desk", Toast.LENGTH_SHORT);
                            progressDialog.cancel();
                            firebaseUserCallBack.onDataError();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: error connection to firebase");
                        Log.i(TAG, "onDataChange: Some error occured, Contact registration desk");
                        ConvergenceToast.create(progressDialog.getContext(), R.drawable.ic_error_outline_black_24dp,"Some error occured\nContact registration desk", Toast.LENGTH_SHORT);
                        progressDialog.cancel();
                        firebaseUserCallBack.onDataError();
                    }
                });
    }
}
