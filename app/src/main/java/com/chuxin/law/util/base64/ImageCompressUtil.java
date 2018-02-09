package com.chuxin.law.util.base64;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jusfoun.baselibrary.Util.ImagePathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhaoyapeng
 * @version create time:15/9/9下午10:27
 * @Email zyp@jusfoun.com
 * @Description ${图片压缩}
 */
public class ImageCompressUtil {
    public static final int DEFAULT_MAX_WIDTH = 320;
    public static final int DEFAULT_MAX_HEIGHT = 480;
    public static Bitmap getBitmapFromMedia(Context context, String pathName, int maxWidth, int maxHeight) {
        Bitmap bitmap ;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            // inJustDecodeBounds为true 不分配内存，返回一个空bitmap
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);
            options.inJustDecodeBounds = false;
            // 图片的宽度和高度
            int outputWidth = options.outWidth;
            int outputHeight = options.outHeight;
//			Log.e("ImageUtil", "&&&&&&&&pathName = " + pathName + " outputHeight = " + outputHeight);
            if (maxWidth <= 0) {
                maxWidth = DEFAULT_MAX_WIDTH;
            }
            if (maxHeight <= 0) {
                maxHeight = DEFAULT_MAX_HEIGHT;
            }

            if (outputWidth < maxWidth && outputHeight < maxHeight) {
                bitmap = BitmapFactory.decodeFile(pathName);
            } else {
                int inSampleSize = 0;
                int widthSmapleSize = (int) (outputWidth / maxWidth);
                int heightSmapleSize = (int) (outputHeight / maxHeight);
                if (widthSmapleSize >= heightSmapleSize) {
                    inSampleSize = widthSmapleSize;
                } else {
                    inSampleSize = heightSmapleSize;
                }
                options.inSampleSize = inSampleSize;
                bitmap = BitmapFactory.decodeFile(pathName, options);
            }

        } catch (OutOfMemoryError oom) {
            Log.e("ImageUtil", oom.getMessage(), oom);
            System.gc();
            return null;
        } catch (Exception e) {
            Log.e("ImageUtil", e.getMessage(), e);
            return null;
        }

        return bitmap;
    }

    public static String saveAndCompressPath(Context context, String imagePath){
        String compressPath = ImagePathUtil.getUploadCameraPath(context);
        Bitmap bitmap = getBitmapFromMedia(context,imagePath,0,0);
        if(bitmap!=null&&!bitmap.isRecycled()){
            saveBitmapToSD(bitmap,compressPath);
        }
        return compressPath;
    }

    // 将Bitmap存入SD卡
    public static void saveBitmapToSD(Bitmap mBitmap, String imagePath) {
        File f = new File(imagePath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mBitmap!=null&&!mBitmap.isRecycled()){
            mBitmap.recycle();
            mBitmap =null;
        }
    }
}
