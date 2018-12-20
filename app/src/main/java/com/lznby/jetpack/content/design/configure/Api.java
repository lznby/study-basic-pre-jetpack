package com.lznby.jetpack.content.design.configure;

import com.lznby.jetpack.base.BaseEntity;
import com.lznby.jetpack.content.design.entity.LoginEntity;
import com.lznby.jetpack.content.design.entity.UserBaseInfoEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerInfoEntity;
import com.lznby.jetpack.content.design.entity.UserFollowerSizeEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
//    String HOST = "http://172.16.66.111:8080";

    /**
     * 阿里云公网IP
     */
    String HOST = "http://39.108.138.218/android/";

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



}
