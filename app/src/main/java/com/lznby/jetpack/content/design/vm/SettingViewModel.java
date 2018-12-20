package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.ui.SettingActivity;
import com.lznby.jetpack.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 * @time 2018/11/5 16:13
 * Class Note:
 */
public class SettingViewModel extends BaseActivityViewModel<SettingActivity,Object> {


    /**
     * user login out.
     */
    public void loginOut() {
        activity.getActivity()
                .addDisposable(
                        IApplication.api.loginOut(CacheConfigure.getToken(activity.getActivity()))
                        .doOnNext(this::clearAllCache)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
                );
    }

    private void doOnNext(BaseEntity<Object> entity) {
        if (entity.getCode() == Configure.ResponseCode.SUCCESS) {
            ToastUtils.shortToast(activity.getActivity(),"登出成功！");
        } else {
            ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
        }
    }

    /**
     * clear all date cache.
     * @param entity
     */
    private void clearAllCache(BaseEntity<Object> entity) {
        if (entity.getCode() == Configure.ResponseCode.SUCCESS || entity.getCode() == Configure.ResponseCode.UN_LOGIN) {
            CacheConfigure.clearAll(activity.getActivity());
        }
    }
}
