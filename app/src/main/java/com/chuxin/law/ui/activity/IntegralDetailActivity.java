package com.chuxin.law.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.IntegralDetailDataModel;
import com.chuxin.law.ui.adapter.IntegralDetailListAdapter;
import com.jusfoun.baselibrary.net.Api;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/1/717:56
 * @Email zyp@jusfoun.com
 * @Description ${积分详情}
 */
public class IntegralDetailActivity extends BaseTalkLawActivity {
    protected TextView textCount;
    protected TextView textShop;
    protected TextView textZhuanqu;
    protected TextView textOrder;
    protected RecyclerView recyclerView;
    protected ImageView imgBack;
    protected ImageView imgBofang;
    protected TextView guizeText;

    private IntegralDetailListAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_integral_detail;
    }

    @Override
    public void initDatas() {
        adapter = new IntegralDetailListAdapter(mContext);
    }

    @Override
    public void initView() {
        textCount = (TextView) findViewById(R.id.text_count);
        textShop = (TextView) findViewById(R.id.text_shop);
        textZhuanqu = (TextView) findViewById(R.id.text_zhuanqu);
        textOrder = (TextView) findViewById(R.id.text_order);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBofang = (ImageView) findViewById(R.id.img_bofang);

        guizeText = (TextView) findViewById(R.id.text_guize);


    }

    @Override
    public void initAction() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        textShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textZhuanqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, RecommendCourtesyActivity.class);
            }
        });

        textOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, ExchangeRecordsActivity.class);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        guizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url","http://api.law.wzgeek.com/html/integral.html");
                intent.putExtra("title","积分规则");
                startActivity(intent);
            }
        });
        delMsg();

    }

    private void delMsg() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getIntegralDetail()
                , new Action1<IntegralDetailDataModel>() {
                    @Override
                    public void call(IntegralDetailDataModel model) {
                        hideLoadDialog();
                        if (model.getCode() == NET_SUC_CODE) {
                            if (model.data != null && model.data.list != null) {
                                adapter.refreshList(model.data.list);
                                textCount.setText(model.data.pointTotle);
                            }
                        } else {
                            Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
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
