package in.ac.vnrvjiet.convergence.presentation.home.aboutFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.vnrvjiet.convergence.databinding.FragmentAboutBinding;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;

/**

 * Created by venkat on 1/1/18.
 */

public class AboutFragment extends Fragment {

    FragmentAboutBinding fragmentAboutBinding;
    MainViewModel mainViewModel;
    private static AboutFragment aboutFragment;

    public static AboutFragment getInstance() {
        if (aboutFragment == null)
            aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentAboutBinding.setMainViewModel(mainViewModel);

        return fragmentAboutBinding.getRoot();
    }
}
