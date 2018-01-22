package cn.com.talklaw.ui.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.jusfoun.baselibrary.Util.PhoneUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2115:13
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TextDrawable extends Drawable {
    private final String text;
    private final Paint paint;
    private Context mContext;
    int tagWidth = 0; // 标签宽度
    int tagHieght = 0;// 标签高度
    int tagTextWidth = 0;// 标签文字长度
     int LR_PADDING=0;//标签内部 左右边距
     int TB_PADDING=0;//标签内部 上下边距
    int textviewRealHeight = 0; // textview 一行文本真实高度
    int TEXT_TAG_INTERVAL = 10;// 结尾处，文本 和 标签之间的间距
    RectF rectF = new RectF();
    public TextDrawable(Context mContext,String text, int height, int textSize) {
        this.mContext=mContext;
        this.text = text;
        this.paint = new Paint();
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        LR_PADDING = PhoneUtil.dip2px(mContext,5);
        TB_PADDING = PhoneUtil.dip2px(mContext,3);
        tagTextWidth =  (int) paint.measureText(text);
        tagHieght =  textSize+TB_PADDING*2;
        tagWidth = tagTextWidth+LR_PADDING*2;
        textviewRealHeight  = height;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        // 设置标签的范围
        rectF.set(TEXT_TAG_INTERVAL,  -(textviewRealHeight-(textviewRealHeight-tagHieght)/2), tagWidth, -(textviewRealHeight-tagHieght)/2);
        // 绘制 背景   rx：x方向上的圆角半径  ry：y方向上的圆角半径
        canvas.drawRoundRect(rectF, PhoneUtil.dip2px(mContext,5), PhoneUtil.dip2px(mContext,5), paint);
        // 文本的 baseline的位置
        int baseline = (int) (rectF.top + (rectF.bottom - rectF.top - paint.getFontMetrics().bottom + paint.getFontMetrics().top) / 2 - paint.getFontMetrics().top)-2;
        paint.setColor(Color.WHITE);
        // drawText y为 baseline的位置
        canvas.drawText(text, rectF.centerX(),  baseline, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
