package com.chuxin.law.ui.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe
 */

public class UIUtils {

    /**
     * 给view 截图
     * @param view
     * @return
     */
    public static final Bitmap convertViewToBitmap(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap=null;
        try {
            bitmap=view.getDrawingCache();
            if (bitmap==null||bitmap.isRecycled()){
                return null;
            }
        }catch(Exception e){

        }
        return bitmap;

    }

    /**
     * 获取等级 好评 等 SpannableStringBuilder
     * @param txt1
     * @param txt2
     * @return
     */
    public static SpannableStringBuilder getText(String txt1, String txt2){
        SpannableStringBuilder builder=new SpannableStringBuilder(txt1+"\n"+txt2);

        builder.setSpan(new AbsoluteSizeSpan(15,true),0,txt1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(12,true),txt1.length(),txt1.length()+txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),0,txt1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")),txt1.length(),txt1.length()+txt2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

}
