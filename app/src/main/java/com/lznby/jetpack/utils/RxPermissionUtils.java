package com.lznby.jetpack.utils;

import android.Manifest;

import com.lznby.jetpack.base.BaseActivity;
import com.lznby.jetpack.base.BaseFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

/**
 * RxPermission网络请求工具类
 *
 * @author Lznby
 */
public class RxPermissionUtils {
    /**
     * RxPermissionsRequest
     */
    public static void requestPermissions(BaseActivity activity) {
        RxPermissions rxPermission = new RxPermissions(activity);
        activity.list.add(
                rxPermission
                        .requestEach(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CALENDAR,
                                Manifest.permission.READ_CALL_LOG,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CAMERA,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.SEND_SMS
                        )
                        .subscribe(permission -> {
                            if (permission.granted) {
                                // 用户已经同意该权限
                                Timber.d("%s is granted.", permission.name);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                Timber.d("%s is denied. More info should be provided.", permission.name);
                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                Timber.d("%s is denied.", permission.name);
                            }
                        })
        );
    }

    /**
     * RxPermissionsRequest
     */
    public static void requestPermissions(BaseFragment fragment) {
        RxPermissions rxPermission = new RxPermissions(fragment);
        fragment.list.add(
                rxPermission
                        .requestEach(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CALENDAR,
                                Manifest.permission.READ_CALL_LOG,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CAMERA,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.SEND_SMS
                        )
                        .subscribe(permission -> {
                            if (permission.granted) {
                                // 用户已经同意该权限
                                Timber.d("%s is granted.", permission.name);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                Timber.d("%s is denied. More info should be provided.", permission.name);
                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                Timber.d("%s is denied.", permission.name);
                            }
                        })
        );
    }
}
