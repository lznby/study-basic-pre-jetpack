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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class FollowerViewModel extends BaseActivityViewModel<FollowerActivity,List<UserFollowerInfoEntity>> {

    public void getData(FollowerRouterEntity entity) {
        if (entity.getType().equals(FollowerRouterEntity.FOLLOW)) {
            getFollows(entity.getUserId(),CacheConfigure.getUserId(activity.getActivity()),entity.getWho(), entity.getType());
        } else {
            getFollowers(entity.getUserId(),entity.getWho(), entity.getType());
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

}
