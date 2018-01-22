package cn.com.talklaw.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/3
 * @describe
 */

public class MyInfoActivity extends BaseTalkLawActivity {
    protected BackTitleView titleView;
    protected TextView head;
    protected ImageView iconHead;
    protected TextView name;
    protected TextView userName;
    protected View lineName;
    protected TextView nickname;
    protected TextView userNickname;
    protected TextView sex;
    protected TextView userSex;
    protected TextView address;
    protected TextView birthday;
    protected TextView userBirthday;
    protected TextView number;
    protected TextView userNumber;
    protected TextView mail;
    protected TextView userMail;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleView = (BackTitleView) findViewById(R.id.titleView);
        head = (TextView) findViewById(R.id.head);
        iconHead = (ImageView) findViewById(R.id.icon_head);
        name = (TextView) findViewById(R.id.name);
        userName = (TextView) findViewById(R.id.user_name);
        lineName = (View) findViewById(R.id.line_name);
        nickname = (TextView) findViewById(R.id.nickname);
        userNickname = (TextView) findViewById(R.id.user_nickname);
        sex = (TextView) findViewById(R.id.sex);
        userSex = (TextView) findViewById(R.id.user_sex);
        address = (TextView) findViewById(R.id.address);
        birthday = (TextView) findViewById(R.id.birthday);
        userBirthday = (TextView) findViewById(R.id.user_birthday);
        number = (TextView) findViewById(R.id.number);
        userNumber = (TextView) findViewById(R.id.user_number);
        mail = (TextView) findViewById(R.id.mail);
        userMail = (TextView) findViewById(R.id.user_mail);

    }

    @Override
    public void initAction() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null,SelectAreaActivity.class);
            }
        });

        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);
    }
}
