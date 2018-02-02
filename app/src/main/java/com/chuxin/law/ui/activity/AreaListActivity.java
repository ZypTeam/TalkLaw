package com.chuxin.law.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.common.CommonConstant;
import com.chuxin.law.model.AreaBean;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.adapter.BaseAreaCityAdapter;
import com.chuxin.law.ui.adapter.BaseAreaNameAdapter;
import com.chuxin.law.ui.widget.BackTitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.baselibrary.base.NoDataModel;
import com.jusfoun.baselibrary.net.Api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1910:27
 * @Email zyp@jusfoun.com
 * @Description ${选择地区 activity}
 */
public class AreaListActivity extends BaseTalkLawActivity {
    private String area_province = null;
    private List<AreaBean> list_areacityname = new ArrayList<>();
    private List<AreaBean> list_city;
    private ListView lv_first_level;
    private ListView lv_second_level;

    private BaseAreaNameAdapter area_city_adpter;
    private BaseAreaCityAdapter area_province_adpter;

    private AreaBean selectProvince;
    private AreaBean selectCity;
    private Map<String, String> map;

    private String type;
    private BackTitleView backTitleView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_common_two_level_select;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        if (area_province == null) {
            area_province = getRowJson(R.raw.area);
            list_areacityname = new Gson().fromJson(area_province, new TypeToken<List<AreaBean>>() {
            }.getType());
            list_city = list_areacityname.get(0).getList();
        }

        lv_first_level = (ListView) findViewById(R.id.lv_first_level);
        lv_second_level = (ListView) findViewById(R.id.lv_second_level);
        backTitleView = (BackTitleView) findViewById(R.id.view_title_bar);

        backTitleView.setRightText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectCity == null) {
                    Toast.makeText(mContext, "请选择城市", Toast.LENGTH_SHORT).show();
                } else if (selectProvince == null) {
                    Toast.makeText(mContext, "请选择省份", Toast.LENGTH_SHORT).show();
                } else {
//                    intent.putExtra("provinceId", selectProvince.getId());
//                    intent.putExtra("provinceName", selectProvince.getName());
//                    intent.putExtra("cityBean", selectCity);
//                    startActivity(intent);
                    submitInfo();
                }


            }
        });

        area_city_adpter = new BaseAreaNameAdapter(AreaListActivity.this);
        area_city_adpter.setData(list_city);
        area_province_adpter = new BaseAreaCityAdapter(AreaListActivity.this,
                list_areacityname);
        area_province_adpter.init();
        lv_first_level.setAdapter(area_province_adpter);
        lv_second_level.setAdapter(area_city_adpter);
        lv_first_level.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                area_province_adpter.init();
                area_province_adpter.selectionMap.put(arg2, true);
                selectProvince = list_areacityname.get(arg2);
                area_city_adpter.setData(selectProvince.getList());
                area_city_adpter.notifyDataSetChanged();
                area_province_adpter.notifyDataSetChanged();
            }
        });

        lv_second_level.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                area_city_adpter.init();
                area_city_adpter.selectionMap.put(arg2, true);
                area_city_adpter.notifyDataSetChanged();
//                city_id = area_city_adpter.getSelectId(arg2);
                selectCity = area_city_adpter.getSelect(arg2);
//                // TODO: 2017/4/24 选择县
//                Intent intent = new Intent(AreaListActivity.this,
//                        SelectCountryActivity.class);
//                intent.putExtra("type", type);
//
////                    province_id = city_id();
//
////                    city_id = getList2("id");
//
//                if (selectCity == null) {
//                    Toast.makeText(mContext,"请选择城市",Toast.LENGTH_SHORT).show();
//                } else if (selectProvince == null) {
//                    Toast.makeText(mContext,"请选择省份",Toast.LENGTH_SHORT).show();
//                } else {
//                    intent.putExtra("provinceId", selectProvince.getId());
//                    intent.putExtra("provinceName", selectProvince.getName());
//                    intent.putExtra("cityBean", selectCity);
////                    startActivity(intent);
//                }
                // }
            }
        });
    }


    @Override
    public void initAction() {
        backTitleView.setTitle("地区");
    }


