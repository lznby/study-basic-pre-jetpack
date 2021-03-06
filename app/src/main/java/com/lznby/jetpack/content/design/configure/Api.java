package com.lznby.jetpack.content.design.configure;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.content.design.alibaba.oss.model.StsModel;
import com.lznby.jetpack.content.design.entity.ArticleAllInfoEntity;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.entity.PersonalHomePageEntity;
import com.lznby.jetpack.content.design.entity.ThemeEntity;
import com.lznby.jetpack.content.design.entity.ThemeHomePageEntity;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerSizeEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Lznby
 */
public interface Api {

    /**
     * 寝室局域网IP
     */
//    String HOST = "http://192.168.199.122:8080";
    /**
     * 公司局域网IP
     */
    String HOST = "http://172.16.66.186:8080";

    /**
     * 阿里云公网IP
     */
//    String HOST = "http://39.108.138.218/android/";

    /**
     * register interface
     *
     * @param userNickName
     * @param userPassword
     * @return
     */
    @FormUrlEncoded
    @POST("userAccount/register")
    Observable<BaseEntity<Object>> register(
            @Field("userNickName") String userNickName,
            @Field("userPassword") String userPassword
    );

    /**
     * login interface
     *
     * @param userNickName
     * @param userPassword
     * @return
     */
    @FormUrlEncoded
    @POST("userAccount/login")
    Observable<BaseEntity<LoginEntity>> login(
            @Field("userNickName") String userNickName,
            @Field("userPassword") String userPassword
    );

    /**
     * user login out.
     *
     * @param userCookies
     * @return
     */
    @GET("userAccount/loginOut")
    Observable<BaseEntity<Object>> loginOut(
            @Header("userCookies") String userCookies
    );


    /**
     * get userInfo by cookies interface
     *
     * @param userCookies
     * @return
     */
    @GET("userBaseInfo/getMineBaseInfo")
    Observable<BaseEntity<UserBaseInfoEntity>> getUserBaseInfo(
            @Header("userCookies") String userCookies
    );

    /**
     * update base user information.
     *
     * @param userCookies
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("userBaseInfo/updateUserBaseInfo")
    Observable<BaseEntity<UserBaseInfoEntity>> updateUserBaseInfo(
            @Header("userCookies") String userCookies,
            @FieldMap Map<String,String> map
    );

    /**
     * update user password.
     *
     * @param userCookies
     * @param userPassword
     * @param newUserPassword
     * @param rUserPassword
     * @return
     */
    @FormUrlEncoded
    @POST("userAccount/updateUserPassword")
    Observable<BaseEntity<Object>> updatePassword(
            @Header("userCookies") String userCookies,
            @Field("userPassword") String userPassword,
            @Field("newUserPassword") String newUserPassword,
            @Field("rUserPassword") String rUserPassword
    );

    /**
     * get mine fragment follower size.
     *
     * @param userCookies
     * @param userId
     * @return
     */
    @GET("userFollower/getUserFollowerSize")
    Observable<BaseEntity<UserFollowerSizeEntity>> getUserFollowerSize(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId
    );

    /**
     * get user follower by userId.
     *
     * @param userCookies
     * @param userId
     * @return
     */
    @GET("userFollower/getFollowers")
    Observable<BaseEntity<List<UserFollowerInfoEntity>>> getFollowers(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId,
            @Query("queryId") String queryId
    );

    /**
     * get user follow by userId.
     *
     * @param userCookies
     * @param userId
     * @return
     */
    @GET("userFollower/getFollows")
    Observable<BaseEntity<List<UserFollowerInfoEntity>>> getFollows(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId,
            @Query("queryId") String queryId
    );

    /**
     * user follow.
     *
     * @param userCookies
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("userFollower/follow")
    Observable<BaseEntity<Object>> follow(
            @Header("userCookies") String userCookies,
            @Field("userId") int userId
    );

    /**
     * user un-follower.
     *
     * @param userCookies
     * @param userId
     * @param followId
     * @return
     */
    @FormUrlEncoded
    @POST("userFollower/unFollower")
    Observable<BaseEntity<Object>> unFollower(
            @Header("userCookies") String userCookies,
            @Field("userId") String userId,
            @Field("followId") int followId
    );

    /**
     * OSS Sts Service.
     *
     * @return
     */
    @GET("oss/sts")
    Observable<BaseEntity<StsModel>> getOssSts();


    /**
     * 多文件上传(老接口)
     * https://www.daidingkang.cc/2016/06/17/Retrofit2-network-framework-parsing/
     *
     * @param parts 含图片
     * @return
     */
    @Multipart
    @POST("fileUpload/uploadImage")
    Observable<BaseEntity> uploadImage(@Part MultipartBody.Part[] parts);

    /**
     * 根据用户Id获取用户的主页
     *
     * @param userCookies   用户Cookies可为空
     * @param userId        用户Id必选
     * @return
     */
    @GET("userBaseInfo/getPersonalHomePage")
    Observable<BaseEntity<PersonalHomePageEntity>> getPersonalHomePage(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId
    );

    /**
     * 上传资讯,文件存储到OSS服务器
     *
     * @param userCookies     用户Cookies值
     * @param files           OSS文件名(List<String>的Json)
     * @param title           资讯标题(可以为空)
     * @param content         资讯内容
     * @param themeId         主题Id(List<String>的Json)
     * @param type            资讯文件类型
     * @return
     */
    @FormUrlEncoded
    @POST("article/createArticleByOss")
    Observable<BaseEntity> createArticle(
            @Header("userCookies") String userCookies,
            @Field("files") String files,
            @Field("title") String title,
            @Field("content") String content,
            @Field("themeId") String themeId,
            @Field("type") String type
    );

