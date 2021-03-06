package com.lznby.jetpack.content.design.adapter;

import android.widget.Button;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Lznby
 */
public class FollowerAdapter extends BaseQuickAdapter<UserFollowerInfoEntity,BaseViewHolder> {

    public FollowerAdapter() {
        super(R.layout.item_adapter_follower);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserFollowerInfoEntity item) {
        helper.setText(R.id.tv_nickname,item.getUserNickName());
        helper.setText(R.id.tv_motto,item.getUserMotto());
        Glide.with(mContext).load(item.getUserHeaderUrl()).into((CircleImageView) helper.getView(R.id.civ_header));
        // check is already follow.
        Button btFollow = helper.getView(R.id.bt_follow);
        if (item.getIsFollow() == 1) {
            btFollow.setBackgroundResource(R.drawable.follower_gray);
            btFollow.setText("已关注");
        } else {
            btFollow.setBackgroundResource(R.drawable.follower_blue);
            btFollow.setText("关注");
        }
        helper.addOnClickListener(R.id.bt_follow);
    }

}