//    public String getList1() {
//        String str = "";
//        for (int i = 0; i < list_province.size(); i++) {
//            if (area_province_adpter.selectionMap.get(i)) {
//                str = list_province.get(i);
//                break;
//            }
//        }
//        return str;
//    }

//    public String city_id() {
//        String str = "";
//        for (int i = 0; i < list_province.size(); i++) {
//            if (area_province_adpter.selectionMap.get(i)) {
//                str = i + 1 + "";
//                break;
//            }
//        }
//        return str;
//    }

//    public String getList2(String id) {
//        String str = "";
//        for (int i = 0; i < list_areacityname.size(); i++) {
//            if (area_city_adpter.selectionMap.get(i)) {
//                str = list_areacityname.get(i).get(id);
//                break;
//            }
//        }
//        return str;
//    }

    /**
     * 获取 文件里面的json
     *
     * @param id
     * @return
     */
    public String getRowJson(int id) {
        String json = null;
        InputStream is = this.getResources().openRawResource(id);
        byte[] buffer;
        try {
            buffer = new byte[is.available()];
            is.read(buffer);
            json = new String(buffer, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public void onSureClick() {

//        province = getList1();
//        province_id = city_id();

//        city = getList2("name");
//        city_id = getList2("id");

        if (type.equals("serch_area")) {
//            RecruitCenterMainActivity.serchinfo.setArea_1(selectProvince.getName());
//            RecruitCenterMainActivity.serchinfo.setArea_2(selectCity.getName());
//            RecruitCenterMainActivity.serchinfo.setArea_1_id(selectProvince.getId());
//            RecruitCenterMainActivity.serchinfo.setArea_2_id(selectCity.getId());
            finish();
        } else {
            if (selectCity == null) {
                Toast.makeText(mContext, "请选择城市", Toast.LENGTH_SHORT).show();
            } else if (selectProvince == null) {
                Toast.makeText(mContext, "请选择省份", Toast.LENGTH_SHORT).show();
            } else {
//                if (NetworkUtil.isNetWorkConnected()) {
//                    Map<String, String> paramMap = new HashMap<>();
//                    paramMap.put("province", selectProvince.getId());
//                    paramMap.put("city", selectCity.getId());
//                    UpdatePersonInfo.update(paramMap, 100, uiHandler);
//                } else {
//                    shortToast("网络连接失败");
//                    // finish();
//                }
            }
        }
    }


//    @Override
//    protected void handleMsg(Message msg) {
//        super.handleMsg(msg);
//        map = (HashMap<String, String>) msg.obj;
//        switch (msg.what) {
//            case 100:
//                if (map.get("code").equals("10000")) {
//                    YMUserService.getPerInfo().setCity(selectCity.getId());
//                    YMUserService.getPerInfo().setProvince(selectProvince.getId());
//                    YMUserService.getPerInfo().getInfo().setCity(selectCity.getName());
//                    YMUserService.getPerInfo().getInfo().setProvince(selectProvince.getName());
//                    finish();
//                }
//                shortToast(map.get("msg"));
//                break;
//
//            default:
//                break;
//        }
//    }

    private void submitInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("province", selectProvince.getName());
        map.put("city", selectCity.getName());
        showLoadDialog();
        Log.e("tag", "map===" + map);
        addNetwork(Api.getInstance().getService(ApiService.class).changeUserInfo(map), new Action1<NoDataModel>() {
            @Override
            public void call(NoDataModel noDataModel) {
                hideLoadDialog();
                if (noDataModel.getCode() == CommonConstant.NET_SUC_CODE) {
                    UserModel userModel = TalkLawApplication.getUserInfo();
                    userModel.setCity(selectCity.getName());
                    userModel.setProvince(selectProvince.getName());
                    showToast("修改地址陈宫");

                    TalkLawApplication.saveUserInfo(userModel);

                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToast(noDataModel.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                hideLoadDialog();
                showToast(throwable.getMessage());
            }
        });
    }
}
