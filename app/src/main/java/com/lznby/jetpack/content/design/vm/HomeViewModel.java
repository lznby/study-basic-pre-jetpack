package com.lznby.jetpack.content.design.vm;

import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.alibaba.oss.app.Config;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.content.design.alibaba.oss.utils.StsGetter;
import com.lznby.jetpack.content.design.ui.CenterActivity;
import com.lznby.jetpack.content.design.ui.MainHomeFragment;
import com.lznby.jetpack.utils.TimeUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class HomeViewModel extends BaseFragmentViewModel<MainHomeFragment,CenterActivity,Object> {
    @Override
    public void onCreate() {
        super.onCreate();
        // 获取OSS存储地址
//        getOssToken();
    }

    private void getOssToken() {
        getFragment()
                .addDisposable(
                        IApplication.api.getOssSts()
                        .doOnNext(this::getUrl)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::doOnNext,Throwable::printStackTrace)
                );
    }

    private void doOnNext(BaseEntity<StsModel> data) {
        if (data.getData()!=null) {
            Toast.makeText(getFragment().getContext(),data.getData().getSecurityToken(),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取Oss动态生成的图片Url
     * @param data
     */
    private void getUrl(BaseEntity<StsModel> data) {
        OSSCredentialProvider ossCredentialProvider = new StsGetter(new OSSFederationToken(
                data.getData().getAccessKeyId(),
                data.getData().getAccessKeySecret(),
                data.getData().getSecurityToken(),
                TimeUtils.date2TimeStamp(data.getData().getExpiration())
        ));

        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(5);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);

        OSS oss = new OSSClient(getActivityContent(),Config.endpoint,ossCredentialProvider,conf);
        long timeEx = 60*1;
        try {
            String url = oss.presignConstrainedObjectURL(Config.bucket,"cheess.png",timeEx);
            System.out.println("转换后地址："+url);
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println("转化URL失败");
        }
    }



}
