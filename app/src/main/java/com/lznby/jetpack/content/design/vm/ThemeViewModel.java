package com.lznby.jetpack.content.design.vm;

import com.lznby.jetpack.base.BaseActivityViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.entity.ThemeRouterEntity;
import com.lznby.jetpack.content.design.ui.ThemeActivity;
import com.lznby.jetpack.net.transform.RestfulTransformer;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * 主题列表页面
 *
 * @author Lznby
 */
public class ThemeViewModel extends BaseActivityViewModel<ThemeActivity,List<ThemeEntity>> {

    private ThemeRouterEntity params;

    public void setParams(ThemeRouterEntity params) {
        this.params = params;
    }

    private void getThemeInfo() {
        activity.getActivity().addDisposable(
                IApplication.api.findAllFlowThemeInfo(CacheConfigure.getToken(activity.getActivity()),params.getUserId())
                .compose(new RestfulTransformer<>())
                .observeOn(Schedulers.io())
                .subscribe(this::doOnNext,Throwable::printStackTrace)
        );
    }

    private void doOnNext(List<ThemeEntity> themeEntities) {
        getLiveData().postValue(themeEntities);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getThemeInfo();
    }
}
