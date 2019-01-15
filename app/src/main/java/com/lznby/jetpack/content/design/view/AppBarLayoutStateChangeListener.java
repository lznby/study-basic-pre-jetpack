package com.lznby.jetpack.content.design.view;

import android.support.design.widget.AppBarLayout;

/**
 * 判断CollapsingToolbarLayout折叠状态
 * https://blog.csdn.net/sinat_28765197/article/details/80900325
 *
 * @author Lznby
 */
public abstract class AppBarLayoutStateChangeListener implements AppBarLayout.OnOffsetChangedListener{

    public enum State{
        /**
         * 展开
         */
        EXPANDED,
        /**
         * 折叠
         */
        COLLAPSED,
        /**
         * 中间状态
         */
        INTERMEDIATE
    }

    /**
     * 当前折叠状态
     */
    private State mCurrentState = State.INTERMEDIATE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.INTERMEDIATE) {
                onStateChanged(appBarLayout, State.INTERMEDIATE);
            }
            mCurrentState = State.INTERMEDIATE;
        }
    }

    /**
     * 折叠状态变化
     *
     * @param appBarLayout      添加监听的appBarLayout对象
     * @param state             状态
     */
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}