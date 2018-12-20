package com.lznby.jetpack.content.design.configure;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.utils.SpUtil;

/**
 * @author Lznby
 */
public class CacheConfigure {

    /**
     * check is login.
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        LoginEntity userEntity = getUserEntity(context);
        return userEntity != null;
    }

    /**
     * get user base information.
     *
     * @param context
     * @return
     */
    public static LoginEntity getUserEntity(Context context) {
        String userInfo = SpUtil.getValue(context,Configure.SpCache.SP_USER_INFO,"");
        if (StringUtils.isEmpty(userInfo.trim())) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo,LoginEntity.class);
        }
    }

    /**
     * get sp entity
     */
    public static Object getSpEntity(Context context,String spKey,Class clazz) {
        String data = SpUtil.getValue(context,spKey,"");
        if (StringUtils.isEmpty(data.trim())) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data,clazz);
        }
    }

    /**
     * get token.
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        LoginEntity loginEntity = getUserEntity(context);
        if (loginEntity!=null) {
            return loginEntity.getToken();
        } else {
            return null;
        }
    }

    /**
     * get userId.
     *
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        LoginEntity loginEntity = getUserEntity(context);
        if (loginEntity!=null) {
            return loginEntity.getUserBaseInfoEntity().getUserId();
        } else {
            return "";
        }
    }

    /**
     * clear all sp.
     *
     * @param context
     */
    public static void clearAll(Context context) {
        SpUtil.clearAllSp(context);
    }
}
