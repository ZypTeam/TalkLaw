package com.chuxin.law.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawFragment;
import com.chuxin.law.model.LawyerProductModel;
import com.chuxin.law.ui.util.LawyerDefViewPagerUtils;
import com.chuxin.law.ui.util.UIUtils;
import com.jusfoun.baselibrary.Util.StringUtil;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 图文 fragemnt
 */

public class LawyerDefImageFragment extends BaseTalkLawFragment {

    protected ImageView img;
    protected TextView content;
    protected TextView dashang;
    private String mContent;
    private String url;
    private String imgUrl;
    private String id;
    private LawyerProductModel.LawyerProductData data;

    public static LawyerDefImageFragment getInstance(Bundle args) {
        LawyerDefImageFragment fragment = new LawyerDefImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_lawyer_def_image;
    }

    @Override
    public void initDatas() {
        data= (LawyerProductModel.LawyerProductData) getArguments().getSerializable(LawyerDefViewPagerUtils.DATA);
        if (data==null||data.getArticle()==null||data.getLawyer()==null){
            return;
        }
        mContent = data.getArticle().getContent();
        imgUrl=data.getArticle().getImg();
        id=data.getLawyer().getUserid();
    }

    @Override
    public void initView(View rootView) {
        img = (ImageView) rootView.findViewById(R.id.img);
        content = (TextView) rootView.findViewById(R.id.content);
        dashang = (TextView) rootView.findViewById(R.id.dashang);

    }

    @Override
    public void initAction() {
        if (StringUtil.isEmpty(imgUrl)||!imgUrl.startsWith("http://")) {
            imgUrl = "http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg";
        }
        Glide.with(mContext)
                .load(imgUrl)
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .transform(new CenterCrop(mContext))
                .crossFade()
                .into(img);


        content.setText(UIUtils.getHtmlTxt(mContent));
        dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goGratuity(mContext,data);
            }
        });
    }
}
