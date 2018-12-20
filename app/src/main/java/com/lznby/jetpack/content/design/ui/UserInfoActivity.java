package com.lznby.jetpack.content.design.ui;

import android.arch.lifecycle.Observer;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.view.TitleEditText;
import com.lznby.jetpack.content.design.vm.UserInfoViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lznby
 */
public class UserInfoActivity extends BaseActivity<UserInfoViewModel, UserBaseInfoEntity> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tet_user_id)
    TitleEditText mTetUserId;
    @BindView(R.id.tet_nick_name)
    TitleEditText mTetNickName;
    @BindView(R.id.tet_sex)
    TitleEditText mTetSex;
    @BindView(R.id.tet_birthday)
    TitleEditText mTetBirthday;
    @BindView(R.id.tet_age)
    TitleEditText mTetAge;
    @BindView(R.id.tet_country)
    TitleEditText mTetCountry;
    @BindView(R.id.tet_city)
    TitleEditText mTetCity;
    @BindView(R.id.tet_school)
    TitleEditText mTetSchool;
    @BindView(R.id.tet_eduction)
    TitleEditText mTetEduction;
    @BindView(R.id.tet_email)
    TitleEditText mTetEmail;
    @BindView(R.id.tet_tel)
    TitleEditText mTetTel;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;

    @Override
    protected int setLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void doOnCreate() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.icon_left_arrows);

        final Observer<Boolean> isEditObserver = this::enable;
        viewModel.getIsEditLiveData().observe(this, isEditObserver);

        viewModel.getUserBaseInfo(CacheConfigure.getToken(this));
    }

    @Override
    protected void bindView(UserBaseInfoEntity entity) {
        mTetUserId.setContent(entity.getUserId());
        mTetNickName.setContent(entity.getUserNickName());
        mTetSex.setContent(entity.getUserSex());
        mTetBirthday.setContent(entity.getUserBirthday());
        mTetAge.setContent(String.valueOf(entity.getUserAge()));
        mTetCountry.setContent(entity.getUserCounty());
        mTetCity.setContent(entity.getUserCity());
        mTetSchool.setContent(entity.getUserSchool());
        mTetEduction.setContent(entity.getUserEdu());
        mTetEmail.setContent(entity.getUserEmail());
        mTetTel.setContent(entity.getUserTel());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * control editText enable be edited.
     *
     * @param isEdit
     */
    private void enable(boolean isEdit) {
        mTetNickName.setEnableEdit(isEdit);
        mTetSex.setEnableEdit(isEdit);
        mTetBirthday.setEnableEdit(isEdit);
        mTetAge.setEnableEdit(isEdit);
        mTetCountry.setEnableEdit(isEdit);
        mTetCity.setEnableEdit(isEdit);
        mTetSchool.setEnableEdit(isEdit);
        mTetEduction.setEnableEdit(isEdit);
        mTetEmail.setEnableEdit(isEdit);
        mTetTel.setEnableEdit(isEdit);
        mTvEdit.setText(isEdit?R.string.save:R.string.edit);
    }


    @OnClick({R.id.tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                viewModel.saveUserBaseInfo(getSaveData());
                break;
            default:
                break;
        }
    }

    private Map<String,String> getSaveData() {
        Map<String,String> map = new HashMap<>(10);
        map.put("userNickName",mTetNickName.getContent());
        map.put("userSex",mTetSex.getContent());
        map.put("userBirthday",mTetBirthday.getContent());
        map.put("userAge",mTetAge.getContent());
        map.put("userCounty",mTetCountry.getContent());
        map.put("userCity",mTetCity.getContent());
        map.put("userSchool",mTetSchool.getContent());
        map.put("userEdu",mTetEduction.getContent());
        map.put("userEmail",mTetEmail.getContent());
        map.put("userTel",mTetTel.getContent());
        return map;
    }
}
