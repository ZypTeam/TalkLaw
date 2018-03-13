package com.chuxin.law.common;

import android.text.TextUtils;
import android.util.Log;

import com.chuxin.law.TalkLawApplication;
import com.google.zxing.utils.StringUtils;
import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.Util.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangcc
 * @date 2018/2/7
 * @describe
 */

public class HeaderTalkInterceptor implements Interceptor {
    private String SEAVER_TOKEN="Seaver-Token";
    private String token;

    public HeaderTalkInterceptor(){

        token=UserInfoDelegate.getInstance().getToken();

        Log.e("tag","tokentokentoken=="+token);
//        if (StringUtil.isEmpty(token)){
//            //TODO 2018年02月07日 如果用户信息中没有token，使用个临时的测试 by wang
//            token="d6115638dbf7d1b4a63513fc50d573d3";
//        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request;
        if(TextUtils.isEmpty(token)){
            request=chain.request().newBuilder()
                    .build();
        }else{
            request=chain.request().newBuilder()
                    .addHeader(SEAVER_TOKEN, token)
                    .build();
        }

        return chain.proceed(request);
    }
}
