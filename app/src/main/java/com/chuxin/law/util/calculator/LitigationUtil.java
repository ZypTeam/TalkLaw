package com.chuxin.law.util.calculator;

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
                if (!isHalved) {
                    return (int) getPropertyCase(price) + "";
                } else {
                    return (int) getPropertyCase(price) / 2 + "";
                }
            case 2:
                return getDivorceCases(price,isHalved);
            case 3:
                return getPersonality(price,isHalved);
            case 4:
                return getIntellectualProperty(price, isHalved);
            case 5:
                return getLaborDispute(price, isHalved);
            case 6:
                return getJurisdictionNoSetUp(price, isHalved);
            case 7:
                return getAdministrative(price, isHalved);
            case 8:
                return getOtherAdministrative(price, isHalved);
            case 9:
                return getExcuteCost(price, isHalved);
            case 10:
                return getPayCost(price, isHalved);
            case 11:
                return getServiceByPublication(price, isHalved);
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


    /**
     * 知识产权
     * @param price
     * @param isHalved
     * @return
     */
    private String getIntellectualProperty(int price, boolean isHalved){
        return "";
    }

    /**
     * 劳动争议案件
     * @param price
     * @param isHalved
     * @return
     */
    private String getLaborDispute(int price, boolean isHalved){
        if(isHalved){
            return "5";
        }
        return "10";
    }
    /**
     * 管辖权异议不成立的案件
     * @param price
     * @param isHalved
     * @return
     */
    private String getJurisdictionNoSetUp(int price, boolean isHalved){
        if(isHalved){
            return "50";
        }
        return "100";
    }
    /**
     * 行政案件-商标、专利、海事行政案件
     * @param price
     * @param isHalved
     * @return
     */
    private String getAdministrative(int price, boolean isHalved){
        if(isHalved){
            return "50";
        }
        return "100";
    }
    /**
     * 行政案件 - 其他行政案件
     * @param price
     * @param isHalved
     * @return
     */
    private String getOtherAdministrative(int price, boolean isHalved){
        if(isHalved){
            return "25";
        }
        return "50";
    }
    /**
     * 执行费
     * @param price
     * @param isHalved
     * @return
     */
    private String getExcuteCost(int price, boolean isHalved){
        double p = 0;
        if(price <= 0){
            if(isHalved){
                return "25~250";
            }else {
                return "50~500";
            }
        }else if(price > 0 && price <= UNIT){
            if(isHalved){
                return "25";
            }else {
                return "50";
            }
        }else if(price > UNIT && price <= 50 * UNIT){
            p = price * 0.015 - 100;
        }else if(price > 50 * UNIT && price <= 500 * UNIT){
            p = price * 0.01 + 2400;
        }else if(price > 500 * UNIT && price <= 1000 * UNIT){
            p = price * 0.005 + 27400;
        }else if(price > 1000 * UNIT){
            p = price * 0.001 + 67400;
        }

        if(isHalved){
            return p / 2 + "";
        }
        return p + "";
    }

    /**
     * 支付令
     * @param price
     * @param isHalved
     * @return
     */
    private String getPayCost(int price, boolean isHalved){
        double p = getPropertyCase(price) * (1/3);

        if(isHalved){
            return p / 2 + "";
        }
        return p + "";
    }

    /**
     * 支付令
     * @param price
     * @param isHalved
     * @return
     */
    private String getServiceByPublication(int price, boolean isHalved){
        if(isHalved){
            return "50";
        }
        return "100";
    }
}
