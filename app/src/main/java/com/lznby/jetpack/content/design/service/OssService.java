package com.lznby.jetpack.content.design.service;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.google.gson.Gson;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.alibaba.oss.app.Config;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.utils.SpUtil;
import com.lznby.jetpack.utils.TimeUtils;

/**
 * 这里是Oss获取Sts认证动态令牌的服务,占时不写到后期功能完善后再写。
 * @author Lznby
 */
public class OssService {

    /**
     * 获取OSS图片地址
     * @param context
     * @return
     */
    public static String getUrl (Context context,String fileName) {
        return getUrl(getStsToken(context),context,fileName);
    }


    //1.  判断本地的 StsToken 是否已缓存
    //1.1 未缓存或缓存已经过期 则重新请求
    //2.  已经有缓存者返回

    /**
     * 网络获取StsToken
     * @param context
     */
    public static void getApiStsToken(Context context) {
        IApplication.api.getOssSts()
                .doOnNext(data->doCache(data,context))
                .subscribe();
    }

    /**
     * 缓存StsToken
     * @param entity
     * @param context
     */
    private static void doCache(BaseEntity<StsModel> entity,Context context) {
        if (entity.getData()!=null) {
            Gson gson = new Gson();
            SpUtil.putValue(context, Configure.SpCache.SP_OSS_TOKEN,gson.toJson(entity.getData()));
        }
    }

    /**
     * 获取StsToken
     * @param context
     * @return
     */
    public static StsModel getStsToken(Context context) {
        String cacheJson = SpUtil.getValue(context,Configure.SpCache.SP_OSS_TOKEN,"");
        Gson gson = new Gson();
        if (cacheJson.equals("")){
            getApiStsToken(context);
            return null;
        } else {
            StsModel stsModel = gson.fromJson(cacheJson,StsModel.class);
            // 判断是否过期
            if (TimeUtils.date2TimeStamp(stsModel.getExpiration())>System.currentTimeMillis()) {
                return stsModel;
            } else {
                return null;
            }
        }
    }


    private static String getUrl(StsModel data,Context context, String fileName) {
        if (data == null) {
            return null;
        }
        OSSCredentialProvider ossCredentialProvider = new StsGetter(new OSSFederationToken(
                data.getAccessKeyId(),
                data.getAccessKeySecret(),
                data.getSecurityToken(),
                TimeUtils.date2TimeStamp(data.getExpiration())
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

        OSS oss = new OSSClient(context,Config.endpoint,ossCredentialProvider,conf);
        // 设置缓存事件
        long timeEx = 60*1;
        try {
            String url = oss.presignConstrainedObjectURL(Config.bucket,fileName,timeEx);
            return url;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * STSToken获取类
     */
    static class StsGetter extends OSSFederationCredentialProvider {

        OSSFederationToken oSSFederationToken;

        public StsGetter(OSSFederationToken oSSFederationToken) {
            this.oSSFederationToken = oSSFederationToken;
        }
        @Override
        public OSSFederationToken getFederationToken() {
            return oSSFederationToken;
        }

    }
}
