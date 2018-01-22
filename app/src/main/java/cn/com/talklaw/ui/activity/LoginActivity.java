package cn.com.talklaw.ui.activity;

import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jusfoun.baselibrary.Util.LogUtil;
import com.jusfoun.baselibrary.base.BaseActivity;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import cn.com.talklaw.R;
import cn.com.talklaw.TalkLawApplication;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.comment.ApiService;
import cn.com.talklaw.model.UserInfoModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;

/**
 * @author wangcc
 * @date 2017/12/12
 * @describe
 */

public class LoginActivity extends BaseTalkLawActivity {
    protected TextView txt;
    protected EditText number;
    protected EditText code;
    protected TextView getCode;
    protected Button login;
    protected TextView loginAgree;
    protected ImageView qq;
    protected ImageView weibo;
    protected ImageView wechat;
    private Subscription timer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        txt = (TextView) findViewById(R.id.txt);
        number = (EditText) findViewById(R.id.number);
        code = (EditText) findViewById(R.id.code);
        getCode = (TextView) findViewById(R.id.get_code);
        login = (Button) findViewById(R.id.login);
        loginAgree = (TextView) findViewById(R.id.login_agree);
        qq = (ImageView) findViewById(R.id.qq);
        weibo = (ImageView) findViewById(R.id.weibo);
        wechat = (ImageView) findViewById(R.id.wechat);

    }

    @Override
    public void initAction() {
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCode();
                getCode(code.getText().toString());

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        HashMap<String,String> params=new HashMap<>();
        params.put("phone",number.getText().toString());
        params.put("code",code.getText().toString());
        addNetwork(Api.getInstance().getService(ApiService.class).login(params)
                , new Action1<UserInfoModel>() {
                    @Override
                    public void call(UserInfoModel userInfoModel) {
                        if (userInfoModel!=null&&userInfoModel.getCode()==10000){
                            Toast.makeText(mContext,"成功",Toast.LENGTH_SHORT).show();
                            TalkLawApplication.saveUserInfo(userInfoModel.getData());
                        }else {
                            Toast.makeText(mContext,userInfoModel.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("login",throwable.getMessage());
                        Toast.makeText(mContext,"失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getCode(String number){
        HashMap<String,String> params=new HashMap<>();
        params.put("phone",number);
        params.put("type","1");
        addNetwork(Api.getInstance().getService(ApiService.class).getAuthCode(params)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel noDataModel) {
                        Log.e("noDataModel",noDataModel.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("throwable",throwable.getMessage());
                    }
                });
    }

    private void startCode(){
        getCode.setEnabled(false);
        timer= Observable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return 60-aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {
                        getCode.setEnabled(true);
                        getCode.setText("获取验证码");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getCode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        getCode.setText(aLong+"");
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer.isUnsubscribed()){
            timer.unsubscribe();
        }
    }
}
