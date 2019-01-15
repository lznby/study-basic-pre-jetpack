package com.lznby.jetpack.base.callback;

/**
 * 获取StsToken地址后的回调
 *
 * @author Lznby
 */
public interface UrlCallback {
    /**
     * do anything in this callback.
     * @param url
     */
    void doSomething(String url);
}
