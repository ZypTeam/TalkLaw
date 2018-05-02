package com.chuxin.law.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.common.UserInfoDelegate;
import com.chuxin.law.model.ShippingAddressSp;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.sharedpreferences.ShippingAddressModel;
import com.chuxin.law.ui.activity.AlreadyPurchaseActivity;
import com.chuxin.law.ui.activity.ApplyForWithdrawalsActivity;
import com.chuxin.law.ui.activity.IntegralActivity;
import com.chuxin.law.ui.activity.MyAttentionActivity;
import com.chuxin.law.ui.activity.MyConsultActivity;
import com.chuxin.law.ui.activity.MyInfoActivity;
import com.chuxin.law.ui.activity.MyMsgListActivity;
import com.chuxin.law.ui.activity.RecommendCourtesyActivity;
import com.chuxin.law.ui.activity.SettingActivity;
import com.chuxin.law.ui.activity.ShippingAddressActivity;
import com.chuxin.law.ui.activity.WebViewActivity;
import com.chuxin.law.util.UIUtils;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.BUY_SUC_BY_JIFEN;
import static com.chuxin.law.common.CommonConstant.EDIT_HEADER;
import static com.chuxin.law.common.CommonConstant.EVENT_BUY_LAWYER;

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
    private TextView recommend,fabu;
    private ImageView msg;
    private UserModel userModel;
    private Drawable dAuthUn, dAuth;

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
        dAuth = mContext.getResources().getDrawable(R.mipmap.icon_lawyer_auth);
        dAuth.setBounds(0, 0, dAuth.getIntrinsicWidth(), dAuth.getIntrinsicHeight());
        dAuthUn = mContext.getResources().getDrawable(R.mipmap.icon_lawyer_auth_un);
        dAuthUn.setBounds(0, 0, dAuthUn.getIntrinsicWidth(), dAuthUn.getIntrinsicHeight());
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
        fabu = rootView.findViewById(R.id.fabu);
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
        btnTixian.setOnClickListener(this);

        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url","http://api.law.wzgeek.com/html/send.html");
                intent.putExtra("title","发布产品");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_head:
                Intent intent1 = new Intent(mContext, MyInfoActivity.class);
                startActivityForResult(intent1, CommonConstant.REQUEST_HEAD_CHANGE);
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
                if (userModel != null) {
                    if (userModel.getType() != 2) {
                        UIUtils.goLawyerAuth(mContext);
                    }
                }
                break;

            case R.id.btn_tixian:
                goActivity(null, ApplyForWithdrawalsActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CommonConstant.FOLLOW_RESULT_CODE:
                if (data != null) {
                    String follow=data.getStringExtra(MyAttentionActivity.FOLLOW_COUNT);
                    userModel.setFollow(follow);
                    followCount.setText(follow);
                    UserInfoDelegate.getInstance().saveUserInfo(userModel);
                }
                break;
            case CommonConstant.LAWYER_AUTH_RESULT_CODE:
                break;
            case CommonConstant.REQUEST_HEAD_CHANGE:
                updateUserInfo();
                break;
            default:
                break;
        }
    }

    @Override
    protected void refreshData() {
        getUserInfo(true);
//        updateUserInfo();
    }

    @Override
    public void refresh(){
        getUserInfo(false);
    }

    private void getUserInfo(boolean show) {
        if (show) {
            showLoadDialog();
        }
        addNetwork(Api.getInstance().getService(ApiService.class).getUserInfo()
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == CommonConstant.NET_SUC_CODE && model.getData() != null) {
                            UserInfoDelegate.getInstance().saveUserInfo(model.getData());
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

        Log.e("tag", "updateUserInfo=" + userModel.getHeadimg());
        Glide.with(mContext)
                .load(userModel.getHeadimg())
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        name.setText(userModel.getName());
//        yxlCount.setText(userModel.getLevel());
        buyCount.setText(userModel.getDonenum());
        followCount.setText(userModel.getFollow());
//        myAddressContent.setText(userModel.getAddress());
        if (StringUtil.isEmpty(userModel.getMoney())){
            zhuanghuCount.setText("¥0");
        }else {
            zhuanghuCount.setText("¥" + userModel.getMoney());
        }
        if (StringUtil.isEmpty(userModel.getPoints())){
            jifenCount.setText("0");
        }else {
            jifenCount.setText(userModel.getPoints());
        }
        if (userModel.getType() != 2) {
            auth.setText("未认证");
            auth.setTextColor(Color.parseColor("#bababa"));
            auth.setCompoundDrawables(null, null, dAuthUn, null);
        } else {
            auth.setTextColor(getResources().getColor(R.color.app_red_color));
            if (userModel.getLaw() != null && userModel.getLaw().getLevel() != null)
                auth.setText(userModel.getLaw().getLevel());
            auth.setCompoundDrawables(null, null, dAuth, null);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ShippingAddressModel.ShippingAddressItemModel model = ShippingAddressSp.getSelectShippingAddress(mContext);
            if (model == null|| TextUtils.isEmpty(model.city)) {
                myAddressContent.setText("暂无");
            } else {
                myAddressContent.setText(model.city);
            }
            RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
                @Override
                public void onSuccess(List<Conversation> conversations) {
                    Log.e("tag","getConversationList="+conversations);
                    if(conversations!=null&&conversations.size()>0){
                        MessageContent messageContent = conversations.get(0).getLatestMessage();
                        if ("RC:TxtMsg".equals(conversations.get(0).getObjectName())) {
                            zixunContent.setText(((TextMessage) (messageContent)).getContent());
                        } else if ("RC:ImgMsg".equals(conversations.get(0).getObjectName())) {
                            zixunContent.setText("[图片]");
                        } else if ("APP:MyPay".equals(conversations.get(0).getObjectName())) {
                            zixunContent.setText("[保证金]");
                        }
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }


    }
}
