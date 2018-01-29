package cn.com.talklaw.ui.util.calculator;

import android.util.Log;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2616:46
 * @Email zyp@jusfoun.com
 * @Description ${诉讼费计算器}
 */
public class LitigationUtil {

    private int UNIT = 10000;

    public String getCost(int id, int price, boolean isHalved) {
        switch (id) {
            case 1:
                if (isHalved) {
                    return (int) getPropertyCase(price) + "";
                } else {
                    return (int) getPropertyCase(price) / 2 + "";
                }
            case 2:
                return getDivorceCases(price,isHalved);
            case 3:
                return getPersonality(price,isHalved);
        }
        return "";
    }


    /**
     * 财产案件
     */
    private double getPropertyCase(int price) {
        if (price <= 10000) {
            return 50;
        } else if (price <= 10 * UNIT) {
            return price * 0.025 - 200;
        } else if (price <= 20 * UNIT) {
            return price * 0.02 + 300;
        } else if (price <= 50 * UNIT) {
            return price * 0.015 + 1300;
        } else if (price <= 100 * UNIT) {
            return price * 0.01 + 380;
        } else if (price <= 200 * UNIT) {
            return price * 0.009 + 4800;
        } else if (price <= 500 * UNIT) {
            return price * 0.008 + 6800;
        } else if (price <= 1000 * UNIT) {
            return price * 0.007 + 11800;
        } else {
            return price * 0.006 + 21800;
        }
    }

    /**
     *  离婚
     * */
    private String getDivorceCases(int price,boolean isHalved) {
        if (price <= 20 * UNIT) {
            if(isHalved){
                return "25~150";
            }else{
                return "50~300";
            }
        } else {
            if(isHalved) {
                return ((int) (price * 0.005 - 700))/2 + "";
            }else {
                return ((int) (price * 0.005 - 700)) + "";
            }
        }
    }

    /**
     *  人格权
     * */
    private String  getPersonality(int price,boolean isHalved){
        double p ;
        if(price<=5*UNIT){
            if(isHalved){
                return "50~250";
            }else{
                return "100~500";
            }
        }else if(price<=10*UNIT){
           p= (price-5*UNIT)*0.01;
           if(isHalved){
               return (p+ 500)/2+"";
           }else{
               return (p+ 500)+"";
           }
        }else {
            p= (price-10*UNIT)*0.005;

            if(isHalved){
                return (p+ 500)/2+"";
            }else{
                return (p+ 500)+"";
            }
        }
    }


}
