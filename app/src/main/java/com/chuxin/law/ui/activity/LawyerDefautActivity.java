package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.LawyerAudioModel;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.model.OrderResultModel;
import com.chuxin.law.model.ShareModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.adapter.LawyerDefPagerAdapter;
import com.chuxin.law.ui.dialog.ShareDialog;
import com.chuxin.law.util.ImageLoderUtil;
import com.chuxin.law.util.UIUtils;
import com.jusfoun.baselibrary.Util.StringUtil;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;
import com.jusfoun.baselibrary.widget.TitleStatusBarView;

import com.chuxin.law.R;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.EVENT_BUY_LAWYER;
import static com.chuxin.law.ui.activity.CommentListActivity.COMMENT_COUNT;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe 律师详情
 */

public class LawyerDefautActivity extends BaseTalkLawActivity {
    public static final String ID="id";
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
    protected TextView edit;
    protected TextView audio;
    protected TextView image;
    protected TextView video,commentCount;
    protected ViewPager viewpager;

    private String id;
    private LawyerAudioModel audioModel;
    private ShareDialog shareDialog;

    private LawyerDefPagerAdapter adapter;
    private UserModel userModel;
    private LawyerProductModel.LawyerProductData data;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_lawyer_defaut;
    }

    @Override
    public void initDatas() {

        id=getIntent().getStringExtra(ID);
        shareDialog=new ShareDialog(this);
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
        edit = (TextView) findViewById(R.id.edit);
        audio = (TextView) findViewById(R.id.audio);
        image = (TextView) findViewById(R.id.image);
        video = (TextView) findViewById(R.id.video);
        commentCount = (TextView) findViewById(R.id.comment_count);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    public void initAction() {

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

        jifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyForJifen();
            }
        });
        iconHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra(LawyerIntroductionActivity.ID,userModel!=null?userModel.getId():"");
                intent.setClass(mContext, LawyerIntroductionActivity.class);
                mContext.startActivity(intent);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data!=null) {
                    UIUtils.goBuyActivity(mContext, data);
                }
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioModel!=null){
                    if (audioModel.getIs_colle()==1){
                        uncollection();
                    }else {
                        collection();
                    }
                }
            }
        });

        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioModel!=null){
                    if (audioModel.getIs_like()==1){
                        unlike();
                    }else {
                        like();
                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goCommentList(mContext,id);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goCommentList(mContext,id);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioModel==null){
                    return;
                }
                ShareModel shareModel=new ShareModel();
                shareModel.setShareUrl(audioModel.getUrl());
                shareModel.setShareTitle("说法");
                shareModel.setShareContent("说法");
                shareDialog.setShareModel(shareModel);
                shareDialog.show();
            }
        });

        rxManage.on(EVENT_BUY_LAWYER, new Action1<Object>() {
            @Override
            public void call(Object o) {

                if (data!=null&&data.getArticle()!=null){
                    data.getArticle().setIs_buy(1);
                }
            }
        });

        getData();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode,resultCode,data);
        if (requestCode== CommonConstant.COMMENT_RESULT_CODE){
            if (data!=null&&audioModel!=null){
                String count=data.getStringExtra(COMMENT_COUNT);
                commentCount.setText(count);
                audioModel.setCommment_num(count);
            }
        }

        if (shareDialog!=null){
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getData(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).getProductDetails(params)
                , new Action1<LawyerProductModel>() {
                    @Override
                    public void call(LawyerProductModel lawyerProductModel) {
                        hideLoadDialog();
                        if (lawyerProductModel.getCode()== CommonConstant.NET_SUC_CODE){
                            updateView(lawyerProductModel.getData());
                        }else {
                            showToast(lawyerProductModel.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void updateView(LawyerProductModel.LawyerProductData data){
        if (data==null){
            return;
        }
        this.data=data;
        userModel=data.getLawyer();
        ImageLoderUtil.loadCircleImage(mContext,iconHead,userModel.getHeadimg(),R.mipmap.icon_head_def_cir);
        if (StringUtil.isEmpty(userModel.getName())){
            name.setText("王律师");
        }else {
            name.setText(userModel.getName());
        }
        yiban.setText(UIUtils.getText(userModel.getDonenum(),"已办"));
        dengji.setText(UIUtils.getText("专业级","等级"));
        haoping.setText(UIUtils.getText(userModel.getPraise()+"%","好评"));
        shenglv.setText(UIUtils.getText(userModel.getWin()+"%","胜率"));

        audioModel=data.getArticle();
        price.setText(audioModel.getPrice());
        if (audioModel.getIs_colle()==1){
            collection.setImageResource(R.mipmap.icon_lawyer_collection);
        }else {
            collection.setImageResource(R.mipmap.icon_lawyer_collection_un);
        }

        if (audioModel.getIs_like()==1){
            thumbsUp.setImageResource(R.mipmap.icon_lawyer_like);
        }else {
            thumbsUp.setImageResource(R.mipmap.icon_lawyer_like_un);
        }
        buyCount.setText("已购："+audioModel.getBuynum());
        title.setText(audioModel.getTitle());
        jifenCount.setText("积分："+audioModel.getPoint());
        commentCount.setText(audioModel.getCommment_num());
        adapter=new LawyerDefPagerAdapter(getSupportFragmentManager(),data);
        viewpager.setAdapter(adapter);

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition(0);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition(1);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition(2);
            }
        });
        selectPosition(0);
    }

    private void like(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).setLike(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            audioModel.setIs_like(1);
                            thumbsUp.setImageResource(R.mipmap.icon_lawyer_like);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void uncollection(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).delCollection(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            audioModel.setIs_colle(0);
                            collection.setImageResource(R.mipmap.icon_lawyer_collection_un);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void unlike(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).delLike(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            audioModel.setIs_like(0);
                            thumbsUp.setImageResource(R.mipmap.icon_lawyer_like_un);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void collection(){
        showLoadDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("id",id);
        addNetwork(Api.getInstance().getService(ApiService.class).setCollection(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()== CommonConstant.NET_SUC_CODE){
                            audioModel.setIs_colle(1);
                            collection.setImageResource(R.mipmap.icon_lawyer_collection);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    private void buyForJifen(){
        if (data==null||data.getArticle()==null){
            return;
        }
        showLoadDialog();
        Map<String,String> params=new HashMap<>();
        params.put("id",data.getArticle().getId());
        params.put("type","2");
        addNetwork(Api.getInstance().getService(ApiService.class).buyProduct(params)
                , new Action1<OrderResultModel>() {
                    @Override
                    public void call(OrderResultModel noDataModel) {
                        hideLoadDialog();
                        if (noDataModel.getCode()==CommonConstant.NET_SUC_CODE){
                            data.getArticle().setIs_buy(1);
                            showToast("兑换成功");
                            return;
                        }
                        showToast(noDataModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                        showToast("兑换失败");
                    }
                });
    }


}
