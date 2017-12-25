package cn.com.talklaw.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2509:26
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public abstract class BaseView  extends RelativeLayout{
    protected Context mContext;
    public BaseView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    protected abstract  void initData();

    protected  abstract void initViews();

    protected abstract  void initActions();
}
