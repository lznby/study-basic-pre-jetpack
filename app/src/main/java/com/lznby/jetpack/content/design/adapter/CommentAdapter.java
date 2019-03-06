package com.lznby.jetpack.content.design.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.CommentEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 资讯详情中评论RecyclerView的Adapter
 *
 * @author Lznby
 */
public class CommentAdapter extends BaseQuickAdapter<CommentEntity,BaseViewHolder> {

    public CommentAdapter() {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        helper.setText(R.id.tv_nickname, item.getUserNickname());
        helper.setText(R.id.tv_comment, item.getComment());
        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.itv_good_count, item.getLoveCount()+"");
        Glide.with(mContext).load(item.getUserHeaderUrl()).into((CircleImageView) helper.getView(R.id.civ_header));
        // 为子控件绑定点击事件
        helper.addOnClickListener(R.id.itv_good_count);
    }
}
