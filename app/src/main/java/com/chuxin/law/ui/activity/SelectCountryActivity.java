package com.chuxin.law.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.chuxin.law.ui.adapter.BaseHospitalAdapter;
import com.chuxin.law.ui.widget.BackTitleView;

import java.util.HashMap;
import java.util.Map;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.model.AreaBean;

/**
 * Created by liangjie on 2017/4/24.
 * Description:TODO
 */

public class SelectCountryActivity extends BaseTalkLawActivity {
    private BaseHospitalAdapter adapter;
    private ListView lv_content;
    private AreaBean cityBean;
    private String provinceId;
    private String provinceName;
    private String type;
    private AreaBean countryBean;
    private Map<String, String> map = new HashMap<>();
    private BackTitleView rightTitleView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_common_simple_listview;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

        lv_content = (ListView) findViewById(R.id.lv_content);
        rightTitleView = (BackTitleView) findViewById(R.id.view_title);
        cityBean = (AreaBean) getIntent().getSerializableExtra("cityBean");
        provinceId = getIntent().getStringExtra("provinceId");
        provinceName = getIntent().getStringExtra("provinceName");
        type = getIntent().getStringExtra("type");

        adapter = new BaseHospitalAdapter(this, cityBean.getList());
        adapter.init();
        if (lv_content != null) {
            lv_content.setAdapter(adapter);
        }
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                adapter.init();
                countryBean = cityBean.getList().get(arg2);
                adapter.selectionMap.put(arg2, true);
                adapter.notifyDataSetChanged();

//                if (type.equals("per_hostpital")) {
//                    Intent intent = new Intent(SelectCountryActivity.this,
//                            SelectHospitalActivity.class);
//                    intent.putExtra("type", type);
//                    intent.putExtra("province_id", provinceId);
//                    intent.putExtra("city_id", cityBean.getId());
//                    intent.putExtra("county_id", countryBean.getId());
//                    startActivity(intent);
//                }
            }
        });

//        rightTitleView.setTitleText("选择乡县");

    }

    @Override
    public void initAction() {
        rightTitleView.setTitle("地区");
    }


    public void onSureClick() {
        if (countryBean == null) {
            Toast.makeText(mContext,"请选择县",Toast.LENGTH_SHORT).show();
            return;
        }
//        if (type.equals("per_hostpital")) {
//            Intent intent = new Intent(SelectCountryActivity.this,
//                    SelectHospitalActivity.class);
//            intent.putExtra("type", type);
//            intent.putExtra("province_id", provinceId);
//            intent.putExtra("city_id", cityBean.getId());
//            intent.putExtra("county_id", countryBean.getId());
//            startActivity(intent);
//        } else {
//            if (NetworkUtil.isNetWorkConnected()) {
//                Map<String, String> paramMap = new HashMap<>();
//                paramMap.put("province", provinceId);
//                paramMap.put("city", cityBean.getId());
//                paramMap.put("county", countryBean.getId());
//                UpdatePersonInfo.update(paramMap, 200, uiHandler);
//            } else {
//                shortToast("网络连接失败");
//                // finish();
//            }
//        }
    }

//    @Override
//    protected void handleMsg(Message msg) {
//        super.handleMsg(msg);
//        map = (HashMap<String, String>) msg.obj;
//        if (map.get("code").equals("10000")) {
//            YMUserService.getPerInfo().getInfo().setCounty(countryBean.getName());
//            YMUserService.getPerInfo().setCounty(countryBean.getId());
//            YMUserService.getPerInfo().setCity(countryBean.getId());
//            YMUserService.getPerInfo().setProvince(provinceId);
//            YMUserService.getPerInfo().getInfo().setCity(cityBean.getName());
//            YMUserService.getPerInfo().getInfo().setProvince(provinceName);
//            ToastUtils.shortToast(map.get("msg"));
//            finish();
//        }
//    }
}
