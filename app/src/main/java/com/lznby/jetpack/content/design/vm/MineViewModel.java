package com.lznby.jetpack.content.design.vm;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerSizeEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.MainMineFragment;
import com.lznby.jetpack.utils.SpUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class MineViewModel extends BaseFragmentViewModel<MainMineFragment, CenterActivity, LoginEntity> {

    private MutableLiveData<UserFollowerSizeEntity> followerSizeLiveData;

    public void getMineUserInfo(String userCookies, String userId) {
        //用户头像及昵称签名——本地
        getLiveData().postValue(CacheConfigure.getUserEntity(getActivityContent()));
        //关注、粉丝数——本地
        getFollowerSizeLiveData().postValue((UserFollowerSizeEntity) CacheConfigure.getSpEntity(getActivityContent(), Configure.SpCache.SP_FOLLOWER_SIZE, UserFollowerSizeEntity.class));
        //获取动态、关注及粉丝——先本地在网络更新
        addDisposable(
                IApplication.api.getUserFollowerSize(userCookies, userId)
                        .doOnNext(this::doCache)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<UserFollowerSizeEntity> entity) {
        getFollowerSizeLiveData().setValue(entity.getData());
    }

    private void doCache(BaseEntity<UserFollowerSizeEntity> entity) {
        if (entity.getData() != null) {
            Gson gson = new Gson();
            SpUtil.putValue(activity.getActivity(), Configure.SpCache.SP_FOLLOWER_SIZE, gson.toJson(entity.getData()));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMineUserInfo(CacheConfigure.getToken(getActivityContent()), CacheConfigure.getUserId(getActivityContent()));
    }

    public MutableLiveData<UserFollowerSizeEntity> getFollowerSizeLiveData() {
        if (followerSizeLiveData == null) {
            followerSizeLiveData = new MutableLiveData<>();
        }
        return followerSizeLiveData;
    }
}
