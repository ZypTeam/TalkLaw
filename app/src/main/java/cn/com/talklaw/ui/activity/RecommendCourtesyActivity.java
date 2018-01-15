package cn.com.talklaw.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.utils.WiterQRUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 推荐有礼
 */

public class RecommendCourtesyActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected View bg;
    protected ImageView qrcode;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_recommending_courtesy;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        bg = (View) findViewById(R.id.bg);
        qrcode = (ImageView) findViewById(R.id.qrcode);

    }

    @Override
    public void initAction() {

        titleView.setTitle("推荐有礼");
        titleView.setTitleColor(Color.WHITE);
        titleView.setRightIcon(R.mipmap.icon_audio_share);
        Bitmap bitmap= WiterQRUtil.witerQRCenterLogo("哈哈哈", BitmapFactory.decodeResource(getResources(),R.mipmap.logo)
        , PhoneUtil.dip2px(mContext,271), Color.BLACK,Color.WHITE);
        qrcode.setImageBitmap(bitmap);
    }
}
