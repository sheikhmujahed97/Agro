package in.ac.vnrvjiet.convergence.presentation.home.homeFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.vnrvjiet.convergence.databinding.FragmentTechnicalPresentationBinding;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;

/**
 * Created by venkat on 4/1/18.
 */

public class TechnicalPresentationFragment extends Fragment {

    MainViewModel mainViewModel;
    FragmentTechnicalPresentationBinding technicalPresentationBinding;
    public static TechnicalPresentationFragment technicalPresentationFragment;

    public static TechnicalPresentationFragment getInstance() {
        if (technicalPresentationFragment == null)
            technicalPresentationFragment = new TechnicalPresentationFragment();
        return technicalPresentationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        technicalPresentationBinding = FragmentTechnicalPresentationBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        technicalPresentationBinding.setMainViewModel(mainViewModel);
        return technicalPresentationBinding.getRoot();
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
                    mainViewModel.replaceFragmentOnBackPress();
                    return true;
                }
                return false;
            }
        });
    }
}
