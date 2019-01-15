package com.lznby.jetpack.content.design.configure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.utils.ToastUtils;

/**
 * Recycle empty layout.
 *
 * @author Lznby
 */
public class EmptyRvPage {

    public static View getEmptyView(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_default_empty, viewGroup, false);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.shortToast(viewGroup.getContext(), "点击事件！");
            }
        });
        return view;
    }
}
