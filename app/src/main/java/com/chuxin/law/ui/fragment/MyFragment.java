package com.chuxin.law.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.model.ShippingAddressModel;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.activity.IntegralActivity;
import com.chuxin.law.ui.activity.LawyerAuthActivity;
import com.chuxin.law.ui.activity.MyMsgListActivity;
import com.chuxin.law.ui.activity.RecommendCourtesyActivity;
import com.chuxin.law.ui.activity.SettingActivity;
import com.chuxin.law.ui.activity.ShippingAddressActivity;
import com.chuxin.law.ui.sharedpreferences.ShippingAddressSp;
import com.chuxin.law.ui.util.UIUtils;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.ui.activity.AlreadyPurchaseActivity;
import com.chuxin.law.ui.activity.MyAttentionActivity;
import com.chuxin.law.ui.activity.MyConsultActivity;
import com.chuxin.law.ui.activity.MyInfoActivity;

import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class MyFragment extends BaseTalkLawFragment implements View.OnClickListener {

    protected ImageView iconHead;
    protected TextView name;
    protected TextView yxlCount;
    protected TextView buyCount;
    protected TextView followCount;
    protected TextView txtZhanghuInfo;
    protected TextView zhuanghu;
    protected TextView jifen;
    protected TextView zhuanghuCount;
    protected TextView jifenCount;
    protected Button btnTixian;
    protected Button btnJifen;
    protected TextView myZixun;
    protected TextView myZixunAll;
    protected TextView zixunContent;
    protected TextView zixunA;
    protected TextView myAddress;
    protected TextView editAddress;
    protected ImageView imgAddress;
    protected TextView myAddressContent, auth;
    private ImageView setting;
    private TextView recommend;
    private ImageView msg;
    private UserModel userModel;
    private Drawable dAuthUn,dAuth;

    public static MyFragment getInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initDatas() {
        dAuth=mContext.getResources().getDrawable(R.mipmap.icon_lawyer_auth);
        dAuth.setBounds(0,0,dAuth.getIntrinsicWidth(),dAuth.getIntrinsicHeight());
        dAuthUn=mContext.getResources().getDrawable(R.mipmap.icon_lawyer_auth_un);
        dAuthUn.setBounds(0,0,dAuthUn.getIntrinsicWidth(),dAuthUn.getIntrinsicHeight());
    }

    @Override
    public void initView(View rootView) {
        iconHead = (ImageView) rootView.findViewById(R.id.icon_head);
        name = (TextView) rootView.findViewById(R.id.name);
        yxlCount = (TextView) rootView.findViewById(R.id.yxl_count);
        buyCount = (TextView) rootView.findViewById(R.id.buy_count);
        followCount = (TextView) rootView.findViewById(R.id.follow_count);
        txtZhanghuInfo = (TextView) rootView.findViewById(R.id.txt_zhanghu_info);
        zhuanghu = (TextView) rootView.findViewById(R.id.zhuanghu);
        jifen = (TextView) rootView.findViewById(R.id.jifen);
        zhuanghuCount = (TextView) rootView.findViewById(R.id.zhuanghu_count);
        jifenCount = (TextView) rootView.findViewById(R.id.jifen_count);
        btnTixian = (Button) rootView.findViewById(R.id.btn_tixian);
        btnJifen = (Button) rootView.findViewById(R.id.btn_jifen);
        myZixun = (TextView) rootView.findViewById(R.id.my_zixun);
        myZixunAll = (TextView) rootView.findViewById(R.id.my_zixun_all);
        zixunContent = (TextView) rootView.findViewById(R.id.zixun_content);
        zixunA = (TextView) rootView.findViewById(R.id.zixun_a);
        myAddress = (TextView) rootView.findViewById(R.id.my_address);
        editAddress = (TextView) rootView.findViewById(R.id.edit_address);
        imgAddress = (ImageView) rootView.findViewById(R.id.img_address);
        myAddressContent = (TextView) rootView.findViewById(R.id.my_address_content);
        recommend = (TextView) rootView.findViewById(R.id.recommend);
        auth = (TextView) rootView.findViewById(R.id.auth);
        msg = (ImageView) rootView.findViewById(R.id.msg);

        setting = rootView.findViewById(R.id.setting);
    }


    @Override
    public void initAction() {

        iconHead.setOnClickListener(this);
        myZixunAll.setOnClickListener(this);
        setting.setOnClickListener(this);
        followCount.setOnClickListener(this);
        buyCount.setOnClickListener(this);
        recommend.setOnClickListener(this);
        msg.setOnClickListener(this);
        editAddress.setOnClickListener(this);
        btnJifen.setOnClickListener(this);
        auth.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_head:
                goActivity(null, MyInfoActivity.class);
                break;
            case R.id.my_zixun_all:
                goActivity(null, MyConsultActivity.class);
                break;
            case R.id.msg:
                goActivity(null, MyMsgListActivity.class);
                break;
            case R.id.setting:
                goActivity(null, SettingActivity.class);
                break;
            case R.id.follow_count:
                Intent intent = new Intent(mContext, MyAttentionActivity.class);
                startActivityForResult(intent, CommonConstant.FOLLOW_RESULT_CODE);
                break;
            case R.id.buy_count:
                goActivity(null, AlreadyPurchaseActivity.class);
                break;
            case R.id.recommend:
                Bundle bundle = new Bundle();
                if (userModel != null) {
                    bundle.putString(RecommendCourtesyActivity.URL, userModel.getQrcode());
                }
                goActivity(bundle, RecommendCourtesyActivity.class);
                break;
            case R.id.edit_address:
                goActivity(null, ShippingAddressActivity.class);
                break;
            case R.id.btn_jifen:
                goActivity(null, IntegralActivity.class);
                break;
            case R.id.auth:
                if (userModel!=null) {
                    if (userModel.getType()==1) {
                        UIUtils.goLawyerAuth(mContext);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.FOLLOW_RESULT_CODE:
                if (data != null) {
                    userModel.setFollow(data.getStringExtra(MyAttentionActivity.FOLLOW_COUNT));
                    TalkLawApplication.saveUserInfo(userModel);
                }
                break;
            case CommonConstant.LAWYER_AUTH_RESULT_CODE:
                break;
        }
    }

    @Override
    protected void refreshData() {
//        getUserInfo();
        updateUserInfo();
    }

    private void getUserInfo() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getUserInfo()
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == CommonConstant.NET_SUC_CODE && model.getData() != null) {
                            TalkLawApplication.saveUserInfo(model.getData());
                        }
                        updateUserInfo();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        updateUserInfo();
                    }
                });
    }

    private void updateUserInfo() {
        userModel = TalkLawApplication.getUserInfo();
        if (userModel == null) {
            return;
        }
        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        name.setText(userModel.getName());
//        yxlCount.setText(userModel.getLevel());
        followCount.setText(userModel.getFollow());
        myAddressContent.setText(userModel.getAddress());
        zhuanghuCount.setText("¥" + userModel.getMoney());
        jifenCount.setText(userModel.getPoints());
        if (userModel.getType() ==1) {
            auth.setText("未认证");
            auth.setTextColor(Color.parseColor("#bababa"));
            auth.setCompoundDrawables(null,null,dAuthUn,null);
        } else {
            auth.setTextColor(getResources().getColor(R.color.app_red_color));
            auth.setText(userModel.getLaw().getLevel());
            auth.setCompoundDrawables(null,null,dAuth,null);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ShippingAddressModel.ShippingAddressItemModel model = ShippingAddressSp.getSelectShippingAddress(mContext);
        if (model == null) {
            myAddressContent.setText("暂无");
        } else {
            myAddressContent.setText(model.city);
        }

    }
}
