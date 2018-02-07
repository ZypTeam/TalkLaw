package com.jusfoun.baselibrary.net;

import android.content.Context;
import com.jusfoun.baselibrary.Util.LogUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;
import okio.BufferedSource;


/**
 * Author  wangchenchen
 * CreateDate 2016/6/29.
 * Email wcc@jusfoun.com
 * Description 添加访问头参数
 */
public class HeaderInterceptor implements Interceptor {

    private Context mContext;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private String APIKEY = "apikey";
    private String Version = "Version";
    private String VersionCode = "VersionCode";
    private String AppType = "AppType";
    private String Channel = "Channel";
    private String Deviceid = "Deviceid";
    private String AccessToken = "AccessToken";
    private String APIVersion = "APIVersion";
    private String ContentType="";
    private String SEAVER_TOKEN="Seaver-Token";
    public HeaderInterceptor(Context mContext) {
        this.mContext=mContext.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                /**********添加头文件**********/
//                .addHeader(Version, AppUtil.getVersionName(mContext))
//                .addHeader(VersionCode, AppUtil.getVersionCode(mContext) + "")
//                .addHeader(AppType, "0")
////                .addHeader(Channel, AppUtil.getChannelName(mContext))
//                .addHeader(Deviceid, PhoneUtil.getIMEI(mContext))
                .addHeader(APIVersion, "1.0")
//                .addHeader(SEAVER_TOKEN, "d6115638dbf7d1b4a63513fc50d573d3")
                .build();

        String requestContent = bodyToString(request);
        LogUtil.e("request", "url==" + request.url().toString()+" "+request.headers() );
//        Log.e("request", "url==" + request.url().toString()+"?"+requestContent);
        long t1 = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        long t2 = System.nanoTime();
        LogUtil.e("response", "time==" + ((t2 - t1) / 1e6d));
        if (response!=null) {
            if (response.body() != null) {
                //打印返回信息
                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();
                LogUtil.e("response", "contentLength" + contentLength);
                Headers headers = response.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    LogUtil.e("response", headers.name(i) + ": " + headers.value(i));
                }
                if (HttpEngine.hasBody(response) && !bodyEncoded(headers)) {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    Charset charset = UTF8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        try {
                            charset = contentType.charset(UTF8);
                        } catch (UnsupportedCharsetException e) {
                            LogUtil.e("response", e.getMessage());
                            return response;
                        }
                    }
                    if (contentLength != 0) {
                        LogUtil.e("response", buffer.clone().readString(charset));
                    }
                    LogUtil.e("response", "<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            }
        }
        return response;
    }

    private static String bodyToString(final Request request) {
        try {
            if (request.body() == null) {
                return "(no body)";
            }
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            String requestContent = buffer.readUtf8();
            if (requestContent.length() == 0) {
                requestContent = "(empty body)";
            }
            return requestContent;
        } catch (final Throwable e) {
            return "(body not printable)";
        }
    }

    private static String bodyToString(String responseContent) {
        if (responseContent == null) {
            responseContent = "(no body)";
        } else if (responseContent.length() == 0) {
            responseContent = "(empty body)";
        }
        return responseContent;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

}
