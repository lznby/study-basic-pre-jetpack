package com.lznby.jetpack.content.design.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.vm.CenterViewModel;

import butterknife.BindView;

/**
 * @author Lznby
 */
public class CenterActivity extends BaseActivity<CenterViewModel,Object> implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    private MainHomeFragment mainHomeFragment;
    private MainSubscribeFragment mainSubscribeFragment;
    private MainDiscoveryFragment mainDiscoveryFragment;
    private MainMineFragment mainMineFragment;

    @Override
    public int setLayout() {
        return R.layout.activity_center;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainHomeFragment = (MainHomeFragment) getSupportFragmentManager().findFragmentByTag(Configure.FragmentType.HOME_TAG);
            mainSubscribeFragment = (MainSubscribeFragment) getSupportFragmentManager().findFragmentByTag(Configure.FragmentType.SUBSCRIBE_TAG);
            mainDiscoveryFragment = (MainDiscoveryFragment) getSupportFragmentManager().findFragmentByTag(Configure.FragmentType.DISCOVERY_TAG);
            mainMineFragment = (MainMineFragment) getSupportFragmentManager().findFragmentByTag(Configure.FragmentType.MINE_TAG);
        }
        initFragment(mainHomeFragment, Configure.FragmentType.HOME);
        bnvMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void bindView(Object entity) {

    }


    /**
     * Navigation item selected event.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        int type;
        switch ( menuItem.getItemId()) {
            case R.id.it_home:
                fragment = mainHomeFragment;
                type = Configure.FragmentType.HOME;
                break;
            case R.id.it_subscribe:
                fragment = mainSubscribeFragment;
                type = Configure.FragmentType.SUBSCRIBE;
                break;
            case R.id.it_discovery:
                fragment = mainDiscoveryFragment;
                type = Configure.FragmentType.DISCOVERY;
                break;
            case R.id.it_mine:
                fragment = mainMineFragment;
                type = Configure.FragmentType.MINE;
                break;
            default:
                return false;
        }
        initFragment(fragment, type);
        return true;
    }

    /*****************************************************************************/

    /**
     * initialize fragment or change fragment
     *
     * @param fragment Fragment
     * @param type     Fragment Type
     */
    private void initFragment(Fragment fragment, int type) {

        // Save current fragment
        Fragment checkedFragment;
        // Get fragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Check fragment is null. If fragment is null, new a fragment.
        if (fragment == null) {
            String tag = "";
            if (type == Configure.FragmentType.SUBSCRIBE) {
                mainSubscribeFragment = new MainSubscribeFragment();
                checkedFragment = mainSubscribeFragment;
                tag = Configure.FragmentType.SUBSCRIBE_TAG;
            } else if (type == Configure.FragmentType.DISCOVERY) {
                mainDiscoveryFragment = new MainDiscoveryFragment();
                checkedFragment = mainDiscoveryFragment;
                tag = Configure.FragmentType.DISCOVERY_TAG;
            } else if (type == Configure.FragmentType.MINE) {
                mainMineFragment = new MainMineFragment();
                checkedFragment = mainMineFragment;
                tag = Configure.FragmentType.MINE_TAG;
            } else {
                mainHomeFragment = new MainHomeFragment();
                checkedFragment = mainHomeFragment;
            }
            //Add current fragment to fragmentTransaction when 'fragment' not already be add to fragmentTransaction.
            transaction.add(R.id.main_activity_container, checkedFragment,tag);
        } else {
            // If fragment is not null,  assigns 'fragment' to 'checkedFragment'.
            checkedFragment = fragment;
        }
        // Hide all fragment.
        hideFragment(transaction);
        // Show currentFragment
        transaction.show(checkedFragment);
        // Submit transaction.
        transaction.commit();
    }

    /**
     * hided all fragment.
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {

        if (mainHomeFragment != null) {
            transaction.hide(mainHomeFragment);
        }
        if (mainSubscribeFragment != null) {
            transaction.hide(mainSubscribeFragment);
        }
        if (mainDiscoveryFragment != null) {
            transaction.hide(mainDiscoveryFragment);
        }
        if (mainMineFragment != null) {
            transaction.hide(mainMineFragment);
        }
    }


}
