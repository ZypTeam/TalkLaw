package cn.com.talklaw.ui.activity;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.jusfoun.baselibrary.Util.PhoneUtil;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.ui.util.TextDrawable;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2115:12
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TextDemoActivity extends BaseTalkLawActivity {

    TextView textView;
    TextView imgText;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_text_demo;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        imgText = (TextView) findViewById(R.id.text_demo1);
        textView = (TextView) findViewById(R.id.text_demo2);
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int height = (int) Math.ceil(textView.getPaint().getFontMetrics().descent - textView.getPaint().getFontMetrics().top);
                Drawable drawable = new TextDrawable(mContext,source,height, PhoneUtil.dip2px(mContext,10));
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        textView.setText(Html.fromHtml("TextView 文本 尾部添加 标签TextView 文本 尾部添加 标签<img src=\"标签\">", imageGetter, null));
//
//


//        Html.ImageGetter imageGetter = new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                int resId = Integer.parseInt(source);
//                Drawable drawable = mContext.getResources().getDrawable(resId);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                        drawable.getIntrinsicHeight());
//                return drawable;
//            }
//        };
//        imgText.setText(Html.fromHtml("TextView中插入图片插入图片插入图片插入图片<img src=\""+R.mipmap.ic_launcher+"\">",
//                imageGetter, null));
    }

    @Override
    public void initAction() {

    }


}
