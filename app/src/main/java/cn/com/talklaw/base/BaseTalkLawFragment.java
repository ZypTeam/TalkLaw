package cn.com.talklaw.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jusfoun.baselibrary.base.BaseFragment;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe
 */

public abstract class BaseTalkLawFragment extends BaseFragment{

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    /**
     * 跳转界面
     * @param bundle 传递数据，为NULL不传递
     * @param cls 跳转的界面
     */
    protected void goActivity(Bundle bundle, Class<?> cls){
        Intent intent=new Intent();
        intent.setClass(mContext,cls);
        if (bundle!=null)
            intent.putExtras(bundle);
       startActivity(intent);
    }
}
