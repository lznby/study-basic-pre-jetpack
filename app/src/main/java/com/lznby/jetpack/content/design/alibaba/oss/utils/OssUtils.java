package com.lznby.jetpack.content.design.alibaba.oss.utils;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.alibaba.oss.app.Config;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.TimeUtils;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Oss 二次封装工具类
 *
 * @author Lznby
 */
public class OssUtils {

    /**
     * 获取私有OSS图片地址(通过STS口令动态生成)
     *
     * @param context           上下文
     * @param data              Sts信息
     */
    public static void getUrl(Context context, BaseEntity<StsModel> data) {
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

        OSS oss = new OSSClient(context,Config.endpoint,ossCredentialProvider,conf);
        long timeEx = 60*1;
        try {
            String url = oss.presignConstrainedObjectURL(Config.bucket,"cheess.png",timeEx);
            System.out.println("转换后地址："+url);
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println("转化URL失败");
        }
    }

    public void getOssToken(BaseFragment fragment) {
        fragment
                .addDisposable(
                        IApplication.api.getOssSts()
                                .doOnNext(o->getUrl(fragment.getContext(),o))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(o->doOnNext(o,fragment),Throwable::printStackTrace)
                );
    }

    private void doOnNext(BaseEntity<StsModel> data,BaseFragment fragment) {
        if (data.getData()!=null) {
            Toast.makeText(fragment.getContext(),data.getData().getSecurityToken(),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 同步上传文件
     *
     * @param context           上下文
     * @param sts               Sts动态口令
     * @param path              上传文件的路径
     * @return
     */
    public static String uploadImage(Context context,StsModel sts,String path) {
        // 1.根据Sts信息创建Oss创建凭证提供者
        OSSCredentialProvider ossCredentialProvider = new StsGetter(new OSSFederationToken(
                sts.getAccessKeyId(),
                sts.getAccessKeySecret(),
                sts.getSecurityToken(),
                TimeUtils.date2TimeStamp(sts.getExpiration())
        ));
        // 2.配置Oss服务请求设置
        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(5);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);

        // 3.创建Oss客户端对象
        OSS oss = new OSSClient(context,Config.endpoint,ossCredentialProvider,conf);


        // PutObjectRequest
        // 1.配置存放的bucket位置; 2.配置上传文件的文件名及类型; 3.配置上传文件的本地路径
        String uuid = UUID.randomUUID().toString();
        String uploadFileKey = uuid+"."+FileUtils.getSubType(path);
        PutObjectRequest putObjectRequest = new PutObjectRequest(Config.bucket,uploadFileKey,path);

        // 5.开始上传文件(使用同步的方式)
        try {
            PutObjectResult result = oss.putObject(putObjectRequest);
            Timber.e("OSS同步请求返回结果:%s",result.getServerCallbackReturnBody());
        } catch (ClientException e) {
            // 客户端异常
            Timber.e("OSS上传客户端异常:%s",e.getMessage());
            uploadFileKey = null;
        } catch (ServiceException e) {
            // 服务器异常
            Timber.e("OSS上传服务器异常:%s",e.getMessage());
            uploadFileKey = null;
        }

        //        // 使用同步方式上传
        //        // OSSCompletedCallback
        //        // 1.配置文件上传结果回调
        //        OSSCompletedCallback completedCallback = new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
        //            @Override
        //            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
        //                // PutObjectRequest (上传请求对象)
        //                Timber.e("文件上传路径:%s",request.getUploadFilePath());
        //
        //                // PutObjectResult (上传结果对象)
        //                Timber.e("文件上传结果:%s,result.getETag():%s",result.getServerCallbackReturnBody(),result.getETag());
        //            }
        //
        //            @Override
        //            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
        //                // PutObjectRequest
        //                Timber.e("上传文件名称:%s",request.getObjectKey());
        //                // ClientException
        //                Timber.e("客户端异常:%s",clientException.getMessage());
        //                // ServiceException
        //                Timber.e("服务器异常:%s", serviceException.getRawMessage());
        //            }
        //        };
        //
        //        // 4.开始上传文件(使用异步的方式)
        //        oss.asyncPutObject(putObjectRequest,completedCallback);

        return uploadFileKey;

    }

}
