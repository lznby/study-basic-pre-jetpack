package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.PersonalHomePageEntity;
import com.lznby.jetpack.content.design.ui.HomePageActivity;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 个人主页ViewModel
 *
 * @author Lznby
 */
public class HomePageViewModel extends BaseActivityViewModel<HomePageActivity, PersonalHomePageEntity> {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初次加载个人主页信息
        getHomePageViewModel(CacheConfigure.getToken(activity.getActivity()),CacheConfigure.getUserId(activity.getActivity()));
    }

    private void getHomePageViewModel(String userCookies, String userId) {
        addDisposable(
                IApplication.api.getPersonalHomePage(userCookies, userId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext, Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<PersonalHomePageEntity> entity) {
        getLiveData().postValue(entity.getData());
    }

    /**
     * 收藏资讯
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,true,position),Throwable::printStackTrace)
        );
    }

    /**
     * 取消资讯收藏
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleUnSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleUnSub(CacheConfigure.getToken(activity.getActivity()), fileAttribution)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o->updateUI(o,false,position),Throwable::printStackTrace)
        );
    }

    /**
     * 收藏与取消更新IU
     *
     * @param entity
     * @param isLove
     * @param position
     */
    private void updateUI(BaseEntity entity, boolean isLove, int position) {
        if (Objects.requireNonNull(getLiveData().getValue()).getArticleAllInfoEntities().size()>position && entity.getCode()== Configure.ResponseCode.SUCCESS) {
            if (isLove) {
                getLiveData().getValue().getArticleAllInfoEntities().get(position).setLove(true);
                getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().getLoveCount()+1);
            } else {
                getLiveData().getValue().getArticleAllInfoEntities().get(position).setLove(false);
                getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().getArticleAllInfoEntities().get(position).getArticleEntity().getLoveCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(activity.getActivity(),entity.getMessage());
        }
    }
}
