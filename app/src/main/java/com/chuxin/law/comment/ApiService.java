package com.chuxin.law.comment;

import com.chuxin.law.model.ArrondiModel;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.model.MoveModel;
import com.chuxin.law.model.MyAttentionListModel;
import com.chuxin.law.model.MyMsgListModel;
import com.chuxin.law.model.ProductListModel;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.model.StatementListModel;
import com.chuxin.law.model.UserInfoModel;
import com.jusfoun.baselibrary.base.NoDataModel;

import java.util.Map;

import cn.com.talklaw.model.GoodsDataModel;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe 网络路由
 */

public interface ApiService {
    @Headers("Cache-Control:public,max-age=60")
    @GET("http://api.douban.com/v2/movie/top250")
    Observable<MoveModel> getMove(@QueryMap Map<String,String> params);

    /**
     * 获取验证码
     * @param params
     * @return
     */
    @GET("/user/code")
    Observable<NoDataModel> getAuthCode(@QueryMap Map<String,String> params);

    /**
     * 登录
     * @param params
     * @return
     */
    @GET("/user/login")
    Observable<UserInfoModel> login(@QueryMap Map<String,String> params);

    /**
     * 三方登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login-oauth")
    Observable<UserInfoModel> thridLogin(@FieldMap Map<String,String> params);

    /**
     * 获取用户信息
     * @param params
     * @return
     */
    @GET("/user/info")
    Observable<UserInfoModel> getUserInfo();

    /**
     * 修改用户信息
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/user/info")
    Observable<NoDataModel> editUserInfo(@FieldMap Map<String,String> params);

    /**
     * 修改用户信息
     * @param params
     * @return
     */
    @POST("/user/info")
    Observable<NoDataModel> changeUserInfo(@FieldMap Map<String,String> params);

    /**
     * 关注
     * @param params
     * @return
     */
    @GET("/follow/add")
    Observable<NoDataModel> addFollow(@QueryMap Map<String,String> params);

    /**
     * 关注列表
     * @param params
     * @return
     */
    @GET("/follow/list")
    Observable<MyAttentionListModel> getFollowList(@QueryMap Map<String,String> params);

    /**
     * 取消关注
     * @param params
     * @return
     */
    @GET("/follow/del")
    Observable<NoDataModel> delFollow(@QueryMap Map<String,String> params);

    /**
     * 获取消息列表
     * @param params
     * @return
     */
    @GET("/sysmsg/list")
    Observable<MyMsgListModel> getSystemMsg(@QueryMap Map<String,String> params);

    /**
     * 消息已读
     * @param params
     * @return
     */
    @GET("/sysmsg/del")
    Observable<NoDataModel> allMsgRead(@QueryMap Map<String,String> params);

    /**
     * 认证律师
     * @param url
     * @param params
     * @return
     */
    @POST
    Observable<NoDataModel> certifiedLawyer(@Url String url,@QueryMap Map<String,String> params);

    /**
     * 说法首页
     * @param url
     * @param params
     * @return
     */
    @GET("/article/sindex")
    Observable<StatementListModel> getHomeShuoFa();
    /**
     * 看法首页
     * @return
     */
    @GET("/article/index")
    Observable<ProductListModel> getHomeKanfa();


    /**
     * 积分首页接口
     * @return
     */
    @GET("/goods/index")
    Observable<IntegralModel> getIntergralHome();

    /**
     * 积分首页接口
     * @return
     */
    @GET("/goods/list")
    Observable<GoodsDataModel> getAllGoods(@QueryMap Map<String,String> params);

    /**
     * 产品列表接口
     * @param params
     * @return
     */
    @GET("/article/list")
    Observable<ProductsModel> getProductList(@QueryMap Map<String,String> params);

    /**
     * 免费专区接口
     * @return
     */
    @GET("/article/free-index")
    Observable<ArrondiModel> getFreeList(@QueryMap Map<String,String> params);

}
