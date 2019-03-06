package com.lznby.jetpack.content.design.view.nine;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.lznby.jetpack.configure.GlideApp;
import com.lznby.jetpack.content.design.entity.FilePathEntity;
import com.lznby.jetpack.content.design.entity.ImageWatcherRouterEntity;
import com.lznby.jetpack.content.design.ui.ImageWatcherActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格布局
 *
 * @author Lznby
 */
public class NineGridTestLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, FilePathEntity url, final int parentWidth) {
        // 这边可以单独设置图片的大小
        GlideApp.with(mContext)
                .load(url.getFilePath())
                .skipMemoryCache(false)
//                .dontAnimate()
                .into(imageView);
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, FilePathEntity url) {
        GlideApp.with(mContext)
                .load(url.getFilePath())
                .skipMemoryCache(false)
//                .dontAnimate()
                .into(imageView);
    }

    @Override
    protected void onClickImage(int i, FilePathEntity url, List<FilePathEntity> urlList) {
        List<String> urls = new ArrayList<>();
        for (FilePathEntity single : urlList) {
        	urls.add(single.getFilePath());
        }
        ImageWatcherRouterEntity params = new ImageWatcherRouterEntity();
        params.setPosition(i);
        params.setUrls(urls);
        Intent intent = new Intent(getContext(),ImageWatcherActivity.class);
        intent.putExtra(ImageWatcherRouterEntity.KEY,params);
        getContext().startActivity(intent);
    }
}
