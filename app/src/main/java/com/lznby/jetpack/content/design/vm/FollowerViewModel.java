package com.lznby.jetpack.content.design.vm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.FollowerRouterEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;
import com.lznby.jetpack.content.design.ui.FollowerActivity;
import com.lznby.jetpack.utils.SpUtil;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class FollowerViewModel extends BaseActivityViewModel<FollowerActivity,List<UserFollowerInfoEntity>> {

    private FollowerRouterEntity params;

    public void getData() {
        if (params.getType().equals(FollowerRouterEntity.FOLLOW)) {
            getFollows(params.getUserId(),CacheConfigure.getUserId(activity.getActivity()),params.getWho(), params.getType());
        } else {
            getFollowers(params.getUserId(),params.getWho(), params.getType());
        }
    }

    private void getFollowers(String userId, String who, String type) {

        // 1.get data from cache.
        if (who.equals(Configure.RouterType.MINE)&&type.equals(FollowerRouterEntity.FOLLOWER)) {
            List<UserFollowerInfoEntity> entities = null;
            Gson gson = new Gson();
            String cacheString = SpUtil.getValue(activity.getActivity(), Configure.SpCache.SP_FOLLOWER_CONTENT,"");
            if (!"".equals(cacheString)) {
                entities = gson.fromJson(cacheString,new TypeToken<List<UserFollowerInfoEntity>>(){}.getType());
            }
            if (entities != null) {
                getLiveData().postValue(entities);
            }
        }
        // 2.get data from network.
        activity.getActivity()
                .addDisposable(
                        IApplication.api.getFollowers(CacheConfigure.getToken(activity.getActivity()),userId,CacheConfigure.getUserId(activity.getActivity()))
                        .doOnNext(o->doCache(o,who,type))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext,Throwable::printStackTrace)
                );
    }

    private void getFollows(String userId, String queryId, String who, String type) {

        // 1.get data from cache.
        if (who.equals(Configure.RouterType.MINE)&&type.equals(FollowerRouterEntity.FOLLOW)) {
            List<UserFollowerInfoEntity> entities = null;
            Gson gson = new Gson();
            String cacheString = SpUtil.getValue(activity.getActivity(), Configure.SpCache.SP_FOLLOW_CONTENT,"");
            if (!"".equals(cacheString)) {
                entities = gson.fromJson(cacheString,new TypeToken<List<UserFollowerInfoEntity>>(){}.getType());
            }
            if (entities != null) {
                getLiveData().postValue(entities);
            }
        }
        // 2.get data from network.
        activity.getActivity()
                .addDisposable(
                        IApplication.api.getFollows(CacheConfigure.getToken(activity.getActivity()),userId,queryId)
                                .doOnNext(o->doCache(o,who,type))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::doOnNext,Throwable::printStackTrace)
                );
    }

    private void doOnNext(BaseEntity<List<UserFollowerInfoEntity>> entity) {
        getLiveData().setValue(entity.getData());
    }

    private void doCache(BaseEntity<List<UserFollowerInfoEntity>> entity, String who, String type) {
        if (entity.getData() != null) {
            Gson gson = new Gson();
            if (who.equals(Configure.RouterType.MINE)&&type.equals(FollowerRouterEntity.FOLLOW)) {
                //1.只存自己的关注信息
                SpUtil.putValue(activity.getActivity(), Configure.SpCache.SP_FOLLOW_CONTENT,gson.toJson(entity.getData()));
            } else if (who.equals(Configure.RouterType.MINE)&&type.equals(FollowerRouterEntity.FOLLOWER)) {
                //2.只存自己的粉丝信息
                SpUtil.putValue(activity.getActivity(), Configure.SpCache.SP_FOLLOWER_CONTENT,gson.toJson(entity.getData()));
            }
        }
    }

    /**
     * 点击关注|取消关注后的响应事件
     *
     * @param entity
     * @param isFollow
     */
    public void updateUi(BaseEntity entity, boolean isFollow) {
        if (isFollow) {
            if (entity.getCode() == Configure.ResponseCode.SUCCESS) {
                ToastUtils.shortToast(activity.getActivity(),"关注成功");
                getData();
            } else {
                ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
            }
        } else {
            if (entity.getCode() == Configure.ResponseCode.SUCCESS) {
                ToastUtils.shortToast(activity.getActivity(),"取消成功");
                getData();
            } else {
                ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
            }
        }
    }

    /**
     * 关注
     *
     * @param item
     */
    public void follow(UserFollowerInfoEntity item) {
        addDisposable(
                IApplication.api.follow(CacheConfigure.getToken(activity.getActivity()), FollowerRouterEntity.FOLLOWER.equals(params.getType())?item.getUserId():item.getFollowId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUi(o,true),Throwable::printStackTrace)
        );
    }

    /**
     * 取消关注
     *
     * @param item
     */
    public void unFollower(UserFollowerInfoEntity item) {
        addDisposable(
                IApplication.api.unFollower(CacheConfigure.getToken(activity.getActivity()),CacheConfigure.getUserId(activity.getActivity()),FollowerRouterEntity.FOLLOWER.equals(params.getType())?item.getUserId():item.getFollowId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUi(o, false),Throwable::printStackTrace)
        );
    }

    public void setParams(FollowerRouterEntity params) {
        this.params = params;
    }
}
