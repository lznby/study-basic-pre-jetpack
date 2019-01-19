package com.lznby.jetpack.content.design.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.base.BaseFragmentViewModel;
import com.lznby.jetpack.content.design.entity.ImageWatcherItemEntity;
import com.lznby.jetpack.content.design.entity.ImageWatcherRouterEntity;
import com.lznby.jetpack.utils.LoaderImageUtils;

import butterknife.BindView;

/**
 * 图片浏览Fragment
 *
 * @author Lznby
 */
public class ImageWatcherFragment extends BaseFragment<BaseFragmentViewModel,ImageWatcherActivity,Object> {

    private ImageWatcherItemEntity params;

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_position)
    TextView tvPosition;

    public ImageWatcherFragment() {
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_image_watcher;
    }

    @Override
    protected void bindView(Object entity) {

    }

    @Override
    protected void doOnCreateView() {
        Bundle args = getArguments();
        if (args != null) {
            params = args.getParcelable(ImageWatcherRouterEntity.KEY);
        }
        LoaderImageUtils.loaderUrlImage(getContext(),params.getUrl(),ivImage);
        tvPosition.setText((params.getPosition()+1)+"/"+params.getSize());
    }

    public static ImageWatcherFragment newInstance(ImageWatcherItemEntity params) {
        Bundle args = new Bundle();
        args.putParcelable(ImageWatcherRouterEntity.KEY,params);
        ImageWatcherFragment fragment = new ImageWatcherFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
