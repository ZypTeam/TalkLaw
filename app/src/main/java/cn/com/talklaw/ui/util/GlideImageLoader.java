package cn.com.talklaw.ui.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2217:20
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {
        Glide.with(context).load("http://img5.imgtn.bdimg.com/it/u=2137958015,4291978384&fm=27&gp=0.jpg").into(imageView);
    }
}
