package com.chuxin.law.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chuxin.law.R;
import com.chuxin.law.model.BackCardModel;
import com.chuxin.law.ui.adapter.SelectBankCardAdapter;
import com.jusfoun.baselibrary.Util.PhoneUtil;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/2/720:43
 * @Email zyp@jusfoun.com
 * @Description ${选择银行卡dialog}
 */
public class SelectBankCardDialog extends Dialog {

    protected RecyclerView recyclerView;
    private Context mContext;
    private SelectBankCardAdapter adapter;
    private List<BackCardModel.BackCardItemModel> list;
    private ImageView closeImg;


    public SelectBankCardDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelectBankCardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.wheel_dialog_back_card);
        Window window = this.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_enter_anim_up_down);
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext));
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        closeImg = (ImageView)findViewById(R.id.img_close);
        adapter = new SelectBankCardAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setCallBack(new SelectBankCardAdapter.CallBack() {
            @Override
            public void click(BackCardModel.BackCardItemModel model) {
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).isSelect = false;
                    }
                }
                model.isSelect = true;
                adapter.refreshList(list);

                if (callBack != null)
                    callBack.click(model);
                dismiss();
            }
        });

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    public void setData(List<BackCardModel.BackCardItemModel> data) {
        list = data;
        adapter.refreshList(data);
    }

    public interface CallBack {
        void click(BackCardModel.BackCardItemModel model);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
