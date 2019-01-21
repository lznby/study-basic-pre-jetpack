package com.lznby.jetpack.content.design.vm;

import android.arch.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.ResponseUiConfigure;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.ui.UserInfoActivity;
import com.lznby.jetpack.utils.SpUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * @author Lznby
 */
public class UserInfoViewModel extends BaseActivityViewModel<UserInfoActivity, UserBaseInfoEntity> {

    /**
     * save edit state.
     */
    private MutableLiveData<Boolean> isEditLiveData;

    /**
     * get user base information.
     *
     * @param userCookies
     */
    public void getUserBaseInfo(String userCookies) {

        // read on local cache.
        String cache = SpUtil.getValue(activity.getActivity(), Configure.SpCache.SP_BASE_USER_INFO, "");
        if (!StringUtils.isEmpty(cache)) {
            Gson gson = new Gson();
            getLiveData().postValue(gson.fromJson(cache, UserBaseInfoEntity.class));
        }

        // default edit state was false.
        getIsEditLiveData().postValue(false);

        //get data on network.
        addDisposable(
                IApplication.api.getUserBaseInfo(userCookies)
                        .doOnNext(this::doCache)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    /**
     * update user base information.
     *
     * @param map
     */
    public void saveUserBaseInfo(Map<String, String> map) {

        if (getIsEditLiveData().getValue() != null) {
            getIsEditLiveData().postValue(!getIsEditLiveData().getValue());
            if (getIsEditLiveData().getValue()) {
                addDisposable(
                        IApplication.api.updateUserBaseInfo(CacheConfigure.getToken(activity.getActivity()), map)
                                .doOnNext(this::doCache)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::doOnNext, Throwable::printStackTrace)
                );
            }
        } else {
            getIsEditLiveData().postValue(false);
        }
    }

    /**
     * network ui response.
     *
     * @param entity
     */
    private void doOnNext(BaseEntity<UserBaseInfoEntity> entity) {
        getLiveData().postValue(entity.getData());
        ResponseUiConfigure.responseUtils(entity, activity.getActivity());
    }

    /**
     * save data by sp.
     *
     * @param entity
     */
    private void doCache(BaseEntity<UserBaseInfoEntity> entity) {
        if (entity.getData() != null) {
            Gson gson = new Gson();
            SpUtil.putValue(activity.getActivity(), Configure.SpCache.SP_BASE_USER_INFO, gson.toJson(entity.getData()));
            Timber.e(gson.toJson(entity.getData()));
        }
    }

    /**
     * get isEditLiveData.
     */
    public MutableLiveData<Boolean> getIsEditLiveData() {
        if (isEditLiveData == null) {
            isEditLiveData = new MutableLiveData<>();
        }
        return isEditLiveData;
    }

}
