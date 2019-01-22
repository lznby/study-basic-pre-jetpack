package com.lznby.jetpack.content.design.adapter;

import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.content.design.view.nine.NineGridTestLayout;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.LoaderImageUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 图片展示控件
 *
 * @author Lznby
 */
public class ArticleItemAdapter extends BaseQuickAdapter<ArticleAllInfoEntity, BaseViewHolder> {

    public ArticleItemAdapter() {
        super(R.layout.item_article);
        // 1.防止数据变更时闪烁
        this.setHasStableIds(true);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleAllInfoEntity item) {
        LoaderImageUtils.loaderCircleImageView(mContext, item.getUserBaseInfoEntity().getUserHeaderUrl(), Configure.DefaultValue.DEFAULT_IAMGE_RES, (CircleImageView) helper.getView(R.id.iv_image));
        helper.setText(R.id.tv_nickname, item.getUserBaseInfoEntity().getUserNickName());
        helper.setText(R.id.tv_create_time, item.getArticleEntity().getTime());
        helper.setText(R.id.tv_content, item.getArticleEntity().getContent());

        helper.setText(R.id.itv_read_count,String.valueOf(item.getArticleEntity().getReadCount()));
        helper.setText(R.id.itv_comment_count,String.valueOf(item.getArticleEntity().getCommentCount()));
        helper.setText(R.id.itv_love_count,String.valueOf(item.getArticleEntity().getLoveCount()));

        // 设置是否收藏样式
        if (item.isLove()) {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_red),null,null,null);
        } else {
            ((ImageTextView)helper.getView(R.id.itv_love_count)).setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.mipmap.icon_love_gray),null,null,null);
        }

        // 动态填充不同类型的内容
        View layoutView;
        switch (item.getArticleEntity().getType()) {
            case FileUtils.AUDIO:
                layoutView = View.inflate(mContext,R.layout.frame_audeo,null);
                break;
            case FileUtils.VIDEO:
                layoutView = View.inflate(mContext,R.layout.frame_video,null);
                break;
            default:
                layoutView = View.inflate(mContext,R.layout.frame_image,null);
                break;
        }
        ((FrameLayout)helper.getView(R.id.fl_container)).addView(layoutView);


        // 处理填充不同类型资讯各自的处理方法
        switch (item.getArticleEntity().getType()) {
            case FileUtils.AUDIO:
                //
                break;
            case FileUtils.VIDEO:
                LoaderImageUtils.loaderImageView(mContext,item.getFilePathEntities().get(0).getFilePath(),R.mipmap.icon_rv_empty,helper.getView(R.id.iv_player));
                break;
            default:
                ((NineGridTestLayout)helper.getView(R.id.rv_image)).setUrlList(item.getFilePathEntities());
                break;
        }

        // 设置item child 监听.
        helper.addOnClickListener(R.id.itv_love_count);
        helper.addOnClickListener(R.id.itv_comment_count);
    }

    @Override
    public long getItemId(int position) {
        // 重写getItemId防止数据刷新时闪烁
        return position;
    }
}
