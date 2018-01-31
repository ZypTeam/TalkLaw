package witmob.com.videolibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by wangcc on 2017/9/25.
 * describe
 */

public class CustomProgressBar extends ImageView {

    private RotateAnimation mRotateAnimation;
    private Paint paint = null;
    private Drawable defaultDrawable = null;
    private int width = 0;
    private int height = 0;

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        this.setFocusable(true);

        mRotateAnimation = new RotateAnimation(0, 360 * 1000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        mRotateAnimation.setDuration(1400 * 1000000);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setFillAfter(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        defaultDrawable = this.getBackground();
        if (defaultDrawable == null)
            defaultDrawable = getResources().getDrawable(R.mipmap.loading1);
        if (width == 0)
            width = this.getWidth();
        if (height == 0)
            height = this.getHeight();
        defaultDrawable.setBounds(0, 0, width, height);
        defaultDrawable.draw(canvas);
    }

    public void show() {

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility==VISIBLE){
            if (mRotateAnimation!=null)
                startAnimation(mRotateAnimation);
        }else {
            clearAnimation();
        }
    }

    public void stop() {
        clearAnimation();
    }

    public void hide() {
        clearAnimation();
        this.setVisibility(View.GONE);
    }
}
