package com.chuxin.law.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.activity.BuyProductActivity;
import com.chuxin.law.ui.activity.CommentListActivity;
import com.chuxin.law.ui.activity.GratuityActivity;
import com.chuxin.law.ui.activity.LawyerAuthActivity;
import com.chuxin.law.ui.activity.LawyerDefautActivity;

import static com.chuxin.law.common.CommonConstant.COMMENT_RESULT_CODE;

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

    public static void goLawyerDef(Context context,String id){
        Intent intent=new Intent(context, LawyerDefautActivity.class);
        intent.putExtra(LawyerDefautActivity.ID,id);
        context.startActivity(intent);

    }

    public static void goGratuity(Context context,LawyerProductModel.LawyerProductData data){
        Intent intent=new Intent(context, GratuityActivity.class);
        intent.putExtra(GratuityActivity.DATA,data);
        context.startActivity(intent);

    }

    public static void goCommentList(Object context,String id,String title){
        Intent intent=new Intent();
        intent.putExtra(CommentListActivity.ID,id);
        intent.putExtra(CommentListActivity.COMMENT_TITLE,title);
        if (context instanceof Activity){
            intent.setClass((Activity) context,CommentListActivity.class);
            ((Activity) context).startActivityForResult(intent,COMMENT_RESULT_CODE);
        }else if (context instanceof Fragment){
            intent.setClass( ((Fragment) context).getContext(),CommentListActivity.class);
            ((Fragment) context).startActivityForResult(intent,COMMENT_RESULT_CODE);
        }
    }

    public static void goLawyerAuth(Context context){
        Intent intent=new Intent(context, LawyerAuthActivity.class);
        context.startActivity(intent);
    }

    public static void goBuyActivity(Context context, LawyerProductModel.LawyerProductData data){
        Intent intent=new Intent(context, BuyProductActivity.class);
        intent.putExtra(BuyProductActivity.DATA,data);
        context.startActivity(intent);
    }

    public static Spanned getHtmlTxt(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


}
