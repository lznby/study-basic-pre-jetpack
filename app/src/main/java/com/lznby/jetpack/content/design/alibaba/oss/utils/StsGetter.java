package com.lznby.jetpack.content.design.alibaba.oss.utils;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;

/**
 * OSS Sts 服务获取
 * @author Lznby
 */
public class StsGetter extends OSSFederationCredentialProvider {

    OSSFederationToken oSSFederationToken;

    public StsGetter(OSSFederationToken oSSFederationToken) {
        this.oSSFederationToken = oSSFederationToken;
    }
    @Override
    public OSSFederationToken getFederationToken() {
        return oSSFederationToken;
    }

}