package cn.com.talklaw.ui.util.calculator;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2616:46
 * @Email zyp@jusfoun.com
 * @Description ${诉讼费计算器}
 */
public class LitigationUtil {

    private int UNIT = 10000;

    public int getCost(int id, int price) {
        switch (id) {
            case 1:
                return (int)getPropertyCase(price);
        }
        return 0;
    }

    private double getPropertyCase(int price) {
        if (price <= 10000) {
            return 50;
        } else if (price <= 10 * UNIT) {
            return price * 0.025 - 200 ;
        } else if (price <= 20 * UNIT) {
            return price * 0.02 + 300 ;
        } else if (price <= 50 * UNIT) {
            return price * 0.015 + 1300 ;
        } else if (price <= 100 * UNIT) {
            return price * 0.01 + 380 ;
        } else if (price <= 200 * UNIT) {
            return price * 0.009 + 4800 ;
        } else if (price <= 500 * UNIT) {
            return price * 0.008 + 6800 ;
        } else if (price <= 1000 * UNIT) {
            return price * 0.007 + 11800 ;
        } else {
            return price * 0.006 + 21800 ;
        }
    }
}
