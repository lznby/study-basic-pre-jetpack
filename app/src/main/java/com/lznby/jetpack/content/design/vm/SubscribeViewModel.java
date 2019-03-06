package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.MainSubscribeFragment;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Lznby
 */
public class SubscribeViewModel extends BaseFragmentViewModel<MainSubscribeFragment,CenterActivity,BaseEntity<List<ArticleAllInfoEntity>>> {

    @Override
    public void onCreate() {
        super.onCreate();
        getSubInfo();
    }

    public void getSubInfo() {
        addDisposable(
                IApplication.api.getUserSubInfo(CacheConfigure.getToken(getActivityContent()))
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(BaseEntity<List<ArticleAllInfoEntity>> entity) {
        getLiveData().postValue(entity);
    }

    /**
     * 收藏资讯
     *
     * @param fileAttribution       资讯Id
     * @param position              资讯位于RecyclerView中位置
     */
    public void articleSub(String fileAttribution, int position) {
        addDisposable(
                IApplication.api.articleSub(CacheConfigure.getToken(getActivityContent()), fileAttribution)
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
                IApplication.api.articleUnSub(CacheConfigure.getToken(getActivityContent()), fileAttribution)
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
        if (Objects.requireNonNull(getLiveData().getValue()).getData().size()>position && entity.getCode()== Configure.ResponseCode.SUCCESS) {
            if (isLove) {
                getLiveData().getValue().getData().get(position).setLove(true);
                getLiveData().getValue().getData().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().getData().get(position).getArticleEntity().getLoveCount()+1);
            } else {
                getLiveData().getValue().getData().get(position).setLove(false);
                getLiveData().getValue().getData().get(position).getArticleEntity().setLoveCount(getLiveData().getValue().getData().get(position).getArticleEntity().getLoveCount()-1);
            }
            getLiveData().postValue(getLiveData().getValue());
        } else {
            ToastUtils.shortToast(getActivityContent(),entity.getMessage());
        }
    }
}
