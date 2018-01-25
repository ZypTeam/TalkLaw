package cn.com.talklaw.comment;

import com.jusfoun.baselibrary.base.BaseModel;
import com.jusfoun.baselibrary.base.NoDataModel;

import java.util.HashMap;
import java.util.Map;

import cn.com.talklaw.model.MoveModel;
import cn.com.talklaw.model.MyMsgListModel;
import cn.com.talklaw.model.MyMsgModel;
import cn.com.talklaw.model.StatementListModel;
import cn.com.talklaw.model.UserInfoModel;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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
     * 获取用户信息
     * @param params
     * @return
     */
    @GET("/user/info")
    Observable<UserInfoModel> getUserInfo(@QueryMap Map<String,String> params);

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
     * @param url
     * @param params
     * @return
     */
    @GET("/article/indexx")
    Observable<StatementListModel> getHomeKanfa();
}
