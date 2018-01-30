package com.chuxin.law.ui.activity;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.model.IntegralProductDetailModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.comment.CommentConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2515:31
 * @Email zyp@jusfoun.com
 * @Description ${积分产品详情}
 */
public class IntegralWebViewActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected WebView webView;
    protected TextView textPrice;
    protected TextView textDuihuan;

    private  String id="";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_webview_integral;
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.back_title_view);
        webView = (WebView) findViewById(R.id.webView);
        textPrice = (TextView) findViewById(R.id.text_price);
        textDuihuan = (TextView) findViewById(R.id.text_duihuan);
    }

    @Override
    public void initAction() {
        titleView.setTitle("商品详情");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        WebUtil.getUserAgentString(webView.getSettings(), mContext);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.setWebChromeClient(chromeClient);

        delMsg();
    }

    WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

    };

    private void delMsg() {
        showLoadDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).getInteralProductDetail(map)
                , new Action1<IntegralProductDetailModel>() {
                    @Override
                    public void call(IntegralProductDetailModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            if (model.data != null) {
                                webView.loadUrl(model.data.url);
                                textPrice.setText(model.data.point);
//                                textCountIntegral.setText();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
