package in.ac.vnrvjiet.convergence.presentation.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.ActivityMainBinding;
import in.ac.vnrvjiet.convergence.presentation.home.aboutFragment.AboutFragment;
import in.ac.vnrvjiet.convergence.presentation.home.contactsFragment.ContactsFragment;
import in.ac.vnrvjiet.convergence.presentation.home.homeFragment.EventsFragment;
import in.ac.vnrvjiet.convergence.presentation.home.homeFragment.HackathonFragment;
import in.ac.vnrvjiet.convergence.presentation.home.homeFragment.HomeFragment;
import in.ac.vnrvjiet.convergence.presentation.home.homeFragment.TechnicalPresentationFragment;
import in.ac.vnrvjiet.convergence.presentation.home.homeFragment.WorkShopFragment;
import in.ac.vnrvjiet.convergence.presentation.home.profileFragment.ProfileFragment;
import in.ac.vnrvjiet.convergence.presentation.util.ActivityUtils;
import in.ac.vnrvjiet.convergence.presentation.util.ViewModelFactory;
import in.ac.vnrvjiet.convergence.presentation.util.bottombar.BottomBar;
import in.ac.vnrvjiet.convergence.presentation.util.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    ActivityMainBinding activityMainBinding;
    BottomBar bottomBar;
    ContactsFragment contactsFragment;
    private static final String TAG = "MainActivity";
    HomeFragment homeFragment;
    AboutFragment aboutFragment;
    ProfileFragment profileFragment;
    TechnicalPresentationFragment technicalPresentationFragment;
    HackathonFragment hackathonFragment;
    EventsFragment eventsFragment;
    WorkShopFragment workShopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);
        mainViewModel = obtainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);
        bottomBar = findViewById(R.id.bottomBar);
        setUpHomeFragment();

        mainViewModel.syncMyData();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Log.d(TAG, "onNavigationItemSelected() called with: item = [" + tabId + "]");
                switch (tabId) {
                    case R.id.tab_home: {
                        if (homeFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                            if (homeFragment == null) {
                                homeFragment = HomeFragment.getInstance();
                            }
                            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), homeFragment, R.id.contentContainer);
                        }
                        break;
                    }
                    case R.id.tab_contact: {
                        if (contactsFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                            if (contactsFragment == null) {
                                contactsFragment = ContactsFragment.getInstance();
                            }
                            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), contactsFragment, R.id.contentContainer);
                        }
                        break;
                    }
                    case R.id.tab_about: {
                        if (aboutFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                            if (aboutFragment == null) {
                                aboutFragment = AboutFragment.getInstance();
                            }
                            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), aboutFragment, R.id.contentContainer);
                        }
                        break;
                    }
                    case R.id.tab_profile: {
                        if (profileFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                            if (profileFragment == null) {
                                profileFragment = ProfileFragment.getInstance();
                            }
                            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), profileFragment, R.id.contentContainer);
                        }
                        break;
                    }
                }
            }
        });

        mainViewModel.getShowTechnicalPresentation().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (technicalPresentationFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                    if (technicalPresentationFragment == null) {
                        technicalPresentationFragment = TechnicalPresentationFragment.getInstance();
                    }
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), technicalPresentationFragment, R.id.contentContainer);
                }
            }
        });

        mainViewModel.getReplaceFragmentBackPress().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (homeFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.getInstance();
                    }
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), homeFragment, R.id.contentContainer);
                }
            }
        });

        mainViewModel.getHomeFragmentBackPress().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                closeApp();
            }
        });

        mainViewModel.showHackathonRules.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (hackathonFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                    if (hackathonFragment == null) {
                        hackathonFragment = HackathonFragment.getInstance();
                    }
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), hackathonFragment, R.id.contentContainer);
                }
            }
        });

        mainViewModel.showEventsList.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (eventsFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                    if (eventsFragment == null) {
                        eventsFragment = EventsFragment.getInstance();
                    }
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), eventsFragment, R.id.contentContainer);
                }
            }
        });

        mainViewModel.showWorkshopList.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (workShopFragment != getSupportFragmentManager().findFragmentById(R.id.contentContainer)) {
                    if (workShopFragment == null) {
                        workShopFragment = WorkShopFragment.getInstance();
                    }
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), workShopFragment, R.id.contentContainer);
                }
            }
        });
    }

    private void closeApp() {
        super.onBackPressed();
    }

    private void setUpHomeFragment() {
        // TODO: 4/1/18 admin fragment
        homeFragment = HomeFragment.getInstance();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), homeFragment, R.id.contentContainer);
    }

    @NonNull
    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }

    @Override
    public void onBackPressed() {
        if (bottomBar.getCurrentTab().getId() == R.id.tab_home) {
            //DoNothing
        } else {
            bottomBar.selectTabAtPosition(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}