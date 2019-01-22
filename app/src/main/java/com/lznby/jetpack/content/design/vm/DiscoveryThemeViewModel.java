package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.DiscoveryThemeFragment;
import com.lznby.jetpack.net.transform.RestfulTransformer;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 发现-主题-Fragment-ViewModel
 *
 * @author Lznby
 */
public class DiscoveryThemeViewModel extends BaseFragmentViewModel<DiscoveryThemeFragment,CenterActivity,List<ThemeEntity>> {

    @Override
    public void onResume() {
        super.onResume();
        getThemeInfo();
    }

    /**
     * 获取所有主题
     */
    private void getThemeInfo() {
        addDisposable(
                IApplication.api.findAllTheme(CacheConfigure.getToken(getActivityContent()))
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<ThemeEntity> themeEntities) {
        getLiveData().postValue(themeEntities);
    }

    /**
     * 订阅主题
     *
     * @param themeId       订阅主题Id
     * @param position      订阅主题在员RecyclerView中位置
     */
    public void followTheme(String themeId, int position) {
        addDisposable(
                IApplication.api.followTheme(CacheConfigure.getToken(getActivityContent()),themeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->updateUI(o,true,position),Throwable::printStackTrace)
        );
    }


    /**
     * 取消订阅主题
     *
     * @param themeId       取消订阅主题Id
     * @param position      取消订阅主题在员RecyclerView中位置
     */
    public void unFollowTheme(String themeId, int position) {
        addDisposable(
                IApplication.api.unfollowTheme(CacheConfigure.getToken(getActivityContent()),themeId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,false,position),Throwable::printStackTrace)
        );
    }

    /**
     * 取消订阅更新结果
     *
     * @param entity            请求结果
     * @param isFollow          true:订阅请求结果|false:取消订阅请求结果
     * @param position          请求对象在原队列中位置
     */
    private void updateUI(BaseEntity entity,boolean isFollow,int position) {
        if (Objects.requireNonNull(getLiveData().getValue()).size()>position  && entity.getCode()==Configure.ResponseCode.SUCCESS) {
            if (isFollow) {
                getLiveData().getValue().get(position).setFollowed(true);
                getLiveData().getValue().get(position).setSubCount(getLiveData().getValue().get(position).getSubCount()+1);
            } else {
                getLiveData().getValue().get(position).setFollowed(false);
                getLiveData().getValue().get(position).setSubCount(getLiveData().getValue().get(position).getSubCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(getActivityContent(),entity.getMessage());
        }
    }


}
