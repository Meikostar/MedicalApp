package com.canplay.medical.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.canplay.medical.R;
import com.orhanobut.logger.Logger;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

public class ProgressDialog extends AlertDialog{

    private int StringId;
    private CircularProgressBar mCircularProgressBar;
    private TextView tv_context;

    public ProgressDialog(Context context){
        super(context, R.style.progressDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public ProgressDialog(Context context, int StringId){
        super(context, R.style.progressDialog);
        this.StringId = StringId;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener){
        super(context, cancelable, cancelListener);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.pb_loading);
        tv_context = (TextView) findViewById(R.id.tv_context);
        if(StringId != 0){
            tv_context.setText(StringId);
        }
    }

    @Override
    public void show(){
        super.show();
        if(!mCircularProgressBar.isShown())
            ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).start();
    }

    @Override
    public void dismiss(){
        super.dismiss();
        Logger.i("progress dismiss");
        if(mCircularProgressBar.isShown())
            ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).progressiveStop();
    }

    public void showProgressBar(){
        if(!isShowing())
            show();
    }

    public void stopProgressBar(){
        if(isShowing())
            dismiss();
    }
}
