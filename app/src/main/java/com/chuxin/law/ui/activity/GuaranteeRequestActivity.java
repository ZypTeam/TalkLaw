package com.chuxin.law.ui.activity;

import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.GuaranteeRequestModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ry.my.mymessage.PayMessage;
import com.chuxin.law.ui.widget.BackTitleView;
import com.google.gson.Gson;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:18/3/917:36
 * @Email zyp@jusfoun.com
 * @Description ${保证金申请页面}
 */
public class GuaranteeRequestActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected EditText editMoney;
    protected TextView textAfirm;
    protected ImageView imgAvatar;
    protected TextView textName;

    private String targetId;
    private String toUserId="";


    @Override
    public int getLayoutResId() {
        return R.layout.activity_guarantee_request;
    }

    @Override
    public void initDatas() {
        targetId = getIntent().getStringExtra("targetId");
        toUserId = getIntent().getStringExtra("toUserId");
    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        editMoney = (EditText) findViewById(R.id.edit_money);
        textAfirm = (TextView) findViewById(R.id.text_afirm);
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        textName = (TextView) findViewById(R.id.text_name);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("保证金申请");

        UserModel userModel = TalkLawApplication.getUserInfo();
        if (userModel != null) {
            Glide.with(mContext)
                    .load(userModel.getHeadimg())
                    .placeholder(R.mipmap.icon_head_def_cir)
                    .error(R.mipmap.icon_head_def_cir)
                    .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                    .crossFade()
                    .into(imgAvatar);
            textName.setText(userModel.getName());
        }


        textAfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editMoney.getText())){
                    showToast("请输入金额");
                    return;
                }

                showLoadDialog();

                Log.e("tag","setOnClickListener="+editMoney.getText().toString()+" "+TalkLawApplication.getUserId());

                getOrderData();

            }
        });


    }

    private void getOrderData() {
        showLoadDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("touserid", toUserId);
        params.put("consult", targetId);
        params.put("money", editMoney.getText().toString());

        addNetwork(Api.getInstance().getService(ApiService.class).getOrderData(params)
                , new Action1<GuaranteeRequestModel>() {
                    @Override
                    public void call(GuaranteeRequestModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {

                            PayMessage payMessage =  PayMessage.obtain(noDataModel.data.order,toUserId,editMoney.getText().toString());
                            RongIM.getInstance().sendMessage(Message.obtain(targetId, Conversation.ConversationType.GROUP, payMessage),
                                    "保证金", "保证金", new IRongCallback.ISendMessageCallback() {
                                        @Override
                                        public void onAttached(Message message) {
                                            Log.e("tag","RongIM-onAttached");
                                        }

                                        @Override
                                        public void onSuccess(Message message) {
                                            hideLoadDialog();
                                            finish();
                                            Log.e("tag","RongIM-onSuccess");
                                        }

                                        @Override
                                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                            hideLoadDialog();
                                            showToast("申请失败，请重试");
                                            Log.e("tag","RongIM-onError="+new Gson().toJson(message)+" "+errorCode);
                                        }
                                    });

                        }else{
                            showToast(noDataModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }
}
