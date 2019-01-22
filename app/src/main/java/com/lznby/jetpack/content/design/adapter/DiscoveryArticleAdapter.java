package com.lznby.jetpack.content.design.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.utils.LoaderImageUtils;

/**
 * 发现-资讯-RecyclerView-adapter
 *
 * @author Lznby
 */
public class DiscoveryArticleAdapter extends BaseQuickAdapter<ArticleAllInfoEntity, BaseViewHolder> {

    public DiscoveryArticleAdapter() {
        super(R.layout.item_discovery_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleAllInfoEntity item) {
        helper.setText(R.id.tv_nickname, item.getUserBaseInfoEntity().getUserNickName());
        helper.setText(R.id.tv_article_note, item.getArticleEntity().getContent());
        helper.setText(R.id.itv_read_count, String.valueOf(item.getArticleEntity().getReadCount()));
        helper.setText(R.id.itv_comment_count, String.valueOf(item.getArticleEntity().getCommentCount()));
        helper.setText(R.id.itv_love_count, String.valueOf(item.getArticleEntity().getLoveCount()));
        if (item.getFilePathEntities().size() > 0) {
            LoaderImageUtils.loaderUrlCenterImage(mContext,item.getFilePathEntities().get(0).getFilePath(),helper.getView(R.id.iv_image));
            helper.setVisible(R.id.cl_layout,true);
        } else {
            helper.setVisible(R.id.cl_layout,false);
        }
        if (item.isLove()) {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_red),null,null,null);
        } else {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_gray),null,null,null);
        }
        helper.addOnClickListener(R.id.itv_love_count);
        helper.addOnClickListener(R.id.itv_comment_count);
    }
}
