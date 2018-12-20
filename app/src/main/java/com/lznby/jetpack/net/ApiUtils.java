package com.lznby.jetpack.net;

import android.content.Context;

import com.lznby.jetpack.BuildConfig;
import com.lznby.jetpack.content.design.configure.Api;
import com.lznby.jetpack.utils.CacheUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Lznby
 */
public enum ApiUtils {

    /**
     *
     */
    INSTANCE;

    public Api getApi(Context context) {
        UserInterceptor userInterceptor = new UserInterceptor(context);
        OkHttpClient client = provideOkHttpClient(context).newBuilder()
                .addInterceptor(userInterceptor).build();
        return new Retrofit.Builder()
                .baseUrl(Api.HOST)
                //使用Gson作为数据转换器
                .addConverterFactory(GsonConverterFactory.create())
                //使用RxJava2作为回调适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                //添加网络访问工具OKHttpClient
                .callFactory(client)
                .build()
                .create(Api.class);
    }

    private OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                //超时时间
                .readTimeout(30, TimeUnit.SECONDS)
                // HTTP过滤器 方便日志打印查看
                .addInterceptor(new HttpLoggingInterceptor())
                // 添加GET请求缓存,缓存大小为10Mib
                .cache(CacheUtils.getCache(context, 10))
                .build().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return httpClientBuilder.build();
    }

}
