package com.chuxin.law.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.model.HotKeyListModel;
import com.chuxin.law.model.ProductModel;
import com.chuxin.law.model.ProductsModel;
import com.chuxin.law.ui.adapter.ProductListAdapter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SearchGuideView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description :搜索页面，搜索历史和热门搜索显示view
 */
public class SearchGuideView extends LinearLayout {
    protected View rootView;
    protected ImageView imgDelete;
    protected RelativeLayout layoutHis;
    private Context mContext;



    private FlowLayout mHistory, mHot;
    private TextView mDeleteHistory;
    private RelativeLayout mHistoryLayout, mHotLayout;


    private String mCurrentType;

    private TextView hisTitleText, hotTitleText;

    public SearchGuideView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchGuideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;

    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_search_guide, this, true);
        mHistory = (FlowLayout) findViewById(R.id.search_history_content);
        mHot = (FlowLayout) findViewById(R.id.hot_search_content);
        mHistoryLayout = (RelativeLayout) findViewById(R.id.search_history_layout);
        mHotLayout = (RelativeLayout) findViewById(R.id.search_hot_layout);
        hisTitleText = (TextView) findViewById(R.id.history_title);
        hotTitleText = (TextView) findViewById(R.id.hot_search_title);
        imgDelete = (ImageView) findViewById(R.id.img_delete);
        layoutHis = (RelativeLayout) findViewById(R.id.layout_his);
    }

    private void initWidgetAction() {
        imgDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mHistoryLayout.setVisibility(GONE);
                mHistory.removeAllViews();
                if (onDelListener!=null){
                    onDelListener.onClick(view);
                }
            }
        });


        TextPaint hisPaint = hisTitleText.getPaint();
        hisPaint.setFakeBoldText(true);

        TextPaint hotPaint = hotTitleText.getPaint();
        hotPaint.setFakeBoldText(true);

    }

    private OnClickListener onDelListener;

    public void setOnDelListener(OnClickListener onDelListener) {
        this.onDelListener = onDelListener;
    }

    /**
     * 设置 历史搜索显示
     *
     * @param set 本地数据库中的历史搜索记录
     */
    public void setHistoryData(HashSet<String> set) {
        if (set==null||set.size()==0){
            mHistoryLayout.setVisibility(GONE);
            mHistory.removeAllViews();
            return;
        }
        mHistoryLayout.setVisibility(VISIBLE);
        mHistory.removeAllViews();

        for(Iterator<String> iter = set.iterator(); iter.hasNext(); ) {
            String string=  iter.next();
            View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_option_item, null);
            final TextView optionName = (TextView) itemview.findViewById(R.id.item_option_name);
            optionName.setText(string);
            optionName.setBackgroundResource(R.drawable.option_unselected);
            optionName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack!=null){
                        callBack.search(optionName.getText().toString());
                    }
                }
            });
            mHistory.addView(itemview);
        }
    }


    /**
     * 设置 热门搜索 显示
     *
     * @param  list 服务器返回的热门搜索结果列表
     */
    public void setHotSearchData(List<HotKeyListModel.HotKeyModel> list) {
        if (list==null){
            return;
        }
        mHotLayout.setVisibility(VISIBLE);
        mHot.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_option_item, null);
            final TextView optionName = (TextView) itemview.findViewById(R.id.item_option_name);
            optionName.setText(list.get(i).getKeyword());
            optionName.setBackgroundResource(R.drawable.option_unselected);
            optionName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack!=null){
                        callBack.search(optionName.getText().toString());
                    }
                }
            });
            mHot.addView(itemview);
        }
    }

    public void refreshData(ProductsModel models){

    }

    public void setSearchType(String type) {
        mCurrentType = type;
    }

    public interface CallBack {
        void search(String key);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
