package cn.com.talklaw.ui.fragment;

import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${说法fragment}
 */
public class StatementFragment extends BaseTalkLawFragment {


    public static StatementFragment getInstance(){
        StatementFragment fragment = new StatementFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_statement
                ;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initAction() {

    }
}
