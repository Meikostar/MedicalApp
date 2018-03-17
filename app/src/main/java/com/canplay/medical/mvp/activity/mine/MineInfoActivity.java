package com.canplay.medical.mvp.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canplay.medical.R;
import com.canplay.medical.base.BaseActivity;
import com.canplay.medical.mvp.activity.account.LoginActivity;
import com.canplay.medical.permission.PermissionConst;
import com.canplay.medical.permission.PermissionGen;
import com.canplay.medical.permission.PermissionSuccess;
import com.canplay.medical.util.SpUtil;
import com.canplay.medical.util.TextUtil;
import com.canplay.medical.view.EditorNameDialog;
import com.canplay.medical.view.NavigationBar;
import com.canplay.medical.view.PhotoPopupWindow;
import com.canplay.medical.view.TimeSelectorDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.ImageSelectActivity;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.widget.ImageLoaderEngine;

/**
 * 个人信息
 */
public class MineInfoActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.ll_birth)
    LinearLayout llBirth;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
   private EditorNameDialog dialog;
   private PhotoPopupWindow mWindowAddPhoto;
    private int sex=0;
    private String name="";
    @Override
    public void initViews() {
        setContentView(R.layout.activity_mine_info);
        ButterKnife.bind(this);
        navigationBar.setNavigationBarListener(this);
        dialog=new EditorNameDialog(this,line);

        mWindowAddPhoto = new PhotoPopupWindow(this);
    }

    @Override
    public void bindEvents() {
        ivPhone.setOnClickListener(this);
        llName.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llBirth.setOnClickListener(this);
        llArea.setOnClickListener(this);
        llBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSelectorDialog selectorDialog = new TimeSelectorDialog(MineInfoActivity.this);
                selectorDialog.setDate(new Date(System.currentTimeMillis()))
                        .setBindClickListener(new TimeSelectorDialog.BindClickListener() {
                            @Override
                            public void time(String time) {
                                tvBirth.setText(time);
                            }
                        });
                selectorDialog.show(findViewById(R.id.ll_area));
            }
        });
        dialog.setBindClickListener(new EditorNameDialog.BindClickListener() {
            @Override
            public void teaMoney(String names) {

                if (TextUtil.isNotEmpty(names)) {
                     name=names;
                    tvName.setText(names);

                }
                dialog.dismiss();

            }
        });
        llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowAddPhoto.showAsDropDown(line);
            }
        });
        mWindowAddPhoto.setSureListener(new PhotoPopupWindow.ClickListener() {
            @Override
            public void clickListener(int type) {
              if(type==0){//男
                  tvSex.setText("男");
              }else {//女
                  tvSex.setText("女");

              }
            }
        });

    }


    @Override
    public void initData() {

    }
    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        Picker.from(this)
                .count(1)
                .enableCamera(true)
                .setEngine(new ImageLoaderEngine())
                .setAdd_watermark(false)
                .forResult(4);
    }
    public String path;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //上传照片
                case 4:
                    List<String> imgs = data.getStringArrayListExtra(ImageSelectActivity.EXTRA_RESULT_SELECTION);
                    path = imgs.get(0);
                    Glide.with(this).load(path).asBitmap().into(ivPhone);
//                    presenter.getToken(path);
                    break;

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_phone://
                                PermissionGen.with(MineInfoActivity.this)
                        .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                        .permissions(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request();
                break;
            case R.id.ll_name://
                dialog.show();
                break;
            case R.id.ll_sex://

                break;
            case R.id.ll_birth://
                break;
            case R.id.ll_area://
                break;

        }
    }
}
