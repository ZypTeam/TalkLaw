package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.AdModel;
import com.chuxin.law.util.ImageLoderUtil;
import com.jusfoun.baselibrary.task.WeakHandler;

/**
 * @author zhaoyapeng
 * @version create time:18/4/1021:05
 * @Email zyp@jusfoun.com
 * @Description ${广告activity}
 */
public class AdActivity extends BaseTalkLawActivity {
    protected ImageView imgAd;
    protected TextView skipView;
    private AdModel.AdDataModel adDataModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ad;
    }

    @Override
    public void initDatas() {
        if(getIntent().getExtras()!=null)
        adDataModel = (AdModel.AdDataModel)getIntent().getExtras().get("adDataModel");
    }

    @Override
    public void initView() {
        imgAd = (ImageView) findViewById(R.id.img_ad);
        skipView = (TextView) findViewById(R.id.skip_view);


        skipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(task);
                goActivity(null, HomeActivity.class);
            }
        });
        imgAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adDataModel!=null) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", adDataModel.url);
                    intent.putExtra("title", "广告");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initAction() {

        handler.postDelayed(task, 2000);

        if(adDataModel!=null)
            Glide.with(mContext)
                    .load(adDataModel.img)
                    .crossFade()
                    .into(imgAd);

    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            goActivity(null, HomeActivity.class);
        }
    };
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            /*if (msg.what == net) {
                netSuccess = true;
                if (handerSuc) {
                    goNextActivity();
                }
            } else if (msg.what == daley) {
                handerSuc = true;
                if (netSuccess) {
                    goNextActivity();
                }
            }*/
            return false;
        }
    });
}
