package com.lznby.jetpack.content.design.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.configure.GlideApp;
import com.lznby.jetpack.content.design.entity.FileEntity;

/**
 * 选择图片预览截图
 *
 * @author Lznby
 */
public class SelectorAdapter extends BaseQuickAdapter<FileEntity, BaseViewHolder> {

    public SelectorAdapter() {
        super(R.layout.item_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileEntity item) {
        GlideApp.with(mContext).load(item.getUrl()).centerCrop().into((ImageView) helper.getView(R.id.fiv));
        helper.addOnClickListener(R.id.ll_del);
    }
}
