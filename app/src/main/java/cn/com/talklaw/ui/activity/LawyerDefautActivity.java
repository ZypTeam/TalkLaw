package cn.com.talklaw.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.adapter.LawyerDefPagerAdapter;
import cn.com.talklaw.ui.util.UIUtils;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 律师详情
 */

public class LawyerDefautActivity extends BaseTalkLawActivity {
    protected TextView title;
    protected TitleStatusBarView titleBar;
    protected ImageView iconHead;
    protected TextView name;
    protected TextView content;
    protected TextView buy;
    protected TextView jifen;
    protected TextView price;
    protected TextView jifenCount;
    protected TextView buyCount;
    protected TextView yiban;
    protected TextView dengji;
    protected TextView haoping;
    protected TextView shenglv;
    protected ImageView back;
    protected ImageView share;
    protected ImageView collection;
    protected ImageView thumbsUp;
    protected ImageView comment;
    protected EditText edit;
    protected TextView audio;
    protected TextView image;
    protected TextView video;
    protected ViewPager viewpager;

    private LawyerDefPagerAdapter adapter;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_defaut;
    }

    @Override
    public void initDatas() {
        adapter=new LawyerDefPagerAdapter(getSupportFragmentManager());
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title);
        titleBar = (TitleStatusBarView) findViewById(R.id.title_bar);
        iconHead = (ImageView) findViewById(R.id.icon_head);
        name = (TextView) findViewById(R.id.name);
        content = (TextView) findViewById(R.id.content);
        buy = (TextView) findViewById(R.id.buy);
        jifen = (TextView) findViewById(R.id.jifen);
        price = (TextView) findViewById(R.id.price);
        jifenCount = (TextView) findViewById(R.id.jifen_count);
        buyCount = (TextView) findViewById(R.id.buy_count);
        yiban = (TextView) findViewById(R.id.yiban);
        dengji = (TextView) findViewById(R.id.dengji);
        haoping = (TextView) findViewById(R.id.haoping);
        shenglv = (TextView) findViewById(R.id.shenglv);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        collection = (ImageView) findViewById(R.id.collection);
        thumbsUp = (ImageView) findViewById(R.id.thumbs_up);
        comment = (ImageView) findViewById(R.id.comment);
        edit = (EditText) findViewById(R.id.edit);
        audio = (TextView) findViewById(R.id.audio);
        image = (TextView) findViewById(R.id.image);
        video = (TextView) findViewById(R.id.video);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    public void initAction() {

        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .transform(new CenterCrop(mContext),new GlideCircleTransform(mContext))
                .crossFade()
                .into(iconHead);

        yiban.setText(UIUtils.getText("1254","已办"));
        dengji.setText(UIUtils.getText("专业级","等级"));
        haoping.setText(UIUtils.getText("100%","好评"));
        shenglv.setText(UIUtils.getText("100%","胜率"));

        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectPosition(0);
    }

    private void selectPosition(int position) {
        audio.setTextColor(Color.parseColor("#999999"));
        audio.setBackgroundResource(R.color.transparent);
        image.setTextColor(Color.parseColor("#999999"));
        image.setBackgroundResource(R.color.transparent);
        video.setTextColor(Color.parseColor("#999999"));
        video.setBackgroundResource(R.color.transparent);
        viewpager.setCurrentItem(position);
        switch (position) {
            case 0:
                audio.setTextColor(Color.parseColor("#cb1f28"));
                audio.setBackgroundResource(R.drawable.bg_red_line);
                break;
            case 1:
                image.setTextColor(Color.parseColor("#cb1f28"));
                image.setBackgroundResource(R.drawable.bg_red_line);
                break;
            case 2:
                video.setTextColor(Color.parseColor("#cb1f28"));
                video.setBackgroundResource(R.drawable.bg_red_line);
                break;
            default:
                break;
        }
    }
}
