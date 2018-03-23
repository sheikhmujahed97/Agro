package in.ac.vnrvjiet.convergence.presentation.home.contactsFragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.FragmentContactsBinding;
import in.ac.vnrvjiet.convergence.models.ContactsModel;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;
import in.ac.vnrvjiet.convergence.presentation.util.ConvergenceToast;
import in.ac.vnrvjiet.convergence.presentation.util.NoInternetActivity;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

/**
 * Created by pinna on 12/30/2017.
 */

public class ContactsFragment extends Fragment {

    FragmentContactsBinding fragmentContactsBinding;
    MainViewModel mainViewModel;

    ContactsAdapter contactsAdapter;
    List<ContactsModel> contactsModelList;
    String[] contactsNameList, contactsPhoneList, contactsCommitteeList;

    public static ContactsFragment contactsFragment;
    private static final String TAG = "ContactsFragment";

    public static ContactsFragment getInstance() {
        if (contactsFragment == null)
            contactsFragment = new ContactsFragment();
        return contactsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsNameList = getResources().getStringArray(R.array.contacts_person_name);
        contactsPhoneList = getResources().getStringArray(R.array.contacts_person_number);
        contactsCommitteeList = getResources().getStringArray(R.array.contacts_person_committee);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentContactsBinding = FragmentContactsBinding.inflate(inflater, container, false);
        contactsModelList = new ArrayList<>(0);
        int contactsSize = contactsNameList.length;
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentContactsBinding.setMainViewModel(mainViewModel);

        for (int i = 0; i < contactsSize; i++) {
            contactsModelList.add(new ContactsModel(contactsCommitteeList[i], contactsPhoneList[i], contactsNameList[i]));
        }

        contactsAdapter = new ContactsAdapter(contactsModelList, mainViewModel);

        fragmentContactsBinding.contactsRecycler.setAdapter(contactsAdapter);

        mainViewModel.getStartCall().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                String phoneNumber = "+91" + s;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                startActivity(intent);
            }
        });

        return fragmentContactsBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: Contacts Fragment");
        super.onDestroy();
    }
}