package com.lznby.jetpack.content.design.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.utils.LoaderImageUtils;

/**
 * 订阅-用户关注或订阅过主题下所有资讯
 *
 * @author Lznby
 */
public class SubScribeArticleAdapter extends BaseQuickAdapter<ArticleAllInfoEntity, BaseViewHolder> {

    public SubScribeArticleAdapter() {
        super(R.layout.item_subscribe_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleAllInfoEntity item) {
        if (item.getThemeEntities() != null) {
            if (item.getThemeEntities().size() > 0) {
                helper.setVisible(R.id.tv_theme,true);
                helper.setText(R.id.tv_theme,item.getThemeEntities().get(0).getThemeName());
            } else {
                helper.setVisible(R.id.tv_theme,false);
            }
        } else {
            helper.setVisible(R.id.tv_theme,false);
        }
        helper.setText(R.id.tv_nickname,item.getUserBaseInfoEntity().getUserNickName());
        helper.setText(R.id.tv_article_note,item.getArticleEntity().getContent());
        helper.setText(R.id.itv_love_count,String.valueOf(item.getArticleEntity().getLoveCount()));
        if (item.isLove()) {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_red),null,null,null);
        } else {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_gray),null,null,null);
        }
        if (item.getFilePathEntities() != null) {
            if (item.getFilePathEntities().size() > 0) {
                LoaderImageUtils.loaderUrlCenterImage(mContext,item.getFilePathEntities().get(0).getFilePath(),helper.getView(R.id.iv_image));
                helper.setGone(R.id.cl_layout,true);
            } else {
                helper.setGone(R.id.cl_layout,false);
            }
        } else {
            helper.setGone(R.id.cl_layout,false);
        }
        helper.addOnClickListener(R.id.itv_love_count);

    }
}
