package cn.com.talklaw.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawFragment;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2115:49
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class HomeFragment extends BaseTalkLawFragment {

    private RecyclerView recyclerView;

    public static HomeFragment getInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void initAction() {

    }
}
