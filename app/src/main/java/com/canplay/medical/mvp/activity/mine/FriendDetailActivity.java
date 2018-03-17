package com.canplay.medical.mvp.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.view.EditorNameDialog;
import com.canplay.medical.view.PhotoPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 亲友详情
 */
public class FriendDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_blood_press)
    LinearLayout llBloodPress;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    @BindView(R.id.ll_Medical_plan)
    LinearLayout llMedicalPlan;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);

    }

    @Override
    public void bindEvents() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llBloodPress.setOnClickListener(this);
        llBloodSugar.setOnClickListener(this);
        llMedicalPlan.setOnClickListener(this);
        tvBind.setOnClickListener(this);


    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_blood_press://血压记录
             startActivity(new Intent(FriendDetailActivity.this,BloodPressRecordActivity.class));
                break;
            case R.id.ll_blood_sugar://血糖记录

                break;
            case R.id.ll_Medical_plan://用药计划

                break;
            case R.id.tv_bind://解除绑定

                break;


        }
    }


}
