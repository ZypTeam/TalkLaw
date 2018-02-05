package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.sharedpreferences.ShippingAddressModel;
import com.chuxin.law.model.ShippingAddressSp;
import com.chuxin.law.ui.widget.BackTitleView;

import java.util.ArrayList;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1910:27
 * @Email zyp@jusfoun.com
 * @Description ${添加地址 activity}
 */
public class SelectAreaActivity extends BaseTalkLawActivity {
    protected BackTitleView viewTitleBar;
    protected EditText textSelectCity;
    protected EditText editArea;
    protected EditText editDetailAddress;
    protected EditText textUsername;
    protected TextView textSexMan;
    protected TextView textSexWoman;
    protected EditText editPhone;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_select_area;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        viewTitleBar = (BackTitleView) findViewById(R.id.view_title_bar);
        textSelectCity = (EditText) findViewById(R.id.text_select_city);
        editArea = (EditText) findViewById(R.id.edit_area);
        editDetailAddress = (EditText) findViewById(R.id.edit_detail_address);
        textUsername = (EditText) findViewById(R.id.text_username);
        textSexMan = (TextView) findViewById(R.id.text_sex_man);
        textSexWoman = (TextView) findViewById(R.id.text_sex_woman);
        editPhone = (EditText) findViewById(R.id.edit_phone);

    }

    @Override
    public void initAction() {
        viewTitleBar.setTitle("添加地址");
        viewTitleBar.setRightText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textUsername.getText().length() == 0) {
                    Toast.makeText(mContext, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editPhone.getText().length() == 0) {
                    Toast.makeText(mContext, "请输入联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textSelectCity.getText().length() == 0) {
                    Toast.makeText(mContext, "请输入城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editArea.getText().length() == 0) {
                    Toast.makeText(mContext, "请输入小区,大厦或地区", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editDetailAddress.getText().length() == 0) {
                    Toast.makeText(mContext, "请输入详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                ShippingAddressModel model =  ShippingAddressSp.getShippingAddress(mContext);
                if(model==null){
                    model = new ShippingAddressModel();
                    model.list = new ArrayList<>();
                }

                ShippingAddressModel.ShippingAddressItemModel shippingAddressItemModel = model.new ShippingAddressItemModel();
                shippingAddressItemModel.name = textUsername.getText().toString();
                shippingAddressItemModel.phone = editPhone.getText().toString();
                shippingAddressItemModel.city = textSelectCity.getText().toString();
                shippingAddressItemModel.area = editArea.getText().toString();
                shippingAddressItemModel.address = editDetailAddress.getText().toString();
                shippingAddressItemModel.id = model.list.size()+1;
                model.list.add(shippingAddressItemModel);
                ShippingAddressSp.saveShippingAddress(mContext,model);

                Toast.makeText(mContext,"添加成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
