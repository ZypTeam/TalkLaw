package cn.com.talklaw.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chuxin.law.model.IntegralModel;

import java.util.List;

import cn.com.talklaw.ui.fragment.GoodsListFragment;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2914:41
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class GoodsViewPagerAdapter extends FragmentPagerAdapter {
    private List<IntegralModel.CatItemModel> list;
    public GoodsViewPagerAdapter(FragmentManager fm,List<IntegralModel.CatItemModel> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int i) {
        return GoodsListFragment.getInstance(list.get(i));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).name;
    }

}
