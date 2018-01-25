package cn.com.talklaw.ui.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideRoundTransform;

import cn.com.talklaw.R;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2509:55
 * @Email zyp@jusfoun.com
 * @Description ${加载图片工具}
 */
public class ImageLoderUtil {

    public static  void loadNormalImg(Context mContext, ImageView imageView, String url){
        Glide.with(mContext)
                .load(url)
                .into(imageView);
    }

    public static void loadRoundImage(Context mContext, ImageView imageView, String url,int radius){
        Glide.with(mContext)
                .load(url)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,radius))
                .crossFade()
                .into(imageView);
    }

    public static void loadRoundSmailImage(Context mContext, ImageView imageView, String url){
        Glide.with(mContext)
                .load(url)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,10))
                .crossFade()
                .into(imageView);
    }

}
