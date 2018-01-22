package cn.com.talklaw.ui.activity;

import android.view.View;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1910:27
 * @Email zyp@jusfoun.com
 * @Description ${选择地区 activity}
 */
public class SelectAreaActivity extends BaseTalkLawActivity {
    protected BackTitleView viewTitleBar;
    protected TextView textSelectCity;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_select_area;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        viewTitleBar = (BackTitleView) findViewById(R.id.view_title_bar);
        textSelectCity = (TextView) findViewById(R.id.text_select_city);

    }

    @Override
    public void initAction() {
        viewTitleBar.setTitle("添加地址");

        textSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null,AreaListActivity.class);
            }
        });
    }
}
