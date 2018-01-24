package cn.com.talklaw.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author wangcc
 * @date 2018/1/24
 * @describe 打赏支付
 */

public class GratuityPayActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    protected ImageView iconGratuity;
    protected TextView price;
    protected TextView wechat;
    protected TextView zhifubao;
    protected TextView help;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gratuity_pay;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.title_bar);
        iconGratuity = (ImageView) findViewById(R.id.icon_gratuity);
        price = (TextView) findViewById(R.id.price);
        wechat = (TextView) findViewById(R.id.wechat);
        zhifubao = (TextView) findViewById(R.id.zhifubao);
        help = (TextView) findViewById(R.id.help);

    }

    @Override
    public void initAction() {
        titleBar.setTitle("打赏");
    }
}
