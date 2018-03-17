package com.canplay.medical.mvp.activity.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.RegularListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用药计划
 */
public class UsePlanActivity extends BaseActivity {


    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_have)
    TextView tvHave;
    @BindView(R.id.ll_box)
    LinearLayout llBox;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.lv_info)
    RegularListView lvInfo;
    @BindView(R.id.tv_frequency)
    TextView tvFrequency;
    @BindView(R.id.ll_again)
    LinearLayout llAgain;
    @BindView(R.id.tv_ring)
    TextView tvRing;
    @BindView(R.id.ll_ring)
    LinearLayout llRing;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_use_plan);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);


    }


    @Override
    public void bindEvents() {


        navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void navigationLeft() {
            }

            @Override
            public void navigationRight() {

            }

            @Override
            public void navigationimg() {

            }
        });


    }


    @Override
    public void initData() {

    }



}
