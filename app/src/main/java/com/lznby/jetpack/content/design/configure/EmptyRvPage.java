package com.lznby.jetpack.content.design.configure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.base.callback.SimpleCallback;

/**
 * Recycle empty layout.
 *
 * @author Lznby
 */
public class EmptyRvPage {


    /**
     * 无点击事件
     *
     * @param viewGroup        父容器
     * @return
     */
    public static View getEmptyView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_default_empty, viewGroup, false);
    }

    /**
     * 有点击事件
     *
     * @param viewGroup         父容器
     * @param callback          回调
     * @return
     */
    public static View getEmptyView(ViewGroup viewGroup, SimpleCallback callback) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_default_empty, viewGroup, false);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.doSomething();
            }
        });
        return view;
    }
}
