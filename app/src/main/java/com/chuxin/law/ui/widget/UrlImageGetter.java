package com.chuxin.law.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author wangcc
 * @date 2018/4/2
 * @describe
 */

public class UrlImageGetter implements Html.ImageGetter {

    Context c;
    TextView container;
    int width;
    /**
     *
     * @param t
     * @param c
     */
    public UrlImageGetter(TextView t, Context c) {
        this.c = c;
        this.container = t;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        ImageLoader.getInstance().loadImage(source,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        if(loadedImage!=null) {
                            float scaleWidth = ((float) width) / loadedImage.getWidth();

                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleWidth);
                            loadedImage = Bitmap.createBitmap(loadedImage, 0, 0,
                                    loadedImage.getWidth(), loadedImage.getHeight(),
                                    matrix, true);
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
                                    loadedImage.getHeight());
                            container.invalidate();
                            container.setText(container.getText()); // ??????
                        }
                    }
                });

        return urlDrawable;
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
