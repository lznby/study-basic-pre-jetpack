package com.lznby.jetpack.content.design.configure;

import com.lznby.jetpack.R;

/**
 * @author Lznby
 */
public interface Configure {

    /**
     * Fragment
     */
    interface FragmentType {
        /**
         * MainHomeFragment Type
         */
        int HOME = 1;

        String HOME_TAG = "HOME";

        /**
         * MainSubscribeFragment Type
         */
        int SUBSCRIBE = 2;

        String SUBSCRIBE_TAG = "SUBSCRIBE";

        /**
         * MainDiscoveryFragment Type
         */
        int DISCOVERY = 3;

        String DISCOVERY_TAG = "DISCOVERY";

        /**
         * MainMineFragment Type
         */
        int MINE = 4;

        String MINE_TAG = "MINE";
    }

    /**
     * Response Code
     */
    interface ResponseCode {
        /**
         * request success
         */
        int SUCCESS = 1;

        /**
         * request fail
         */
        int FAIL = -1;

        /**
         * cookies out of time
         */
        int UN_LOGIN = 0;

        /**
         * others error
         */
        int ERROR = 2;

    }

    /**
     * Sp Cache
     */
    interface SpCache {
        /**
         * user base information have cookies.
         */
        String SP_USER_INFO = "SP_USER_INFO";
        /**
         * user base information do not have cookies.
         */
        String SP_BASE_USER_INFO = "SP_BASE_USER_INFO";
        /**
         * user follower size.
         */
        String SP_FOLLOWER_SIZE = "SP_FOLLOWER_SIZE";
        /**
         * user follow content.
         */
        String SP_FOLLOW_CONTENT = "SP_FOLLOW_CONTENT";
        /**
         * user follower content.
         */
        String SP_FOLLOWER_CONTENT = "SP_FOLLOWER_CONTENT";
        /**
         * oss sts token.
         */
        String SP_OSS_TOKEN = "SP_OSS_TOKEN";
    }

    /**
     * onActivityResult Code
     */
    interface ResultCode {
        /**
         * choose single photo from photo album.
         */
        int SINGLE_IMAGE_REQUEST_CODE = 101;

        /**
         * choose many photo from photo album.
         */
        int MANY_IMAGE_REQUEST_CODE = 102;

        /**
         * choose photo by take photos.
         */
        int CAMERA_IMAGE_REQUEST_CODE = 103;

        /**
         * choose video from local.
         */
        int CHOOSE_VIDEO = 201;

        /**
         * choose video by camera shooting.
         */
        int CAMERA_VIDEO = 202;
    }

    /**
     * defaultValue interface.
     */
    interface DefaultValue {
        /**
         * default Glide loader url.
         */
        String DEFAULT_IMAGE_URL = "https://lznby-image.oss-cn-shenzhen.aliyuncs.com/25e68919031d1ec70342b795ce377d42.png";
        /**
         * default Glide loader res.
         */
        int DEFAULT_IAMGE_RES = R.mipmap.icon_default_header;

    }

    /**
     * Router bundle default.
     */
    interface RouterType {
        String MINE = "我的";
        String HIS = "他的";
        String HER = "她的";
    }
}
