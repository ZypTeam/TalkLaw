package com.chuxin.law.ry.my.mymessage;

import android.os.Parcel;

import com.chuxin.law.ry.message.TestMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * @author zhaoyapeng
 * @version create time:18/3/910:05
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
@MessageTag(value = "app:pay", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class PayMessage extends MessageContent {
    private String content;



    public PayMessage(){

    }
    @Override
    public byte[] encode() {

        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.putOpt("message",content);
        } catch (JSONException e) {
//            Log.e(TAG, "JSONException " + e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public static PayMessage obtain(String text) {
        PayMessage model = new PayMessage();
        model.setContent(text);
        return model;
    }


    //给消息赋值。
    public PayMessage(Parcel in) {
        content= ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<PayMessage> CREATOR = new Creator<PayMessage>() {

        @Override
        public PayMessage createFromParcel(Parcel source) {
            return new PayMessage(source);
        }

        @Override
        public PayMessage[] newArray(int size) {
            return new PayMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }



}
