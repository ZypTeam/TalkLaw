package cn.com.talklaw.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseView;
import cn.com.talklaw.ui.activity.ArrondiActivity;
import cn.com.talklaw.ui.activity.IntegralActivity;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2514:47
 * @Email zyp@jusfoun.com
 * @Description ${首页 专区 顾问 积分 tab 栏}
 */
public class HomeTabIconView extends BaseView {
    protected RelativeLayout layoutIntegral;
    protected RelativeLayout layoutFree;
    protected RelativeLayout layoutPrivate;
    protected RelativeLayout layoutCompany;

    public HomeTabIconView(Context context) {
        super(context);
    }

    public HomeTabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeTabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_tab_icon_home, this, true);
        layoutIntegral = (RelativeLayout) findViewById(R.id.layout_integral);
        initView(this);
    }

    @Override
    protected void initActions() {
        layoutIntegral.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, IntegralActivity.class);
                mContext.startActivity(intent);
            }
        });

        layoutFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArrondiActivity.class);
                mContext.startActivity(intent);
            }
        });
        layoutPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArrondiActivity.class);
                mContext.startActivity(intent);
            }
        });
        layoutCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArrondiActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    private void initView(View rootView) {
        layoutFree = (RelativeLayout) rootView.findViewById(R.id.layout_free);
        layoutPrivate = (RelativeLayout) rootView.findViewById(R.id.layout_private);
        layoutCompany = (RelativeLayout) rootView.findViewById(R.id.layout_company);
        layoutIntegral = (RelativeLayout) rootView.findViewById(R.id.layout_integral);
    }
}
