package com.lznby.jetpack.content.design.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.callback.SimpleCallback;
import com.lznby.jetpack.content.design.adapter.CommentAdapter;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.view.ImageTextView;
import com.lznby.jetpack.content.design.view.RecyclerItemDecoration;
import com.lznby.jetpack.content.design.view.nine.NineGridTestLayout;
import com.lznby.jetpack.content.design.vm.ArticleDetailsViewModel;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.LoaderImageUtils;
import com.lznby.jetpack.utils.ToastUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 资讯详情页面
 *
 * @author Lznby
 */
public class ArticleDetailsActivity extends BaseActivity<ArticleDetailsViewModel, ArticleAllInfoEntity> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_image)
    CircleImageView ivImage;
    @BindView(R.id.tv_nickname)
    TextView tvNickName;
    @BindView(R.id.tv_content)
    TextView tvContext;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.fl_container)
    FrameLayout frameLayout;
    @BindView(R.id.itv_read_count)
    ImageTextView itvReadCount;
    @BindView(R.id.itv_comment_count)
    ImageTextView itvCommentCount;
    @BindView(R.id.itv_love_count)
    ImageTextView itvLoveCount;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    /**
     * 评论RecyclerViewAdapter
     */
    CommentAdapter adapter;

    /**
     * 资讯Intent传参
     */
    ArticleDetailsRouterEntity params;

    /**
     * 视频播放器配置对象
     */
    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;

    @Override
    protected void onResume() {
        super.onResume();
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }
    }

    /**
     * 视屏播放器释放
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        View layoutView;
        params = getIntent().getParcelableExtra(ArticleDetailsRouterEntity.KEY);
        //
        viewModel.setParams(params);
        //
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_left_arrows);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 根据类型设置动态加载资讯布局
        if (FileUtils.VIDEO.equals(params.getType())) {
            layoutView = View.inflate(this, R.layout.frame_video_player, null);
        } else if (FileUtils.AUDIO.equals(params.getType())) {
            layoutView = View.inflate(this, R.layout.frame_audeo, null);
        } else {
            layoutView = View.inflate(this, R.layout.frame_image, null);
        }
        frameLayout.addView(layoutView);

        initRecyclerView();
    }

    @Override
    protected void bindView(ArticleAllInfoEntity entity) {
        // 设置公共部分资讯信息
        LoaderImageUtils.loaderCircleImageView(this, entity.getUserBaseInfoEntity().getUserHeaderUrl(), Configure.DefaultValue.DEFAULT_IAMGE_RES, ivImage);
        tvNickName.setText(entity.getUserBaseInfoEntity().getUserNickName());
        tvCreateTime.setText(entity.getArticleEntity().getTime());
        tvContext.setText(entity.getArticleEntity().getContent());

        itvReadCount.setText(String.valueOf(entity.getArticleEntity().getReadCount()));
        itvCommentCount.setText(String.valueOf(entity.getArticleEntity().getCommentCount()));
        itvLoveCount.setText(String.valueOf(entity.getArticleEntity().getLoveCount()));

        if (entity.isLove()) {
            itvLoveCount.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.icon_love_red), null, null, null);
        } else {
            itvLoveCount.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.icon_love_gray), null, null, null);
        }
        // 设置动态部分资讯内容
        if (FileUtils.VIDEO.equals(params.getType())) {
            // 视屏
            initVideoPlayer(entity);
        } else if (FileUtils.AUDIO.equals(params.getType())) {
            // 音频
            ((TextView) findViewById(R.id.tv_video)).setText("我是音频");
        } else {
            // 图片
            ((NineGridTestLayout) findViewById(R.id.rv_image)).setUrlList(entity.getFilePathEntities());
        }
        if (entity.getCommentEntities()!=null) {
            ToastUtils.shortToast(this,entity.getCommentEntities().get(0).getComment());
        }
        // 设置评论数据
        adapter.setNewData(entity.getCommentEntities());
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoPlayer(ArticleAllInfoEntity entity) {
        videoPlayer = findViewById(R.id.video_player);
        videoPlayer.setUp(entity.getFilePathEntities().get(0).getFilePath(), true, entity.getArticleEntity().getContent());
        //增加封面
        ImageView imageView = new ImageView(this);
        LoaderImageUtils.loaderImageView(this, entity.getFilePathEntities().get(0).getFilePath(), R.mipmap.background, imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    private void initRecyclerView() {
        adapter = new CommentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(layoutManager);
        rvComment.setAdapter(adapter);

        // 设置RecyclerView的分割线
        rvComment.addItemDecoration(new RecyclerItemDecoration());

        // 设置空布局 todo 有时间加一个SwipeRefreshLayout
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvComment, new SimpleCallback() {
            @Override
            public void doSomething() {
                // 加载资讯
                viewModel.getArticleByFileAttribution();
            }
        }));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_good_count:
                        ToastUtils.shortToast(ArticleDetailsActivity.this,"点赞请求！");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @OnClick({R.id.itv_love_count, R.id.itv_comment_count})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.itv_love_count:
                // 收藏
                if (viewModel.getLiveData().getValue().isLove()) {
                    viewModel.articleUnSub(viewModel.getLiveData().getValue().getArticleEntity().getFileAttribution());
                } else {
                    viewModel.articleSub(viewModel.getLiveData().getValue().getArticleEntity().getFileAttribution());
                }
                break;
            case R.id.itv_comment_count:
                ToastUtils.shortToast(this, "评论");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // 先返回正常状态
        if (orientationUtils != null) {
            if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                if (videoPlayer != null) {
                    videoPlayer.getFullscreenButton().performClick();
                }
                return;
            }
        }

        // 释放所有
        if (videoPlayer != null) {
            videoPlayer.setVideoAllCallBack(null);
        }
        super.onBackPressed();
    }

}
