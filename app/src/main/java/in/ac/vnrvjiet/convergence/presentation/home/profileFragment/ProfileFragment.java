package in.ac.vnrvjiet.convergence.presentation.home.profileFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;
import in.ac.vnrvjiet.convergence.databinding.FragmentProfileBinding;
import in.ac.vnrvjiet.convergence.models.OfflineParticipation;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;
import in.ac.vnrvjiet.convergence.presentation.util.TextDrawable;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

/**
 * Created by venkat on 1/1/18.
 */

public class ProfileFragment extends Fragment {

    MainViewModel mainViewModel;
    private static ProfileFragment profileFragment;
    FragmentProfileBinding fragmentProfileBinding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userNodedatabase;

    PersistentDeviceStorage persistentDeviceStorage;
    String userName, profilePicURL, emailId, phoneNumber;

    ProgressDialog progressDialog;

    public static ProfileFragment getInstance() {
        if (profileFragment == null)
            profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentProfileBinding.setMainViewModel(mainViewModel);
        persistentDeviceStorage = PersistentDeviceStorage.getInstance(getContext());
        progressDialog = Utils.showLoadingDialog(getContext(), true);

        userName = persistentDeviceStorage.getName();
        profilePicURL = persistentDeviceStorage.getPic();
        emailId = persistentDeviceStorage.getEmail();
        phoneNumber = persistentDeviceStorage.getPhoneNumber();

        firebaseDatabase = FirebaseDatabase.getInstance();
        userNodedatabase = firebaseDatabase.getReference().child("users").child(phoneNumber).child("participatedEvents");
        populateView();
        return fragmentProfileBinding.getRoot();
    }

    private void populateView() {
        if (Utils.hasActiveInternetConnection(getContext()) && profilePicURL != null && (profilePicURL.contains(".com") || profilePicURL.contains(".net"))) {
            Utils.loadImage(profilePicURL, fragmentProfileBinding.profilePic, getContext());
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(String.valueOf(userName.toCharArray()[0]).toUpperCase(), getResources().getColor(R.color.grey));
            fragmentProfileBinding.profilePic.setImageDrawable(drawable);
            fragmentProfileBinding.profilePic.setScaleType(ImageView.ScaleType.FIT_XY);
            fragmentProfileBinding.profilePic.setBackground(null);
        }
        fragmentProfileBinding.profileUserName.setText(userName);
        fragmentProfileBinding.profileUserEmail.setText(emailId);
        fragmentProfileBinding.profilePhoneNumer.setText(phoneNumber);
        userNodedatabase.keepSynced(true);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
