package com.chuxin.law.ui.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chuxin.law.R;
import com.chuxin.law.model.CarouseModel;
import com.youth.banner.loader.ImageLoader;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2217:20
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {
        CarouseModel model;
        if (o instanceof CarouseModel) {
            model = (CarouseModel) o;
            Glide.with(context).load(model.img).placeholder(R.drawable.img_banner_default)
                    .error(R.drawable.img_banner_default).into(imageView);
        }

    }
}
