package cn.com.talklaw.ui.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.com.talklaw.ui.fragment.MyConsultFragment;

/**
 * @author wangcc
 * @date 2018/1/18
 * @describe
 */

public class MyConsultPagerUtils {

    public static final int COUNT = 2;
    public static final String TYPE = "type";
    public static final int TYPE_ING = 0;
    public static final int TYPE_HIS = 1;

    public static Fragment getFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt(TYPE, TYPE_ING);
                break;
            case 1:
                bundle.putInt(TYPE, TYPE_HIS);
                break;
            default:
                bundle.putInt(TYPE, TYPE_ING);
                break;
        }
        fragment = MyConsultFragment.getInstance(bundle);
        return fragment;
    }
}
