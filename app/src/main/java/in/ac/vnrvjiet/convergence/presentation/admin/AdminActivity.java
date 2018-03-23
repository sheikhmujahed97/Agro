package in.ac.vnrvjiet.convergence.presentation.admin;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.ActivityAdminBinding;
import in.ac.vnrvjiet.convergence.presentation.util.ViewModelFactory;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding activityAdminBinding;
    AdminViewModel adminViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminBinding = DataBindingUtil.setContentView(this,R.layout.activity_admin);
        adminViewModel = obtainViewModel(this);
        activityAdminBinding.setAdminViewModel(adminViewModel);
    }

    @NonNull
    public static AdminViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AdminViewModel.class);
    }
}
