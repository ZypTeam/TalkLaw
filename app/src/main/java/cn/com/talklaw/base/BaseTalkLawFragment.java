package cn.com.talklaw.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jusfoun.baselibrary.base.BaseViewPagerFragment;
import com.jusfoun.baselibrary.permissiongen.PermissionGen;

import cn.com.talklaw.R;
import cn.com.talklaw.ui.dialog.LoadingDialog;

/**
 * @author wangcc
 * @date 2017/11/17
 * @describe
 */

public abstract class BaseTalkLawFragment extends BaseViewPagerFragment{

    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    private void initDialog(){
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mContext, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    protected void showLoadDialog(){
        if(mContext == null){
            Log.e("TAG", "该Activity已经销毁，但仍欲显示dialog");
            return;
        }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void hideLoadDialog(){
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
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
