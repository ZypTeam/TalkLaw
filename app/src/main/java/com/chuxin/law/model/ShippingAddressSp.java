package com.chuxin.law.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chuxin.law.sharedpreferences.ShippingAddressModel;
import com.google.gson.Gson;

/**
 * Created by hzc on 16/7/27.
 * 保存收货地址
 */
public class ShippingAddressSp {
    private final static String NAME = "ShippingAddress";
    private final static String MODE = "address";

    public static void saveShippingAddress(Context context, ShippingAddressModel model) {
        SharedPreferences crashPreferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = crashPreferences.edit();
        editor.putString(MODE, new Gson().toJson(model));
        editor.apply();
    }

    public static ShippingAddressModel getShippingAddress(Context context) {
        SharedPreferences crashPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String address = crashPreferences.getString(MODE, "");
        if (!TextUtils.isEmpty(address)) {
            return new Gson().fromJson(address, ShippingAddressModel.class);
        }
        return null;
    }


    public static ShippingAddressModel.ShippingAddressItemModel getSelectShippingAddress(Context context) {
        SharedPreferences crashPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String address = crashPreferences.getString(MODE, "");
        if (!TextUtils.isEmpty(address)) {
            ShippingAddressModel model = new Gson().fromJson(address, ShippingAddressModel.class);
            if (model != null && model.list != null) {
                for (int i = 0; i < model.list.size(); i++) {
                    if (model.list.get(i).isSelect) {
                        return model.list.get(i);
                    }
                }
            }

        }
        return null;
    }


}
