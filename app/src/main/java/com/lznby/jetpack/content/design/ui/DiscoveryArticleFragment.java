package com.lznby.jetpack.content.design.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.jetpack.R;
import com.lznby.jetpack.base.BaseFragment;
import com.lznby.jetpack.content.design.adapter.DiscoveryArticleAdapter;
import com.lznby.jetpack.content.design.configure.EmptyRvPage;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.vm.DiscoveryArticleViewModel;
import com.lznby.jetpack.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 发现-资讯碎片
 *
 * @author Lznby
 */
public class DiscoveryArticleFragment extends BaseFragment<DiscoveryArticleViewModel,CenterActivity,List<ArticleAllInfoEntity>> {

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;

    private DiscoveryArticleAdapter adapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_discovery_article;
    }

    @Override
    protected void bindView(List<ArticleAllInfoEntity> entity) {
        adapter.setNewData(entity);
    }

    @Override
    protected void doOnCreateView() {
        adapter = new DiscoveryArticleAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewModel.getActivityContent());
        rvArticle.setLayoutManager(layoutManager);
        rvArticle.setAdapter(adapter);
        // 设置空布局
        adapter.setEmptyView(EmptyRvPage.getEmptyView(rvArticle));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.itv_comment_count:
                        // todo 评论
                        ToastUtils.shortToast(viewModel.getActivityContent(),"评论");
                        break;
                    case R.id.itv_love_count:
                        // 收藏
                        if (viewModel.getLiveData().getValue().get(position).isLove()) {
                            viewModel.articleUnSub(((ArticleAllInfoEntity)adapter.getData().get(position)).getArticleEntity().getFileAttribution(),position);
                        } else {
                            viewModel.articleSub(((ArticleAllInfoEntity)adapter.getData().get(position)).getArticleEntity().getFileAttribution(),position);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
