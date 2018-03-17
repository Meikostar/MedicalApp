package com.canplay.medical.mvp.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.fragment.MeasureRemindFragment;
import com.canplay.medical.fragment.RemindMedicatFragment;
import com.canplay.medical.mvp.adapter.FragmentViewPagerAdapter;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 健康提醒
 */
public class RemindHealthActivity extends BaseActivity {

    @BindView(R.id.line)
    View line;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    @BindView(R.id.tv_use)
    TextView tvUse;
    @BindView(R.id.tv_merure)
    TextView tvMerure;
    private FragmentViewPagerAdapter mainViewPagerAdapter;
    private List<Fragment> mFragments;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_remind_health);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);


    }


    @Override
    public void bindEvents() {

        tvUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvUse.setBackground(getResources().getDrawable(R.drawable.choose_hui_rectangle));
                tvUse.setTextColor(getResources().getColor(R.color.white));
                tvMerure.setTextColor(getResources().getColor(R.color.slow_black));
                tvMerure.setBackground(null);
                viewpagerMain.setCurrentItem(0);
            }
        });

        tvMerure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpagerMain.setCurrentItem(1);
                tvUse.setBackground(null);
                tvUse.setTextColor(getResources().getColor(R.color.slow_black));
                tvMerure.setTextColor(getResources().getColor(R.color.white));
                tvMerure.setBackground(getResources().getDrawable(R.drawable.choose_huis_rectangle));
            }
        });
        navigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void navigationLeft() {
            }

            @Override
            public void navigationRight() {
                startActivity(new Intent(RemindHealthActivity.this, RemindSettingActivity.class));
            }

            @Override
            public void navigationimg() {
                startActivity(new Intent(RemindHealthActivity.this, RemindSettingActivity.class));
            }
        });


    }


    @Override
    public void initData() {
        addFragment();
        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(2);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(0);
        viewpagerMain.setScanScroll(false);
    }

    private RemindMedicatFragment remindMedicatFragment;
    private MeasureRemindFragment measureRemindFragment;

    private void addFragment() {
        mFragments = new ArrayList<>();
        remindMedicatFragment = new RemindMedicatFragment();
        measureRemindFragment = new MeasureRemindFragment();
        mFragments.add(remindMedicatFragment);
        mFragments.add(measureRemindFragment);

    }


}
