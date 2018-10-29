package com.lznby.jetpack.base.net.transform;

import com.lznby.jetpack.base.entity.BaseEntity;
import com.lznby.jetpack.base.net.NetWork;
import com.lznby.jetpack.base.net.exception.ApiException;
import com.lznby.jetpack.base.net.exception.TokenExpireException;

import org.json.JSONException;

import java.util.ServiceConfigurationError;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author Lznby
 */
public class ErrorTransform<T> implements ObservableTransformer<T,T> {
    private int code = 1;
    private String errorMessage = "";
    private BaseEntity entity;

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(this::onThrowable)
                .flatMap(this::flat);
    }


    // private <R> ObservableSource<? extends R> flat(T t) {
    private ObservableSource<T> flat(T t) {
        if (t instanceof BaseEntity) {
            entity = (BaseEntity) t;
            return Observable.create(this::create);
        }
        return Observable.just(t);
    }

    private void create(ObservableEmitter<T> emitter) {
        try {
            switch (entity.getCode()) {
                case NetWork.success:
                    emitter.onNext((T) entity);
                    break;
                case NetWork.tokenExpire:
                    throw new TokenExpireException(entity.getMessage(), entity.getCode());
                case NetWork.logout: /* 这里进行很多登出之后 很多的置空的行为 比如设置登录状态 修改对应的sp文件 */
                    break;
                case NetWork.fail:
                    emitter.onNext((T) entity);
                    break;
                default: throw new ApiException(entity.getMessage(), entity.getCode());
            }
        } catch (Exception e) {
            emitter.onError(e);
        }finally {
            emitter.onComplete();
        }
    }

    private  Observable<T> onThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException response = (HttpException) throwable;
            switch (code = response.code()) {
                //请求要求用户的身份认证
                case 401: errorMessage = "token无效或者过期"+response.message(); break;
                //保留 将来使用
                case 402: errorMessage = "数据库连接错误"+response.message(); break;
                //服务器理解请求客户端的请求，但是拒绝执行此请求
                case 403: errorMessage ="无记录"+response.message();break;
                //服务器无法根据客户管的请求找到资源
                case 404: errorMessage = "URL路径不存在"+response.message();break;
                //客户端请求中的方法被禁止
                case 405: errorMessage = "token无效或者过期"+response.message();break;
                //客户端请求的语法错误，服务器无法理解
                case 400: errorMessage = "参数为空"+response.message();break;
                default: errorMessage="未知错误"+response.message();break;
            }
            throw new ApiException(errorMessage, code);
        } else if (throwable instanceof ServiceConfigurationError) {
            throw new ApiException("服务器错误", 0);
        } else if (throwable instanceof JSONException) {
            throw new ApiException("数据解析错误",0);
        }
        return Observable.error(throwable);
    }
}