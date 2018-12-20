package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.ResponseUiConfigure;
import com.lznby.jetpack.content.design.params.RegisterParams;
import com.lznby.jetpack.content.design.ui.RegisterActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class RegisterViewModel extends BaseActivityViewModel<RegisterActivity, BaseEntity<Object>> {

    /**
     * 注册请求
     * 1.这里是注册请求
     *
     * @param params
     */
    public void register(RegisterParams params) {
        activity.getActivity().addDisposable(
                IApplication.api.register(params.getUserNickName(), params.getUserPassword())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    /**
     * 更新LiveData的值
     * 1.这里是网络请求结束的处理的具体逻辑
     *
     * @param entity
     */
    private void doOnNext(BaseEntity<Object> entity) {
        getLiveData().postValue(entity);
        ResponseUiConfigure.responseUtils(entity,activity.getActivity());
    }



}
