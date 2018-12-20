package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.RouterConfigure;
import com.lznby.jetpack.content.design.params.UpdatePasswordParams;
import com.lznby.jetpack.content.design.ui.AccountManagerActivity;
import com.lznby.jetpack.content.design.ui.LoginActivity;
import com.lznby.jetpack.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 * @time 2018/11/13 15:09
 * Class Note:
 */
public class AccountManagerViewModel extends BaseActivityViewModel<AccountManagerActivity, Object> {

    public void updatePassword(UpdatePasswordParams params) {
        activity.getActivity()
                .addDisposable(
                        IApplication.api.updatePassword(CacheConfigure.getToken(activity.getActivity()), params.getUserPassword(), params.getNewUserPassword(), params.getrUserPassword())
                        .doOnNext(this::doClear)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext,Throwable::printStackTrace)
                );
    }

    private void doOnNext(BaseEntity<Object> entity) {

        if (entity.getCode()==Configure.ResponseCode.UN_LOGIN) {
            ToastUtils.shortToast(activity.getActivity(),"密码修改成功,请重新登录！");
            RouterConfigure.normalRouterUtils(activity.getActivity(),LoginActivity.class);
            activity.getActivity().finish();
        } else {
            ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
        }
    }

    private void doClear(BaseEntity<Object> entity) {
        if (entity.getCode()==Configure.ResponseCode.UN_LOGIN) {
            CacheConfigure.clearAll(activity.getActivity());
        }
    }
}
