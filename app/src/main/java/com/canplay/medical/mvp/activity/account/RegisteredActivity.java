package com.canplay.medical.mvp.activity.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.view.ClearEditText;
import com.canplay.medical.view.NavigationBar;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class RegisteredActivity extends BaseActivity {


    @BindView(R.id.navigationbar)
    NavigationBar navigationbar;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.et_code)
    ClearEditText etCode;
    @BindView(R.id.tv_psw)
    TextView tvPsw;
    @BindView(R.id.et_pws)
    ClearEditText etPws;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private Subscription mSubscription;

    private LinearLayoutManager mLinearLayoutManager;
    private int type;
    private boolean is_time;
    private TimeCount timeCount;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        navigationbar.setNavigationBarListener(this);
        type = getIntent().getIntExtra("type", 0);

        if(type!=0){
            navigationbar.setNaviTitle("忘记密码");
            tvPsw.setText("新密码");
        }
        //计时器
        timeCount = new TimeCount(60000, 1000);
    }

    @Override
    public void bindEvents() {


    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    //计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetcode.setEnabled(false);
            tvGetcode.setText(millisUntilFinished / 1000 + getString(R.string.schongxinhuoqu));
        }

        @Override
        public void onFinish() {
            is_time = false;
            tvGetcode.setText(R.string.chongxinhuoqu);
            tvGetcode.setEnabled(true);
        }
    }
}
