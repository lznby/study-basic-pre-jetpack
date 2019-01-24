package com.lznby.jetpack.content.design.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.utils.LoaderImageUtils;

/**
 * 发现-用户-RecyclerView-Adapter
 *
 * @author Lznby
 */
public class DiscoveryUserAdapter extends BaseQuickAdapter<UserBaseInfoEntity, BaseViewHolder> {

    public DiscoveryUserAdapter() {
        super(R.layout.item_discovery_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBaseInfoEntity item) {
        helper.setText(R.id.tv_nickname,item.getUserNickName());
        helper.setText(R.id.tv_theme_note,item.getUserMotto());
        helper.setText(R.id.tv_city, item.getUserCity());
        helper.setText(R.id.itv_love_count, String.valueOf(item.getFollowerCount()));
        if ("男".equals(item.getUserSex())) {
            helper.setImageResource(R.id.iv_sex, R.mipmap.icon_sex_boy);
        } else if ("女".equals(item.getUserSex())) {
            helper.setImageResource(R.id.iv_sex, R.mipmap.icon_sex_girl);
        } else {
            helper.setImageResource(R.id.iv_sex, R.mipmap.icon_sex_what);
        }
        if (item.isFollow()) {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_red),null,null,null);
        } else {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_gray),null,null,null);
        }
        LoaderImageUtils.loaderUrlCenterImage(mContext,item.getUserHeaderUrl(),helper.getView(R.id.iv_image));
        helper.addOnClickListener(R.id.itv_love_count);

    }
}
