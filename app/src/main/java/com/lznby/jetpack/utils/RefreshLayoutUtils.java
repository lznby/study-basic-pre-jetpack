package com.lznby.jetpack.utils;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * RefreshLayout utils.
 *
 * @author Lznby
 */
public class RefreshLayoutUtils {

    /**
     * SwipeRefreshLayout 初始化工具类
     *
     * @param refreshLayout 下拉刷新控件
     * @param listener  下拉事件监听
     */
    public static void initRefreshLayout (SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        // 设置进度条的颜色,不定长参数可以设置多种颜色
        // 对于RefreshLayout,网上说最多设置4中颜色,不要使用android.R.color, 否则会卡死
        refreshLayout.setColorSchemeColors(
                Color.RED,
                Color.YELLOW,
                Color.GREEN
        );

        // 设置进度条背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

        // 设置手指划过多少像素开始触犯刷新
        refreshLayout.setDistanceToTriggerSync(100);

        // 刷新监听
        refreshLayout.setOnRefreshListener(listener);
    }
}
