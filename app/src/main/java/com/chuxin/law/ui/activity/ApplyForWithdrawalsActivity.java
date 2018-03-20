package com.chuxin.law.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.TalkLawApplication;
import com.chuxin.law.base.BaseTalkLawActivity;
import com.chuxin.law.common.ApiService;
import com.chuxin.law.model.BackCardModel;
import com.chuxin.law.model.UserModel;
import com.chuxin.law.ui.view.PresentInstructionsDialog;
import com.chuxin.law.ui.view.SelectBankCardDialog;
import com.chuxin.law.ui.widget.BackTitleView;
import com.jusfoun.baselibrary.Util.TouchUtil;
import com.jusfoun.baselibrary.net.Api;

import java.util.HashMap;

import rx.functions.Action1;

import static com.chuxin.law.common.CommonConstant.NET_SUC_CODE;

/**
 * @author zhaoyapeng
 * @version create time:18/2/718:26
 * @Email zyp@jusfoun.com
 * @Description ${申请提现页面}
 */
public class ApplyForWithdrawalsActivity extends BaseTalkLawActivity {
    protected BackTitleView backTitleView;
    protected ImageView imgTitle;
    protected TextView textNum;
    protected EditText editMoney;
    protected TextView textYue;
    protected TextView textYueNum;
    protected TextView textTixianAll;
    protected TextView textTixian;
    protected LinearLayout layoutAdd;
    protected LinearLayout layoutCard;
    protected TextView textName;
    private BackCardModel backCardModel;
    private SelectBankCardDialog selectBankCardDialog;
    private ImageView img_wenhao;

    private PresentInstructionsDialog dialog;

    private BackCardModel.BackCardItemModel backCardItemModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_apply_for_withdrawals;
    }

    @Override
    public void initDatas() {
        selectBankCardDialog = new SelectBankCardDialog(mContext, R.style.my_dialog);
        dialog = new PresentInstructionsDialog(mContext, R.style.my_dialog);
    }

    @Override
    public void initView() {
        backTitleView = (BackTitleView) findViewById(R.id.back_title_view);
        imgTitle = (ImageView) findViewById(R.id.img_title);
        textNum = (TextView) findViewById(R.id.text_num);
        editMoney = (EditText) findViewById(R.id.edit_money);
        textYue = (TextView) findViewById(R.id.text_yue);
        textYueNum = (TextView) findViewById(R.id.text_yue_num);
        textTixianAll = (TextView) findViewById(R.id.text_tixian_all);
        textTixian = (TextView) findViewById(R.id.text_tixian);
        layoutAdd = (LinearLayout) findViewById(R.id.layout_add);
        layoutCard = (LinearLayout) findViewById(R.id.layout_card);
        textName = (TextView) findViewById(R.id.text_name);
        img_wenhao =(ImageView)findViewById(R.id.img_wenhao);

    }

    @Override
    public void initAction() {
        backTitleView.setTitle("申请提现");
        backTitleView.setRightText("账户明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, AccountDetailsActivity.class);
            }
        });
        layoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(null, NewAddYinHangActivity.class);
            }
        });

        layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backCardModel != null) {
                    selectBankCardDialog.setData(backCardModel.data);
                    selectBankCardDialog.show();
                }
            }
        });

        selectBankCardDialog.setCallBack(new SelectBankCardDialog.CallBack() {
            @Override
            public void click(BackCardModel.BackCardItemModel model) {
                backCardItemModel = model;
                if (model.card.length() > 4) {
                    textNum.setText(model.card.substring(model.card.length() - 4, model.card.length() ));
                }else{
                    textNum.setText(model.card);
                }

            }
        });

        textTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tixian();
            }
        });
        final UserModel model = TalkLawApplication.getUserInfo();
        if (model != null) {
            textYueNum.setText(model.getMoney());
        }

        textTixianAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model != null) {
                    editMoney.setText(model.getMoney());
                }
            }
        });

        img_wenhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        TouchUtil.createTouchDelegate(img_wenhao,40);

        dialog.setContent("（1）如何提现\n" +
                "在iOS或者Android，在“我的-账户信息”里，申请提现即可。\n" +
                "\n" +
                "（2）提现多久可以到账\n" +
                "提现是否到账，可以联系平台客服询问，一般3个“工作日”会到账，具体的以银行的到账信息为准。\n" +
                "\n" +
                "（3）提现需要填写哪些信息\n" +
                "a、你提现的银行卡号。\n" +
                "\n" +
                "b、该账号对应的真实姓名。\n" +
                "\n" +
                "（4）遇到一切问题，联系平台客服，最终解释权归宁夏初心科技有限公司所有。");

    }

    private void delMsg() {
        showLoadDialog();
        addNetwork(Api.getInstance().getService(ApiService.class).getCards()
                , new Action1<BackCardModel>() {
                    @Override
                    public void call(BackCardModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {
                            backCardModel = model;
                            if (model.data != null && model.data.size() > 0) {
                                backCardItemModel = model.data.get(0);

                                if (backCardItemModel.card.length() > 4) {
                                    textNum.setText(backCardItemModel.card.substring(backCardItemModel.card.length() - 4, backCardItemModel.card.length() ));
                                }else{
                                    textNum.setText(backCardItemModel.card);
                                }

                                layoutCard.setVisibility(View.VISIBLE);
                            }


                        } else {
                            if (model != null)
                                showToast(model.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }


    private void tixian() {
        showLoadDialog();

        if (backCardItemModel == null) {
            showToast("请选择银行卡");
            return;
        }

        if (editMoney.getText().length() == 0) {
            showToast("请输入提现金额");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("cardid", backCardItemModel.card);
        map.put("money", editMoney.getText().toString());
        addNetwork(Api.getInstance().getService(ApiService.class).submitTixian(map)
                , new Action1<BackCardModel>() {
                    @Override
                    public void call(BackCardModel model) {
                        hideLoadDialog();
                        if (model != null && model.getCode() == NET_SUC_CODE) {

                            showToast("提现成功");
                            finish();
                        } else {
                            if (model != null)
                                showToast(model.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadDialog();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        delMsg();
    }
}
