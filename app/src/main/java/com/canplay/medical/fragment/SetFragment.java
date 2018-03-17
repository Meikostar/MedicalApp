package com.canplay.medical.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseFragment;
import com.canplay.medical.mvp.activity.account.LoginActivity;
import com.canplay.medical.mvp.activity.mine.CollectionActivity;
import com.canplay.medical.mvp.activity.mine.MineCodeActivity;
import com.canplay.medical.mvp.activity.mine.MineEuipmentActivity;
import com.canplay.medical.mvp.activity.mine.MineHealthCenterActivity;
import com.canplay.medical.mvp.activity.mine.MineInfoActivity;
import com.canplay.medical.mvp.activity.mine.RemindHealthActivity;
import com.canplay.medical.mvp.activity.mine.SettingActivity;
import com.canplay.medical.util.SpUtil;
import com.canplay.medical.view.EditorNameDialog;
import com.canplay.medical.view.PhotoPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mykar on 17/4/10.
 */
public class SetFragment extends BaseFragment implements View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.ll_reminder)
    LinearLayout llReminder;
    @BindView(R.id.ll_health)
    LinearLayout llHealth;
    @BindView(R.id.ll_equipment)
    LinearLayout llEquipment;
    @BindView(R.id.ll_collection)
    LinearLayout llCollection;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.iv_code)
    ImageView ivCode;


    private PhotoPopupWindow mWindowAddPhoto;
    private EditorNameDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, null);
        unbinder = ButterKnife.bind(this, view);


        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initListener() {
        llCenter.setOnClickListener(this);
        llReminder.setOnClickListener(this);
        llHealth.setOnClickListener(this);
        ivCode.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
        llCollection.setOnClickListener(this);
        llSetting.setOnClickListener(this);




    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_center://编辑中心
                startActivity(new Intent(getActivity(), MineInfoActivity.class));

                break;
            case R.id.ll_reminder://用药提醒
                startActivity(new Intent(getActivity(), RemindHealthActivity.class));
                break;
            case R.id.ll_health://健康中心

                startActivity(new Intent(getActivity(), MineHealthCenterActivity.class));
                break;
            case R.id.ll_equipment://我的设备
                startActivity(new Intent(getActivity(), MineEuipmentActivity.class));
                break;
            case R.id.ll_collection://我的收藏

                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
            case R.id.ll_setting://
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.iv_code://我的二维码
                startActivity(new Intent(getActivity(), MineCodeActivity.class));
                break;
        }
    }


}
