package cn.com.talklaw.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jusfoun.baselibrary.Util.PhoneUtil;
import com.jusfoun.baselibrary.task.WeakHandler;
import com.jusfoun.baselibrary.view.HomeViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;

/**
 * @author wangcc
 * @date 2017/12/23
 * @describe
 */

public class LoopScrollView extends FrameLayout {

    protected View rootView;
    protected LinearLayout bottomLayout;
    private ViewPager.OnPageChangeListener listener;
    private Context context;
    private int imageRadius;
    private int imageDis;
    private int currentIndex = 0;

    private HomeViewPager viewPager;
    /**
     * list size 1张图片不滚动
     */
    private int sizeCount;
    private boolean isScroll;

    private ImageView defaultTopImage;

    /**
     * 延迟时间 默认1秒
     */
    private int delayTime = 1000;

    private WeakHandler handler = new WeakHandler();

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (sizeCount > 1) {
                currentIndex = currentIndex % (sizeCount + 1) + 1;
                if (currentIndex == 1) {
                    viewPager.setCurrentItem(currentIndex, false);
                    handler.postDelayed(task, delayTime);
                } else if (currentIndex == sizeCount + 1) {
                    viewPager.setCurrentItem(currentIndex);
                    //viewpager滚动1页为200毫秒，viewpager滚动后切换到第一页
                    handler.postDelayed(task, 200);
                } else {
                    viewPager.setCurrentItem(currentIndex);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    public LoopScrollView(@NonNull Context context) {
        this(context, null);
    }

    public LoopScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAction();
    }

    private void initView(Context context) {
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_loop_scroll, this);
        viewPager =findViewById(R.id.viewpager);
        bottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        defaultTopImage = findViewById(R.id.default_image);

    }

    private void initAction() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (listener != null) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

                int index;
                if (position == 0) {
                    position = 1;
                }

                if (position > sizeCount) {
                    position = sizeCount;
                }
                index = position - 1;

                if (bottomLayout.getVisibility() != View.GONE) {
                    for (int i = 0; i < bottomLayout.getChildCount(); i++) {
                        View child=bottomLayout.getChildAt(i);
                        if (i == index) {
                            child.setBackgroundResource(R.mipmap.icon_dot_light);
                        } else {
                            child.setBackgroundResource(R.mipmap.icon_dot_drag);
                        }
                    }
                }

                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentIndex = viewPager.getCurrentItem();

                switch (state) {
                    case 0:
                        if (currentIndex == 0) {
                            viewPager.setCurrentItem(sizeCount, false);
                        } else if (currentIndex == sizeCount + 1) {
                            viewPager.setCurrentItem(1, false);
                        }
                        break;
                    case 1:
                        if (currentIndex == sizeCount + 1) {
                            viewPager.setCurrentItem(1, false);
                        } else if (currentIndex == 0) {
                            viewPager.setCurrentItem(sizeCount, false);
                        }
                        break;
                    default:
                        break;
                }

                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //监听ViewPager的触摸事件，当用户按下的时候取消注册，当用户手抬起的时候再注册
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stop();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 让viewpager循环滚动 所有在list首尾添加两条数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public <T> List<T> getList(List<T> list) {
        List<T> resultList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            sizeCount = 0;
            defaultTopImage.setVisibility(VISIBLE);
            return resultList;
        }
        defaultTopImage.setVisibility(GONE);
        refreshBottomView(list.size());
        sizeCount = list.size();
        if (sizeCount > 1) {
            viewPager.setNotTouchScoll(false);
        } else {
            viewPager.setNotTouchScoll(true);
        }

        resultList.add(0, list.get(list.size() - 1));
        resultList.addAll(list);
        resultList.add(list.get(0));
        return resultList;
    }

    private void refreshBottomView(int size){
        bottomLayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view=new View(context);
            if (i==0){
                view.setBackgroundResource(R.mipmap.icon_dot_light);
            }else {
                view.setBackgroundResource(R.mipmap.icon_dot_drag);
            }

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(PhoneUtil.dip2px(context,9),PhoneUtil.dip2px(context,9));
            if (i!=0){
                params.leftMargin=PhoneUtil.dip2px(context,5);
            }
            bottomLayout.addView(view,params);
        }
    }

    public LoopScrollView setAdapter(PagerAdapter adapter) {
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }
        return this;
    }

    public LoopScrollView setCurrentItem(int position, boolean smoothScroll) {
        if (position > sizeCount + 1) {
            return this;
        }

        viewPager.setCurrentItem(position, smoothScroll);
        return this;

    }

    public LoopScrollView setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public int getSize() {
        return sizeCount;
    }

    public LoopScrollView setOffscreenPageLimit(int limit) {
        viewPager.setOffscreenPageLimit(limit);
        return this;
    }

    public void onResume() {
        if (!isScroll) {
            start();
        }
    }

    public void onPause() {
        if (isScroll) {
            stop();
        }
    }

    /**
     * 开始滚动
     */
    public void start() {
        isScroll = true;
        handler.postDelayed(task, delayTime);
    }

    /**
     * 停止滚动
     */
    public void stop() {
        isScroll = false;
        handler.removeCallbacks(task);
    }

    public boolean isScroll() {
        return isScroll;
    }

    public LoopScrollView setDotVisibility(int visibility) {
        bottomLayout.setVisibility(visibility);
        return this;
    }

    public void setListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

}
