package com.chuxin.law.util.base64;

import android.content.Context;
import android.util.Base64;

import com.chuxin.law.util.ThreadUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author zhaoyapeng
 * @version create time:18/2/519:01
 * @Email zyp@jusfoun.com
 * @Description ${base 64 util}
 */
public class Base64Util {

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     */
    public static void encodeBase64File(Context mContext,final String path, final EncodeCallBack callBack) {
        final String compressPath = ImageCompressUtil.saveAndCompressPath(mContext, path);

        ThreadUtil.getInstance().getFixThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(compressPath);
                FileInputStream inputFile = null;
                byte[] buffer = new byte[(int) file.length()];
                try {
                    inputFile = new FileInputStream(file);
                    inputFile.read(buffer);
                    inputFile.close();
                    String enData = Base64.encodeToString(buffer, Base64.DEFAULT);
                    callBack.callBack(enData);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String encodeBase64File(Context mContext,final String path) {
        final String compressPath = ImageCompressUtil.saveAndCompressPath(mContext, path);
        String result="";
        File file = new File(compressPath);
        FileInputStream inputFile = null;
        byte[] buffer = new byte[(int) file.length()];
        try {
            inputFile = new FileInputStream(file);
            inputFile.read(buffer);
            inputFile.close();
            result = Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static byte[] decode(String data) {
        return Base64.decode(data, Base64.DEFAULT);
    }

    public static String encode(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
