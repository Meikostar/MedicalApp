package com.canplay.medical.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.canplay.medical.R;
import com.canplay.medical.util.CanplayUtils;

/**
 * Created by leo on 2016/9/23.
 */
public class SelectImageDialog extends Dialog implements View.OnClickListener {

    private TextView select_image_take;
    private TextView select_image_select;
    private TextView select_image_cancle;

    private Context context;

    private DialogClickListener dialogClickListener;

    private LayoutInflater inflater;

    public void setDialogClicListen(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    public SelectImageDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initViw();
    }


    public SelectImageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initViw();
    }

    public SelectImageDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initViw();
    }

    private void initViw() {
        View view = inflater.inflate(R.layout.select_image_dialog, null);
        select_image_take = (TextView) view.findViewById(R.id.select_image_take);
        select_image_select = (TextView) view.findViewById(R.id.select_image_select);
        select_image_cancle = (TextView) view.findViewById(R.id.select_image_cancle);
        setContentView(view);
        select_image_take.setOnClickListener(this);
        select_image_select.setOnClickListener(this);
        select_image_cancle.setOnClickListener(this);
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.AnimBottom); //设置窗口弹出动画
        WindowManager.LayoutParams wl = window.getAttributes(); //根据x，y坐标设置窗口需要显示的位置
        wl.gravity = Gravity.BOTTOM;
        wl.width = CanplayUtils.SCREEN_WIDTH;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl); //设置触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image_take:
                dialogClickListener.buttonFirstClick();
                break;
            case R.id.select_image_select:
                dialogClickListener.buttonTwoClick();
                break;
            case R.id.select_image_cancle:
                dialogClickListener.cancleClick();
                break;
        }
    }

    public interface DialogClickListener {
        void buttonFirstClick();

        void buttonTwoClick();

        void cancleClick();
    }
}
