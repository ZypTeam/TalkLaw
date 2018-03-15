package com.chuxin.law.audioplayer.util;

import android.content.Context;
import android.os.Environment;

import com.chuxin.law.audioplayer.model.StorageInfo;
import com.jusfoun.baselibrary.Util.StringUtil;

import java.io.File;
import java.util.List;

/**
 * @author wangcc
 * @date 2018/3/11
 * @describe 资源文件处理类
 */

public class ResourceFileUtil {

    /**
     * 文件的基本路径
     */
    private static String baseFilePath = null;

    /**
     * 获取资源文件的完整路径
     *
     * @param context
     * @param tempFilePath 文件的临时路径
     * @return
     */
    public static String getFilePath(Context context, String tempFilePath, String fileName) {
        if (baseFilePath == null) {
            List<StorageInfo> storageInfos = StorageListUtil.listAvaliableStorage(context);
            for (int i = 0; i < storageInfos.size(); i++) {
                StorageInfo temp = storageInfos.get(i);
                if (!temp.isRemoveable) {
                    baseFilePath = temp.path;
                    break;
                }
            }
        }

        //
        if (fileName == null) {
            fileName = "";
        }

        if (StringUtil.isEmpty(baseFilePath)){
            baseFilePath= Environment.getExternalStorageDirectory().toString();
        }

        //
        String filePath = baseFilePath + File.separator + tempFilePath + File.separator + fileName;

        File file = new File(filePath);
        if(!fileName.equals("")){
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }else{
            if(!file.exists()){
                file.mkdirs();
            }
        }

        return filePath;
    }
}
