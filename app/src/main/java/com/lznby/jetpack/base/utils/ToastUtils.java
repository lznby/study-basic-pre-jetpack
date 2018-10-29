package com.lznby.jetpack.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * @author Lznby
 */
public class ToastUtils {
    /**
     * ToastUtils
     * @param context
     * @param content
     */
    public static void shortToast(@NonNull Context context, @NonNull String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * SnackBaUtils
     * @param view
     * @param content
     */
    public static void shortSanckbar(View view, String content) {
        Snackbar.make(view,content,Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
    }
}
