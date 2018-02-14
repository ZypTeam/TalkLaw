package com.chuxin.law.common;

import com.chuxin.law.model.LawyerProductModel;

/**
 * @author wangcc
 * @date 2018/2/12
 * @describe
 */

public class CommonLogic {

    private LawyerProductModel.LawyerProductData lawyerProductData;

    private static class SingleHolder{
        private static CommonLogic instance=new CommonLogic();
    }

    private CommonLogic(){

    }

    public static CommonLogic getInstance(){
        return SingleHolder.instance;
    }

    public LawyerProductModel.LawyerProductData getLawyerProductData() {
        return lawyerProductData;
    }

    public void setLawyerProductData(LawyerProductModel.LawyerProductData lawyerProductData) {
        this.lawyerProductData = lawyerProductData;
    }
}
