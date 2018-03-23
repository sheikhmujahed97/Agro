package in.ac.vnrvjiet.convergence.presentation.home.homeFragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;
import in.ac.vnrvjiet.convergence.databinding.FragmentHomeBinding;
import in.ac.vnrvjiet.convergence.models.OfflineParticipation;
import in.ac.vnrvjiet.convergence.models.UserProfilePOJO;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceToast;
import in.ac.vnrvjiet.convergence.presentation.util.NoInternetActivity;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

/**
 * Created by venkat on 1/1/18.
 */

public class HomeFragment extends Fragment {

    private static HomeFragment homeFragment;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userNodeDatabase;
    FragmentHomeBinding fragmentHomeBinding;
    MainViewModel mainViewModel;
    private static final String TAG = "HomeFragment";
    UserProfilePOJO userProfilePOJO;
    PersistentDeviceStorage persistentDeviceStorage;
    private String phoneNumber;
    ArrayList<String> unPushedEventNames, unPushedTimeNames;

    public static HomeFragment getInstance() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentHomeBinding.setMainViewModel(mainViewModel);
        firebaseDatabase = FirebaseDatabase.getInstance();
        persistentDeviceStorage = PersistentDeviceStorage.getInstance(getContext());
        phoneNumber = persistentDeviceStorage.getPhoneNumber();
        //Utils.loadImage(R.drawable.convergence_font,fragmentHomeBinding.bigTextLayout,getContext());
        userProfilePOJO = new UserProfilePOJO(persistentDeviceStorage.getEmail(), persistentDeviceStorage.getName(), phoneNumber, persistentDeviceStorage.getPic());
        userNodeDatabase = firebaseDatabase.getReference()
                .child("users")
                .child(mainViewModel.getUserPhoneNumber());
        mainViewModel.getParticipateInEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (persistentDeviceStorage.getHasPaidMoney()) {
                    IntentIntegrator.forSupportFragment(HomeFragment.this).initiateScan();
                } else {
                    ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "You have not registered yet\nContact registration desk", Toast.LENGTH_SHORT);
                }
            }
        });

        mainViewModel.getSyncMyData().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (Utils.hasActiveInternetConnection(getContext())) {
                    ConvergenceToast.create(getContext(), 0, "Syncing Data", Toast.LENGTH_SHORT);
                    ArrayList<String> eventNamesList = persistentDeviceStorage.participationEventNames();
                    ArrayList<String> eventTimesList = persistentDeviceStorage.participationEventTimes();
                    unPushedEventNames = new ArrayList<>(0);
                    unPushedTimeNames = new ArrayList<>(0);

                    int eventSize = eventNamesList.size();
                    for (int i = 0; i < eventSize; i++) {
                        final String eventName = eventNamesList.get(i);
                        final String eventTime = eventTimesList.get(i);
                        userNodeDatabase
                                .child("participatedEvents")
                                .push()
                                .setValue(new OfflineParticipation(eventName, eventTime))
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        unPushedEventNames.add(eventName);
                                        unPushedTimeNames.add(eventTime);
                                    }
                                });

                        firebaseDatabase.getReference()
                                .child("Events")
                                .child(eventName)
                                .child(phoneNumber)
                                .setValue(userProfilePOJO)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        unPushedEventNames.add(eventName);
                                        unPushedTimeNames.add(eventTime);
                                    }
                                });
                    }
                    persistentDeviceStorage.updateTinyDB(unPushedEventNames, unPushedTimeNames);
                } else {
                    ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "No Internet\nSync data failed", Toast.LENGTH_SHORT);
                }
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "Scan Cancelled", Toast.LENGTH_LONG);
            } else {
                if (result.getContents().contains("conv2k18")) {
                    String convergenceEventName = result.getContents();
                    Pattern pattern = Pattern.compile("_ *");
                    Matcher matcher = pattern.matcher(convergenceEventName);
                    String time = Calendar.getInstance().getTime().toString();
                    if (matcher.find()) {
                        convergenceEventName = convergenceEventName.substring(matcher.end());
                    }
                    if (Utils.hasActiveInternetConnection(getContext())) {
                        userNodeDatabase.child("participatedEvents").push().setValue(new OfflineParticipation(convergenceEventName, time));
                        firebaseDatabase.getReference().child("Events").child(convergenceEventName).child(phoneNumber).setValue(userProfilePOJO);
                    } else {
                        PersistentDeviceStorage.getInstance(getContext()).participateInEvent(convergenceEventName, time);
                    }
                    ConvergenceToast.create(getContext(), R.drawable.ic_toast_success_black_24dp, "Registered for event\n" + convergenceEventName, Toast.LENGTH_LONG);
                } else {
                    ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "Not a Convergence2k18 Event", Toast.LENGTH_LONG);
                }
            }
        } else {
            ConvergenceToast.create(getContext(), R.drawable.ic_error_outline_black_24dp, "Some Error occured\nPlease try again", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mainViewModel.getHomeFragmentBackPress().call();
                    return true;
                }
                return false;
            }
        });
    }
}
