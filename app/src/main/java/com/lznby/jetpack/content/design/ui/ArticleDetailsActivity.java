package com.lznby.jetpack.content.design.ui;

import android.content.pm.ActivityInfo;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.ArticleDetailsRouterEntity;
import com.lznby.jetpack.content.design.view.nine.NineGridTestLayout;
import com.lznby.jetpack.content.design.vm.ArticleDetailsViewModel;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.LoaderImageUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 资讯详情页面
 *
 * @author Lznby
 */
public class ArticleDetailsActivity extends BaseActivity<ArticleDetailsViewModel,ArticleAllInfoEntity> {

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
    protected int setLayout() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void doOnCreate() {
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
            layoutView = View.inflate(this,R.layout.frame_video_player,null);
        } else if (FileUtils.AUDIO.equals(params.getType())) {
            layoutView = View.inflate(this,R.layout.frame_audeo,null);
        } else {
            layoutView = View.inflate(this,R.layout.frame_image,null);
        }
        frameLayout.addView(layoutView);
    }

    @Override
    protected void bindView(ArticleAllInfoEntity entity) {
        // 设置公共部分资讯信息
        LoaderImageUtils.loaderCircleImageView(this, entity.getUserBaseInfoEntity().getUserHeaderUrl(), Configure.DefaultValue.DEFAULT_IAMGE_RES, ivImage);
        tvNickName.setText(entity.getUserBaseInfoEntity().getUserNickName());
        tvCreateTime.setText(entity.getArticleEntity().getTime());
        tvContext.setText(entity.getArticleEntity().getContent());

        // 设置动态部分资讯内容
        if (FileUtils.VIDEO.equals(params.getType())) {
            // 视屏
            initVideoPlayer(entity);
        } else if (FileUtils.AUDIO.equals(params.getType())) {
            // 音频
            ((TextView)findViewById(R.id.tv_video)).setText("我是音频");
        } else {
            // 图片
            ((NineGridTestLayout)findViewById(R.id.rv_image)).setUrlList(entity.getFilePathEntities());
        }
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoPlayer(ArticleAllInfoEntity entity){
        videoPlayer =  findViewById(R.id.video_player);
        videoPlayer.setUp(entity.getFilePathEntities().get(0).getFilePath(), true, entity.getArticleEntity().getContent());
        //增加封面
        ImageView imageView = new ImageView(this);
        LoaderImageUtils.loaderImageView(this,entity.getFilePathEntities().get(0).getFilePath(),R.mipmap.background,imageView);
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

    /**
     * 视屏播放器释放
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (videoPlayer !=null) {
            videoPlayer.onVideoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoPlayer !=null) {
            videoPlayer.onVideoResume();
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
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils!=null) {
            if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                if (videoPlayer!=null) {
                    videoPlayer.getFullscreenButton().performClick();
                }
                return;
            }
        }

        //释放所有
        if (videoPlayer!=null) {
            videoPlayer.setVideoAllCallBack(null);
        }
        super.onBackPressed();
    }


}
