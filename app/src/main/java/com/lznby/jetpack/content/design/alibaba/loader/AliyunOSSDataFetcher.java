package com.lznby.jetpack.content.design.alibaba.loader;

import android.support.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.InputStream;

/**
 * @author Lznby
 */
public class AliyunOSSDataFetcher implements DataFetcher<InputStream> {

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return null;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return null;
    }
}
