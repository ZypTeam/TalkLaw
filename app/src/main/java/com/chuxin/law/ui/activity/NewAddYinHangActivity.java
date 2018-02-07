package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.IntegralModel;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/2/719:58
 * @Email zyp@jusfoun.com
 * @Description ${新增银行卡信息}
 */
public class NewAddYinHangActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected EditText editName;
    protected EditText editNum;
    protected EditText editPhone;
    protected TextView textAfrim;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_add_yinhang;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        editName = (EditText) findViewById(R.id.edit_name);
        editNum = (EditText) findViewById(R.id.edit_num);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        textAfrim = (TextView) findViewById(R.id.text_afrim);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("新增银行卡信息");
        textAfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delMsg();
            }
        });
    }


    private void delMsg() {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();
        if (editName.getText().length() == 0) {
            Toast.makeText(mContext, "请输入持卡人姓名", Toast.LENGTH_SHORT).show();
            return;
        } else if (editNum.getText().length() == 0) {
            Toast.makeText(mContext, "请输入银行号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (editPhone.getText().length() == 0) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("card", editNum.getText().toString());
        map.put("name", editName.getText().toString());
        map.put("phone", editPhone.getText().toString());
        addNetwork(Api.getInstance().getService(ApiService.class).newAddCard(map)
                , new Action1<NoDataModel>() {
                    @Override
                    public void call(NoDataModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            showToast("新增成功");
                            finish();
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
