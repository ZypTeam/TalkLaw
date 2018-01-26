package cn.com.talklaw.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.talklaw.R;
import cn.com.talklaw.model.CaseTypeModel;
import cn.com.talklaw.ui.viewholder.CaseTypeTitleViewHolder;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2216:17
 * @Email zyp@jusfoun.com
 * @Description ${案件类型adapter}
 */
public class CaseTypeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CaseTypeModel.CaseTypeItemModel> list;

    public CaseTypeAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        list = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new CaseTypeTitleViewHolder(
                        mInflater.inflate(R.layout.item_case_type_title, parent, false),
                        mContext,callBack);
            case 1:
                return new CaseTypeTitleViewHolder(
                        mInflater.inflate(R.layout.item_case_type_content, parent, false),
                        mContext,callBack);
        }
        return new CaseTypeTitleViewHolder(
                mInflater.inflate(R.layout.item_case_type_content, parent, false),
                mContext,callBack);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CaseTypeTitleViewHolder) holder).update(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    public void refresh(List<CaseTypeModel.CaseTypeItemModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    private CallBack callBack;

    public interface CallBack {
        void getCaseTyoe(CaseTypeModel.CaseTypeItemModel model);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
