package cn.com.talklaw.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:43
 * @Email zyp@jusfoun.com
 * @Description ${轮播图viewpager}
 */
public class CarouselViewHolder extends BaseViewHolder {

    protected ImageView imgAvatar;
    protected TextView textName;
    protected TextView textTime;
    protected TextView textCount;
    protected TextView titleText;

    public CarouselViewHolder(View itemView, Context mContext) {
        super(itemView, mContext);
        imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        textName = (TextView) itemView.findViewById(R.id.text_name);
        textTime = (TextView) itemView.findViewById(R.id.text_time);
        textCount = (TextView) itemView.findViewById(R.id.text_count);
        titleText = (TextView)itemView.findViewById(R.id.text_title);
    }

    @Override
    public void update(Serializable model) {
        Glide.with(mContext).load("http://img5.imgtn.bdimg.com/it/u=2137958015,4291978384&fm=27&gp=0.jpg").into(imgAvatar);
        textName.setText("赵律师");
        textTime.setText("2017-11-12");
        textCount.setText("1000");
        titleText.setText("编译失败；看到编译器错误");
    }

    private void initView(View rootView) {

    }
}
