package com.lznby.jetpack.net.transform;

import com.lznby.jetpack.base.BaseEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * @author Lznby
 */
public class RestfulTransformer<T> implements ObservableTransformer<BaseEntity<T>, T> {
    private BaseEntity<T> entity;

    @Override
    public ObservableSource<T> apply(Observable<BaseEntity<T>> upstream) {
        return upstream
                .compose(new ErrorTransform<>())
                .flatMap(this::flat);
    }

    private ObservableSource<? extends T> flat(BaseEntity<T> tInfoEntity) {
        this.entity = tInfoEntity;
        return Observable.create(this::create);
    }


    //todo 明天回来 看方法的调用过程 打断点 看下到底是怎么走的
    private void create(ObservableEmitter<T> emitter) {
        try {
            if (entity.getData() != null) {
                emitter.onNext(entity.getData());
            }
        } catch (Exception e) {
            emitter.onError(e);
        } finally {
            emitter.onComplete();
        }
    }
}