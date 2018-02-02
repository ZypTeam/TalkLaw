package com.chuxin.law.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.comment.ApiService;
import com.chuxin.law.model.UserInfoModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.activity.MyMsgListActivity;
import com.chuxin.law.ui.activity.RecommendCourtesyActivity;
import com.chuxin.law.ui.activity.SettingActivity;
import com.chuxin.law.ui.activity.ShippingAddressActivity;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import com.chuxin.law.R;

import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.comment.CommentConstant;
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
    protected TextView myAddressContent;
    private ImageView setting;
    private TextView recommend;
    private ImageView msg;

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
                goActivity(null, MyAttentionActivity.class);
                break;
            case R.id.buy_count:
                goActivity(null, AlreadyPurchaseActivity.class);
                break;
            case R.id.recommend:
                goActivity(null, RecommendCourtesyActivity.class);
                break;
            case R.id.edit_address:
                goActivity(null, ShippingAddressActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void refreshData() {
        getUserInfo();
    }

    private void getUserInfo(){
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getUserInfo()
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel model) {
                        hideLoadDialog();
                        if (model!=null&&model.getCode()== CommentConstant.NET_SUC_CODE&&model.getData()!=null){
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

    private void updateUserInfo(){
        UserModel model= TalkLawApplication.getUserInfo();
        if (model==null){
            return;
        }
        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        name.setText(model.getName());
    }
}
