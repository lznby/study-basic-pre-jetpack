package com.lznby.jetpack.content.design.adapter;

import android.widget.Button;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.ThemeEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主题关注Adapter
 *
 * @author Lznby
 */
public class ThemeAdapter extends BaseQuickAdapter<ThemeEntity, BaseViewHolder> {

    public ThemeAdapter() {
        super(R.layout.item_adapter_theme);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeEntity item) {
        helper.setText(R.id.tv_name,item.getThemeName());
        helper.setText(R.id.tv_note,item.getThemeNote());
        Glide.with(mContext).load(item.getThemeHeaderImage()).into((CircleImageView) helper.getView(R.id.civ_header));
        // check is already follow.
        Button btFollow = helper.getView(R.id.bt_follow);
        if (item.isFollowed()) {
            btFollow.setBackgroundResource(R.drawable.follower_gray);
            btFollow.setText("已关注");
        } else {
            btFollow.setBackgroundResource(R.drawable.follower_blue);
            btFollow.setText("关注");
        }
        // 为子控件绑定点击事件
        helper.addOnClickListener(R.id.bt_follow);
    }
}
