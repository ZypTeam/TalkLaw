package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.IntegralDetailDataModel;
import com.chuxin.law.model.IntegralProductDetailModel;
import com.chuxin.law.model.ShippingAddressSp;
import com.chuxin.law.sharedpreferences.ShippingAddressModel;
import com.chuxin.law.ui.view.IntegralDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2515:31
 * @Email zyp@jusfoun.com
 * @Description ${webview详情页面}
 */
public class WebViewActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected WebView webView;
    private String url;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initDatas() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.back_title_view);
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    public void initAction() {
        titleView.setTitle("详情");
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

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoadDialog();
            }
        });
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(url);
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
}
