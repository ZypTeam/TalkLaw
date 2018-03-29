package com.chuxin.law.ry.my.mymessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.event.CheckOrderEvent;
import com.chuxin.law.model.CheckConsultModel;
import com.jusfoun.baselibrary.base.RxManage;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import io.rong.eventbus.EventBus;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/3/909:59
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
@ProviderTag(messageContent = PayMessage.class, showReadState = true)
public class PayMessageProvider extends IContainerItemProvider.MessageProvider<PayMessage> {
    @Override
    public void bindView(View view, int i, PayMessage payMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.layout_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
            holder.iconImg.setImageResource(R.drawable.img_baozhengjin_send);
            holder.message.setTextColor(0xffffffff);
            holder.desText.setTextColor(0xffffc5c8);

            if("0".equals(payMessage.getType())) {
                holder.desText.setText("已发送保证金");
            }else{
                holder.desText.setText("已支付保证金");
            }
        } else {
            holder.layout_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
            holder.iconImg.setImageResource(R.drawable.img_chat_left_baozheng);
            holder.message.setTextColor(0xff000000);
            holder.desText.setTextColor(0xff999999);

            if("0".equals(payMessage.getType())) {
                holder.desText.setText("点击支付保证金");
            }else{
                holder.desText.setText("已支付保证金");
            }
//            if(PayMessage.TYPE_PAY_NO.equals(payMessage.getState())){
//                holder.desText.setText("点击支付保证金");
//            }else{
//                holder.desText.setText("已支付");
//            }
        }
        holder.message.setText("￥" + payMessage.getMoney()); // 设置消息内容


    }

    @Override
    public Spannable getContentSummary(PayMessage payMessage) {
        return new SpannableString("支付");
    }

    @Override
    public void onItemClick(final View view, int i, PayMessage payMessage, UIMessage uiMessage) {
        if (!TextUtils.isEmpty(TalkLawApplication.getUserId()) && !TextUtils.isEmpty(payMessage.getUserId())) {

        }

        if (uiMessage.getMessageDirection() != Message.MessageDirection.SEND&&"0".equals(payMessage.getType())) {
            CheckOrderEvent checkOrderEvent = new CheckOrderEvent();
            checkOrderEvent.order = payMessage.getOrder();
            checkOrderEvent.price = payMessage.getMoney();
            checkOrderEvent.userid = payMessage.getUserId();
            EventBus.getDefault().post(checkOrderEvent);
        }
//        CheckOrderEvent checkOrderEvent = new CheckOrderEvent();
//        checkOrderEvent.order = payMessage.getOrder();
//        EventBus.getDefault().post(checkOrderEvent);
        Log.e("tag","payMessage="+payMessage.getOrder());
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_mesg_pay, null);

        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.text_content);
        holder.iconImg = (ImageView) view.findViewById(R.id.img_icon);
        holder.layout_bg = (RelativeLayout) view.findViewById(R.id.layout_bg);
        holder.desText = (TextView) view.findViewById(R.id.text_des);
        view.setTag(holder);
        return view;
    }

    private static class ViewHolder {
        TextView message, desText;
        RelativeLayout layout_bg;
        ImageView iconImg;
    }
}
