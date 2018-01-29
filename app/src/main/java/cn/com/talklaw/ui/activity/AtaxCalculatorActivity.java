package cn.com.talklaw.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.jusfoun.baselibrary.Util.AppUtil;

import cn.com.talklaw.R;
import cn.com.talklaw.base.BaseTalkLawActivity;
import cn.com.talklaw.model.CaseTypeModel;
import cn.com.talklaw.ui.adapter.CaseTypeAdapter;
import cn.com.talklaw.ui.view.CaseTypeDialog;
import cn.com.talklaw.ui.widget.BackTitleView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1718:20
 * @Email zyp@jusfoun.com
 * @Description ${个税计算器}
 */
public class AtaxCalculatorActivity extends BaseTalkLawActivity {
    protected BackTitleView titleBar;
    private int mTotalIncome = 0;
    private RelativeLayout rl_income_type;


    private CaseTypeDialog caseTypeDialog;

    private String mIncomeType = "";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_atax_calculator;
    }

    @Override
    public void initDatas() {
        String typeJson = AppUtil.getRowJson(mContext, R.raw.income_type);
        caseTypeDialog = new CaseTypeDialog(mContext,typeJson);
    }

    @Override
    public void initView() {
        titleBar = (BackTitleView) findViewById(R.id.back_title_view);
        rl_income_type = findViewById(R.id.rl_income_type);
    }

    @Override
    public void initAction() {
        titleBar.setTitle("个税计算器");

        caseTypeDialog.setTitleText("选择收入类型");

        rl_income_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caseTypeDialog.show();
            }
        });

        caseTypeDialog.setCallBack(new CaseTypeAdapter.CallBack() {
            @Override
            public void getCaseType(CaseTypeModel.CaseTypeItemModel model) {
                mIncomeType = model.title;
            }
        });
    }
}