    /**
     * 根据资讯编号获取资讯详情
     *
     * @param userCookies          浏览者Cookies
     * @param fileAttribution      资讯Id
     * @return
     */
    @GET("article/getArticleByFileAttribution")
    Observable<BaseEntity<List<ArticleAllInfoEntity>>> getArticleByFileAttribution(
            @Header("userCookies") String userCookies,
            @Query("fileAttribution") String fileAttribution
    );

    /**
     * 查询某人所有订阅主题的详细信息(返回theme详情信息)
     *
     * @param userCookies       发起查询者的Cookies
     * @param userId            被查询者的userId
     * @return
     */
    @GET("theme/findAllFlowThemeInfo")
    Observable<BaseEntity<List<ThemeEntity>>> findAllFlowThemeInfo(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId
    );

    /**
     * 新增主题关注
     *
     * @param userCookies       用户Cookies
     * @param themeId           关注的主题Id
     * @return
     */
    @FormUrlEncoded
    @POST("theme/followTheme")
    Observable<BaseEntity> followTheme(
            @Header("userCookies") String userCookies,
            @Field("themeId") String themeId
    );

    /**
     * 取消主题关注
     *
     * @param userCookies       用户Cookies
     * @param themeId           取关的主题Id
     * @return
     */
    @FormUrlEncoded
    @POST("theme/unfollowTheme")
    Observable<BaseEntity> unfollowTheme(
            @Header("userCookies") String userCookies,
            @Field("themeId") String themeId
    );

    /**
     * 获取所有主题
     *
     * @param userCookies       用户资讯
     * @return
     */
    @GET("theme/findAllTheme")
    Observable<BaseEntity<List<ThemeEntity>>> findAllTheme(
            @Header("userCookies") String userCookies
    );

    /**
     * 新增主题信息
     *
     * @param userCookies       用户Cookies
     * @param themeName         主题名称
     * @param themeNote         主题简介
     * @param themeImage        主题背景图OSS地址
     * @param themeHeaderImage  主题头像图OSS地址
     * @return
     */
    @FormUrlEncoded
    @POST("theme/createTheme")
    Observable<BaseEntity> createTheme(
            @Header("userCookies") String userCookies,
            @Field("themeName") String themeName,
            @Field("themeNote") String themeNote,
            @Field("themeImage") String themeImage,
            @Field("themeHeaderImage") String themeHeaderImage
    );

    /**
     * 获取所有资讯(发现-资讯)
     *
     * @param userCookies       用户Cookies
     * @return
     */
    @GET("article/getAllArticle")
    Observable<BaseEntity<List<ArticleAllInfoEntity>>> getAllArticle(
            @Header("userCookies") String userCookies
    );

    /**
     * 收藏资讯
     *
     * @param userCookies       用户Cookies
     * @param fileAttribution   资讯编号
     * @return
     */
    @FormUrlEncoded
    @POST("article/articleSub")
    Observable<BaseEntity> articleSub(
            @Header("userCookies") String userCookies,
            @Field("fileAttribution") String fileAttribution
    );


    /**
     * 取消收藏资讯
     *
     * @param userCookies       用户Cookies
     * @param fileAttribution   资讯编号
     * @return
     */
    @FormUrlEncoded
    @POST("article/articleUnSub")
    Observable<BaseEntity> articleUnSub(
            @Header("userCookies") String userCookies,
            @Field("fileAttribution") String fileAttribution
    );

    /**
     * 获取所有用户信息(发现-用户)
     *
     * @param userCookies       用户Cookies
     * @return                  所有用户信息
     */
    @GET("userBaseInfo/getAllUserBaseInfo")
    Observable<BaseEntity<List<UserBaseInfoEntity>>> getAllUserBaseInfo(
            @Header("userCookies") String userCookies
    );

    /**
     * 查询某人所有收藏资讯
     *
     * @param userCookies       用户Cookies
     * @param userId            被查询者Id
     * @return                  被查询者所有收藏的资讯信息
     */
    @GET("article/getAllSubArticle")
    Observable<BaseEntity<List<ArticleAllInfoEntity>>> getAllSubArticle(
            @Header("userCookies") String userCookies,
            @Query("userId") String userId
    );

    /**
     * 主题首页信息
     *
     * @param userCookies       用户Cookies
     * @param themeId           查询的主题Id
     * @return                  主题页信息
     */
    @GET("theme/findArticleByThemeId")
    Observable<BaseEntity<ThemeHomePageEntity>> findArticleByThemeId(
            @Header("userCookies") String userCookies,
            @Query("themeId") String themeId
    );

    /**
     * 用户订阅主题及关注用户下所有资讯(订阅模块接口)
     *
     * @param userCookies       用户Cookies
     * @return                  订阅资讯
     */
    @GET("/article/getUserSubInfo")
    Observable<BaseEntity<List<ArticleAllInfoEntity>>> getUserSubInfo(
            @Header("userCookies") String userCookies
    );

    /**
     * 轮播资讯
     *
     * @param userCookies       用户Cookies(可以省略)
     * @param size              返回资讯数量
     * @return                  轮播资讯
     */
    @GET("article/getBannerArticle")
    Observable<BaseEntity<List<ArticleAllInfoEntity>>> getBannerArticle(
            @Header("userCookies") String userCookies,
            @Query("size") int size
    );

}
