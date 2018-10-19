package com.lznby.jetpack.base;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

/**
 * @author Lznby
 */
public abstract class BaseSingleFragmentActivity <T extends BaseFragment> extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This code be used to set content view layout.
        setContentView(setLayout());
        // Get fragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Get fragment by findFragmentById.
        T fragment = (T)fragmentManager.findFragmentById(setContainer());
        //Add fragment to fragmentManager.
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(setContainer(),fragment)
                    .commit();
        }

    }

    /**
     * Return A fragment be used to inflate in container.
     * @return A fragment extends BaseFragment.
     */
    protected abstract T createFragment();



    /**
     * This method was used to set fragment'container.
     *
     * @return A FrameLayout view in setLayout'R.layout.xxx.
     */
    public abstract @IdRes
    int setContainer();
}
