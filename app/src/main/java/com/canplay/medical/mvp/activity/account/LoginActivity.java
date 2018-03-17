package com.canplay.medical.mvp.activity.account;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.base.BaseApplication;
import com.canplay.medical.mvp.activity.MainActivity;
import com.canplay.medical.mvp.component.DaggerBaseComponent;
import com.canplay.medical.mvp.present.LoginContract;
import com.canplay.medical.mvp.present.LoginPresenter;
import com.canplay.medical.permission.PermissionConst;
import com.canplay.medical.permission.PermissionGen;
import com.canplay.medical.permission.PermissionSuccess;
import com.canplay.medical.util.SpUtil;
import com.canplay.medical.util.StringUtil;
import com.canplay.medical.util.TextUtil;
import com.canplay.medical.view.ClearEditText;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.widget.ImageLoaderEngine;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;
    @BindView(R.id.tv_logo)
    ImageView tvLogo;
    @BindView(R.id.et_user)
    ClearEditText etUser;
    @BindView(R.id.et_pws)
    ClearEditText etPws;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DaggerBaseComponent.builder().appComponent(((BaseApplication) getApplication()).getAppComponent()).build().inject(this);
        presenter.attachView(this);
//        String userId = SpUtil.getInstance().getUserId();
//        if (TextUtil.isNotEmpty(userId)) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
    }
   private int REQUEST_CODE_SCAN =6;
    @Override
    public void bindEvents() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPws.getText().toString();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                startActivity(intent);


//                if (TextUtil.isEmpty(user)) {
//                    showToasts(getString(R.string.qingshurusjh));
//                    return;
//                }
//                if (TextUtil.isEmpty(password)) {
//                    showToasts(getString(R.string.mimanull));
//                    return;
//                }
//                showProgress("登录中...");
//                presenter.goLogin(user, StringUtil.md5(password));
            }
        });


        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//忘记密码
                Intent intent=new Intent(LoginActivity.this,RegisteredActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        tvRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//注册
                Intent intent=new Intent(LoginActivity.this,RegisteredActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });
    }
    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
         /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */

        //ZxingConfig config = new ZxingConfig();
        //config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        //config.setPlayBeep(true);//是否播放提示音
        //config.setShake(true);//是否震动
        //config.setShowAlbum(true);//是否显示相册
        //config.setShowFlashLight(true);//是否显示闪光灯
        //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);

    }
    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                showToasts("扫描结果为：" +content);
//                result.setText("扫描结果为：" + content);
            }
        }
    }
    @Override
    public <T> void toEntity(T entity) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        dimessProgress();
    }

    @Override
    public void showTomast(String msg) {
        showToasts(msg);
        dimessProgress();
    }


}
