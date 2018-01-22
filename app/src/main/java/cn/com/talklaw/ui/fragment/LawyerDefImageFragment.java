package cn.com.talklaw.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jusfoun.baselibrary.widget.GlideCircleTransform;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;

/**
 * @author wangcc
 * @date 2018/1/21
 * @describe 律师 图文 fragemnt
 */

public class LawyerDefImageFragment extends BaseTalkLawFragment {

    protected ImageView img;

    public static LawyerDefImageFragment getInstance() {
        LawyerDefImageFragment fragment = new LawyerDefImageFragment();
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

    }

    @Override
    public void initView(View rootView) {
        img = (ImageView) rootView.findViewById(R.id.img);

    }

    @Override
    public void initAction() {
        Glide.with(mContext)
                .load("http://img10.3lian.com/sc6/show/s11/19/20110711104956189.jpg")
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .transform(new CenterCrop(mContext))
                .crossFade()
                .into(img);
    }
}
