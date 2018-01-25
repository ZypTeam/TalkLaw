package cn.com.talklaw.ui.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.com.talklaw.model.CarouseModel;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2217:20
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {
        CarouseModel model = (CarouseModel)o;
        Log.e("tag","displayImage="+model.img);
        Glide.with(context).load(model.img).into(imageView);
    }
}
