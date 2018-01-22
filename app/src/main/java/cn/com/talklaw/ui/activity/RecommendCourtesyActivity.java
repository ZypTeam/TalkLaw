package cn.com.talklaw.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.utils.WiterQRUtil;
import com.jusfoun.baselibrary.Util.PhoneUtil;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.dialog.RecommendationRulesDialog;
import cn.com.talklaw.ui.widget.BackTitleView;
import cn.com.talklaw.ui.dialog.ShareDialog;

/**
 * @author wangcc
 * @date 2018/1/15
 * @describe 推荐有礼
 */

public class RecommendCourtesyActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected View bg;
    protected ImageView qrcode;

    private ShareDialog shareDialog;
    private RecommendationRulesDialog rulesDialog;
    private TextView rules;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_recommending_courtesy;
    }

    @Override
    public void initDatas() {

        shareDialog=new ShareDialog(mContext);
        rulesDialog=new RecommendationRulesDialog(mContext);
    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        bg = (View) findViewById(R.id.bg);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        rules = (TextView) findViewById(R.id.rules);

        qrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveQrcode();
                return false;
            }
        });

    }

    private void saveQrcode(){

    }

    @Override
    public void initAction() {

        titleView.setTitle("推荐有礼");
        titleView.setTitleColor(Color.WHITE);
        titleView.setRightIcon(R.mipmap.icon_share_white);
        titleView.setBackIcon(R.mipmap.icon_back_white);
        titleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.show();
            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulesDialog.show();
            }
        });
        Bitmap bitmap= WiterQRUtil.witerQRCenterLogo("哈哈哈", BitmapFactory.decodeResource(getResources(),R.mipmap.logo)
        , PhoneUtil.dip2px(mContext,271), Color.BLACK,Color.WHITE);
        qrcode.setImageBitmap(bitmap);
    }
}
