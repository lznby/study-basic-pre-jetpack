package com.lznby.jetpack.content.design.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.params.UpdatePasswordParams;
import com.lznby.jetpack.content.design.view.TitleEditText;
import com.lznby.jetpack.content.design.vm.AccountManagerViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class AccountManagerActivity extends BaseActivity<AccountManagerViewModel, Object> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tet_old_pass)
    TitleEditText mTetOldPass;
    @BindView(R.id.tet_new_pass)
    TitleEditText mTetNewPass;
    @BindView(R.id.tet_re_pass)
    TitleEditText mTetRePass;

    @Override
    protected int setLayout() {
        return R.layout.activity_account_manager;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
    }

    @Override
    protected void bindView(Object entity) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                viewModel.updatePassword(getParams());
                break;
            default:
                break;
        }
    }

    private UpdatePasswordParams getParams() {
        UpdatePasswordParams params = new UpdatePasswordParams();
        params.setUserPassword(mTetOldPass.getContent());
        params.setNewUserPassword(mTetNewPass.getContent());
        params.setrUserPassword(mTetRePass.getContent());
        return params;
    }
}
