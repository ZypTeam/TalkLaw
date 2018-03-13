package com.chuxin.law.ry.my.mymessage;

import android.os.Parcel;
import android.util.Log;

import com.chuxin.law.ry.message.TestMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * @author zhaoyapeng
 * @version create time:18/3/910:05
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
@MessageTag(
        value = "APP:MyPay",
        flag = 3
)
public class PayMessage extends MessageContent {
    private String content;
    private String userId;
    private String state; // 0.未支付 1.已支付

    public static String TYPE_PAY_YES = "1";
    public static String TYPE_PAY_NO = "0";


    public PayMessage(){

    }
    public PayMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");

            if (jsonObj.has("userId"))
                userId = jsonObj.optString("userId");

            if (jsonObj.has("state"))
                state = jsonObj.optString("state");

        } catch (JSONException e) {
        }

    }
    @Override
    public byte[] encode() {

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", content);
            jsonObj.put("userId", userId);
            jsonObj.put("state", state);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static PayMessage obtain(String text,String userId,String state) {
        PayMessage model = new PayMessage();
        model.setContent(text);
        model.setUserId(userId);
        model.setState(state);
        return model;
    }


    //给消息赋值。
    public PayMessage(Parcel in) {
//        setContent(ParcelUtils.readFromParcel(in));
        this.content = ParcelUtils.readFromParcel(in);
        this.state = ParcelUtils.readFromParcel(in);
        this.userId = ParcelUtils.readFromParcel(in);
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


    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);
        ParcelUtils.writeToParcel(dest, state);
        ParcelUtils.writeToParcel(dest, userId);
    }


    @Override
    public List<String> getSearchableWord() {
        List<String> words = new ArrayList<>();
        words.add(content);
        words.add(state);
        words.add(userId);
        return words;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
