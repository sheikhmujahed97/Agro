package in.ac.vnrvjiet.convergence.presentation.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ashwithateam on 9/27/17.
 */

public class BaseActivity extends AppCompatActivity implements LifecycleOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }
}
