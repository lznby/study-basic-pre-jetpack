package com.lznby.jetpack.content.design.vm;

import com.google.gson.Gson;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.ResponseUiConfigure;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.params.LoginParams;
import com.lznby.jetpack.content.design.ui.LoginActivity;
import com.lznby.jetpack.utils.SpUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class LoginViewModel extends BaseActivityViewModel<LoginActivity, BaseEntity<LoginEntity>> {

    public void login(LoginParams params) {
        addDisposable(
                IApplication.api.login(params.getUserNickName(), params.getUserPassword())
                        .doOnNext(this::doCache)
                        //.compose(new RestfulTransformer<>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<LoginEntity> entity) {
        getLiveData().postValue(entity);
        ResponseUiConfigure.responseUtils(entity, activity.getActivity());
    }

    private void doCache(BaseEntity<LoginEntity> entity) {
        if (entity.getData() != null) {
            Gson gson = new Gson();
            SpUtil.putValue(activity.getActivity(), Configure.SpCache.SP_USER_INFO, gson.toJson(entity.getData()));
        }
    }
}
