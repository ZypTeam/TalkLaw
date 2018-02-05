package com.chuxin.law.common;

import com.chuxin.law.model.ArrondiModel;
import com.chuxin.law.model.CommentListModel;
import com.chuxin.law.model.ExchangeRecordsDataModel;
import com.chuxin.law.model.IntegralDetailDataModel;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.model.LawyerIntroModel;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.model.IntegralProductDetailModel;
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
import retrofit2.http.Header;
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
    @FormUrlEncoded
    @POST("/user/info")
    Observable<NoDataModel> editUserInfo(@FieldMap Map<String,String> params,@Header("data") String lang);

    /**
     * 修改用户信息
     * @param params
     * @return
     */
    @FormUrlEncoded
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
    @GET("/follow/callback")
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
    @GET("/sysmsg/callback")
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
     * 积分产品详情
     * @param params
     * @return
     */
    @GET("/goods/view")
    Observable<IntegralProductDetailModel> getInteralProductDetail(@QueryMap Map<String,String> params);


    /**
     * 免费专区接口
     * @return
     */
    @GET("/article/free-index")
    Observable<ArrondiModel> getFreeList(@QueryMap Map<String,String> params);

    /**
     * 私人顾问接口
     * @return
     */
    @GET("/article/private")
    Observable<ArrondiModel> getPrivate(@QueryMap Map<String,String> params);

    /**
     * 公司顾问接口
     * @return
     */
    @GET("/article/company")
    Observable<ArrondiModel> getCompany(@QueryMap Map<String,String> params);

    /**
     * 产品详情接口
     * @param params
     * @return
     */
    @GET("/article/view")
    Observable<LawyerProductModel> getProductDetails(@QueryMap Map<String,String> params);

    @GET("/article-like/set")
    Observable<NoDataModel> setLike(@QueryMap Map<String,String> params);
    @GET("/article-like/callback")
    Observable<NoDataModel> delLike(@QueryMap Map<String,String> params);
    @GET("/article-collection/set")
    Observable<NoDataModel> setCollection(@QueryMap Map<String,String> params);
    @GET("/article-collection/callback")
    Observable<NoDataModel> delCollection(@QueryMap Map<String,String> params);

    @GET("/article-comment/list")
    Observable<CommentListModel> getCommentList(@QueryMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/article-comment/add")
    Observable<NoDataModel> sendComment(@FieldMap Map<String,String> params);

    @GET("/law-info/view")
    Observable<LawyerIntroModel> getLawIntro(@QueryMap Map<String,String> params);


    /**
     * 获取兑换记录
     * @param params
     * @return
     */
    @GET("/goods/buy-log")
    Observable<ExchangeRecordsDataModel> getExchangeRecords();



    /**
     * 获取积分详情
     * @param params
     * @return
     */
    @GET("/user/my-points")
    Observable<IntegralDetailDataModel> getIntegralDetail();

    /**
     * 获取积分详情
     * @param params
     * @return
     */
    @GET("/goods/buy")
    Observable<IntegralDetailDataModel> integralExchangeNet(@QueryMap Map<String,String> params);

    /**
     * 律师认证
     * @param params
     * @return
     */
    @FormUrlEncoded
    @GET("/law-info/submit")
    Observable<NoDataModel> lawyerAuth(@FieldMap Map<String,String> params);

}
