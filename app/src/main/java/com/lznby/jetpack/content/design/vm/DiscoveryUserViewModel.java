package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.DiscoveryUserFragment;
import com.lznby.jetpack.net.transform.RestfulTransformer;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 发现-用户-Fragment-ViewModel
 *
 * @author Lznby
 */
public class DiscoveryUserViewModel extends BaseFragmentViewModel<DiscoveryUserFragment,CenterActivity,List<UserBaseInfoEntity>> {

    @Override
    public void onCreate() {
        super.onCreate();
        getUserInfo();
    }

    /**
     * 获取所有用户
     */
    public void getUserInfo() {
        addDisposable(
                IApplication.api.getAllUserBaseInfo(CacheConfigure.getToken(getActivityContent()))
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<UserBaseInfoEntity> entity) {
        getLiveData().postValue(entity);
    }

    public void follow(int userId, int position) {
        addDisposable(
                IApplication.api.follow(CacheConfigure.getToken(getActivityContent()),userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o-> updateUI(o,position,true), Throwable::printStackTrace)
        );
    }

    public void unFollower(int userId, int position) {
        addDisposable(
                IApplication.api.unFollower(CacheConfigure.getToken(getActivityContent()),CacheConfigure.getUserId(getActivityContent()),userId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o-> updateUI(o,position,false), Throwable::printStackTrace)
        );
    }

    private void updateUI(BaseEntity entity, int position, boolean isFollow) {
        if (Objects.requireNonNull(getLiveData().getValue()).size()>position && entity.getCode()==Configure.ResponseCode.SUCCESS) {
            if (isFollow) {
                getLiveData().getValue().get(position).setFollow(true);
                getLiveData().getValue().get(position).setFollowerCount(getLiveData().getValue().get(position).getFollowerCount()+1);
            } else {
                getLiveData().getValue().get(position).setFollow(false);
                getLiveData().getValue().get(position).setFollowerCount(getLiveData().getValue().get(position).getFollowerCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(getActivityContent(),entity.getMessage());
        }

    }
}
