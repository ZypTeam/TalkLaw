package com.chuxin.law.ry.my.mymessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.ry.message.TestMessage;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * @author zhaoyapeng
 * @version create time:18/3/909:59
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
@ProviderTag(messageContent = PayMessage.class, showReadState = true)
public class PayMessageProvider  extends IContainerItemProvider.MessageProvider<PayMessage> {
    @Override
    public void bindView(View view, int i, PayMessage payMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.layout_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
            holder.iconImg.setImageResource(R.drawable.img_baozhengjin_send);
        } else {
            holder.layout_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
            holder.iconImg.setImageResource(R.drawable.img_chat_left_baozheng);
        }

        holder.message.setText(payMessage.getContent()); // 设置消息内容
    }

    @Override
    public Spannable getContentSummary(PayMessage payMessage) {
        return new SpannableString("支付");
    }

    @Override
    public void onItemClick(View view, int i, PayMessage payMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_mesg_pay, null);

        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.text_content);
        holder.iconImg = (ImageView)view.findViewById(R.id.img_icon);
        holder.layout_bg = (RelativeLayout) view.findViewById(R.id.layout_bg);
        view.setTag(holder);
        return view;
    }

    private static class ViewHolder {
        TextView message;
        RelativeLayout layout_bg;
        ImageView iconImg;
    }
}
