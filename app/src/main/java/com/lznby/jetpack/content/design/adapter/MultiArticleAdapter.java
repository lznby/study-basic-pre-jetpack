package com.lznby.jetpack.content.design.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.configure.Configure;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.view.nine.NineGridTestLayout;
import com.lznby.jetpack.utils.FileUtils;
import com.lznby.jetpack.utils.LoaderImageUtils;

/**
 * 多类型资讯Adapter
 *
 * @author Lznby
 */
public class MultiArticleAdapter extends BaseQuickAdapter<ArticleAllInfoEntity,BaseViewHolder> {

    public MultiArticleAdapter() {
        super(null);
        // Step.1 判断布局类型
        setMultiTypeDelegate(new MultiTypeDelegate<ArticleAllInfoEntity>() {
            @Override
            protected int getItemType(ArticleAllInfoEntity articleAllInfoEntity) {
                String type = articleAllInfoEntity.getArticleEntity().getType().isEmpty()?"":articleAllInfoEntity.getArticleEntity().getType();
                // 根据实体类判断布局类型
                switch (type) {
                    case FileUtils.IMAGE:
                        return FileUtils.IMAGE_TYPE;
                    case FileUtils.VIDEO:
                        return FileUtils.VIDEO_TYPE;
                    case FileUtils.AUDIO:
                        return FileUtils.AUDIO_TYPE;
                    default:
                        return FileUtils.IMAGE_TYPE;
                }
            }
        });

        //Step.2 配置多类型布局匹配的布局文件
        getMultiTypeDelegate()
                .registerItemType(FileUtils.IMAGE_TYPE,R.layout.item_article);
//                .registerItemType(FileUtils.VIDEO_TYPE,R.layout.item_article_video)
//                .registerItemType(FileUtils.AUDIO_TYPE,R.layout.item_article_audio);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleAllInfoEntity item) {

        // 公共部分
        LoaderImageUtils.loaderCircleImageView(mContext, item.getUserBaseInfoEntity().getUserHeaderUrl(), Configure.DefaultValue.DEFAULT_IAMGE_RES, helper.getView(R.id.iv_image));
        helper.setText(R.id.tv_nickname, item.getUserBaseInfoEntity().getUserNickName());
        helper.setText(R.id.tv_create_time, item.getArticleEntity().getTime());
        helper.setText(R.id.tv_content, item.getArticleEntity().getContent());

        //Step.3 处理私有部分
        switch (helper.getItemViewType()) {
            case FileUtils.AUDIO_TYPE:
                // doSomething

                break;
            case FileUtils.VIDEO_TYPE:
                // 配置视屏播放控件
                LoaderImageUtils.loaderImageView(mContext,item.getFilePathEntities().get(0).getFilePath(),R.mipmap.icon_rv_empty,helper.getView(R.id.iv_player));
                break;
            default:
                // 默认为图片
                ((NineGridTestLayout)helper.getView(R.id.rv_image)).setUrlList(item.getFilePathEntities());
                break;
        }
    }


}
