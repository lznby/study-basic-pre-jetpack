package com.lznby.jetpack.content.design.configure;

import android.content.Context;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.utils.ToastUtils;

/**
 * @author Lznby
 */
public class ResponseUiConfigure {

    public static int responseUtils(BaseEntity entity, Context context) {
        if (entity.getCode()==Configure.ResponseCode.SUCCESS) {
            ToastUtils.shortToast(context,entity.getMessage());
        } else if (entity.getCode()==Configure.ResponseCode.FAIL) {
            ToastUtils.shortToast(context,entity.getMessage());
        } else if (entity.getCode()==Configure.ResponseCode.ERROR) {
            ToastUtils.shortToast(context,entity.getMessage());
        } else if (entity.getCode()==Configure.ResponseCode.UN_LOGIN) {
            ToastUtils.shortToast(context,entity.getMessage());
        } else {
            ToastUtils.shortToast(context,"未知错误！");
        }
        return entity.getCode();
    }
}
