package com.chuxin.law.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.InputStream;

/**
 * @author wangcc
 * @date 2018/4/2
 * @describe
 */

public class HtmlTextView extends AppCompatTextView {
    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html, boolean useLocalDrawables) {
        Html.ImageGetter imgGetter;

        if (useLocalDrawables) {
            imgGetter = new LocalImageGetter(getContext());
        } else {
            imgGetter = new UrlImageGetter(this, getContext());
        }

        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY,imgGetter,new HtmlTagHandler());
        } else {
            result = Html.fromHtml(html,imgGetter,new HtmlTagHandler());
        }

        setText(result);

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
//        setText(Html.fromHtml(html, imgGetter, new HtmlTagHandler()));

        // make links work
        setMovementMethod(LinkMovementMethod.getInstance());
//        text.setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
    }
}
