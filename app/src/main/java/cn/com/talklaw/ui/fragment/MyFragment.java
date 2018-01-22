package cn.com.talklaw.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;
import com.jusfoun.baselibrary.widget.GlideRoundTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;
import cn.com.talklaw.model.MyAttentionModel;
import cn.com.talklaw.ui.activity.AlreadyPurchaseActivity;
import cn.com.talklaw.ui.activity.MyAttentionActivity;
import cn.com.talklaw.ui.activity.MyConsultActivity;
import cn.com.talklaw.ui.activity.MyInfoActivity;
import cn.com.talklaw.ui.activity.MyMsgListActivity;
import cn.com.talklaw.ui.activity.RecommendCourtesyActivity;
import cn.com.talklaw.ui.activity.SettingActivity;

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

        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.icon_head_def_cir)
                .error(R.mipmap.icon_head_def_cir)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);
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
            default:
                break;
        }
    }

    @Override
    protected void refreshData() {

    }
}
