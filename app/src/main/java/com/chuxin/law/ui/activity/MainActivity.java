package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuxin.law.model.MoveModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.Map;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.comment.ApiService;

import rx.functions.Action1;

public class MainActivity extends BaseTalkLawActivity {

    protected Button login, audio, list;
    protected Button home;
    private TextView txt;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        txt = (TextView) findViewById(R.id.txt);
        login = (Button) findViewById(R.id.login);
        audio = (Button) findViewById(R.id.audio);
        list = (Button) findViewById(R.id.list);
        home = (Button) findViewById(R.id.home);
    }

    @Override
    public void initAction() {
        Map<String, String> params = new HashMap<>();
        addNetwork(Api.getInstance().getService(ApiService.class).getMove(params)
                , new Action1<MoveModel>() {
                    @Override
                    public void call(MoveModel model) {
                        txt.setText(model.getTitle());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        txt.setText(throwable.getMessage());
                    }
                });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, LoginActivity.class);
            }
        });

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, AudioDetailsActivity.class);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(null, ArrondiActivity.class);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(null,HomeActivity.class);
            }
        });
    }

    @Override
    public boolean isNeedSwipe() {
        return false;
    }
}
