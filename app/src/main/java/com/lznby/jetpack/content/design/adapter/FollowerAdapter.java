package com.lznby.jetpack.content.design.adapter;

import android.content.Context;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.base.BaseModel;
import com.lznby.jetpack.configure.IApplication;
import com.lznby.jetpack.content.design.configure.CacheConfigure;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.FollowerRouterEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;
import com.lznby.jetpack.content.design.vm.FollowerViewModel;
import com.lznby.jetpack.utils.ToastUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Lznby
 */
public class FollowerAdapter extends BaseQuickAdapter<UserFollowerInfoEntity,BaseViewHolder> {

    private Context context;

    private FollowerViewModel viewModel;

    private FollowerRouterEntity params;

    public FollowerAdapter(int layoutResId, BaseModel viewModel, FollowerRouterEntity params) {
        super(layoutResId);
        this.viewModel = (FollowerViewModel)viewModel;
        this.context = this.viewModel.activity.getActivity();
        this.params = params;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserFollowerInfoEntity item) {
        helper.setText(R.id.tv_nickname,item.getUserNickName());
        helper.setText(R.id.tv_motto,item.getUserMotto());
        Glide.with(context).load(item.getUserHeaderUrl()).into((CircleImageView) helper.getView(R.id.civ_header));
        // check is already follow.
        Button btFollow = helper.getView(R.id.bt_follow);
        if (item.getIsFollow() == 1) {
            btFollow.setBackgroundResource(R.drawable.follower_gray);
            btFollow.setText("已关注");
        } else {
            btFollow.setBackgroundResource(R.drawable.follower_blue);
            btFollow.setText("关注");
        }

        btFollow.setOnClickListener(v -> {
            if (item.getIsFollow() == 1) {
                unFollower(item,helper);
            } else {
                follow(item,helper);
            }
        });
    }

    /**
     * follow.
     *
     * @param item
     * @param helper
     */
    void follow(UserFollowerInfoEntity item,BaseViewHolder helper) {
        viewModel.addDisposable(
                IApplication.api.follow(CacheConfigure.getToken(context), FollowerRouterEntity.FOLLOWER.equals(params.getType())?item.getUserId():item.getFollowId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->updateUi(o,true),Throwable::printStackTrace)
        );
    }

    void unFollower(UserFollowerInfoEntity item,BaseViewHolder helper) {
        viewModel.addDisposable(
                IApplication.api.unFollower(CacheConfigure.getToken(context),CacheConfigure.getUserId(context),FollowerRouterEntity.FOLLOWER.equals(params.getType())?item.getUserId():item.getFollowId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->updateUi(o, false),Throwable::printStackTrace)
        );
    }


    void updateUi(BaseEntity entity, boolean isFollow) {
        if (isFollow) {
            if (entity.getCode() == Configure.ResponseCode.SUCCESS) {
                ToastUtils.shortToast(context,"关注成功");
                viewModel.getData(params);
            } else {
                ToastUtils.shortToast(context,entity.getMessage());
            }
        } else {
            if (entity.getCode() == Configure.ResponseCode.SUCCESS) {
                ToastUtils.shortToast(context,"取消成功");
                viewModel.getData(params);
            } else {
                ToastUtils.shortToast(context,entity.getMessage());
            }
        }
    }
}
