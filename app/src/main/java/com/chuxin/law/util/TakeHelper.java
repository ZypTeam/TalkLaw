package com.chuxin.law.util;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;

import java.io.File;

/**
 * @author wangcc
 * @date 2018/4/18
 * @describe
 */
public class TakeHelper {

    /**
     * 照相 裁剪
     * @param takePhoto
     */

    public static void pickByTakeCorp(TakePhoto takePhoto){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Log.e("pickByTakeCorp",file.getParentFile().getPath());
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
    }

    /**
     * 照相 不裁剪
     * @param takePhoto
     */

    public static void pickByTake(TakePhoto takePhoto){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        takePhoto.onPickFromCapture(imageUri);
    }

    /**
     * 单选从相册 裁剪
     *
     * @param takePhoto
     */
    public static void pickBySelectCrop(TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
//        configCompress(takePhoto);
        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    }

    /**
     * 单选从相册 裁剪
     *
     * @param takePhoto
     */
    public static void pickBySelect(TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        takePhoto.onPickFromGallery();
    }

    private static void configCompress(TakePhoto takePhoto) {
        int maxSize = 2*1024*1024;
        boolean showProgressBar = false;
        boolean enableRawFile = true;
        CompressConfig compressConfig = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(compressConfig, showProgressBar);
    }

    private static CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        boolean withWonCrop = true;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
