package com.lznby.jetpack.content.design.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.utils.LoaderImageUtils;

/**
 * 发现-主题-RecyclerView-Adapter
 *
 * @author Lznby
 */
public class DiscoveryThemeAdapter extends BaseQuickAdapter<ThemeEntity,BaseViewHolder> {

    public DiscoveryThemeAdapter() {
        super(R.layout.item_discovery_theme);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeEntity item) {
        helper.setText(R.id.tv_theme_name,item.getThemeName());
        helper.setText(R.id.tv_theme_note, item.getThemeNote());
        ((ImageTextView)helper.getView(R.id.itv_love_count)).setText(String.valueOf(item.getSubCount()));
        if (item.isFollowed()) {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_red),null,null,null);
        } else {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_gray),null,null,null);
        }
        LoaderImageUtils.loaderUrlCenterImage(mContext,item.getThemeImage(),helper.getView(R.id.iv_image));
        helper.addOnClickListener(R.id.itv_love_count);
    }
}
