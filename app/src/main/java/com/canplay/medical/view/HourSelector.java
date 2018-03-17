package com.canplay.medical.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.canplay.medical.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/***
 * 功能描述:
 * 作者:chenwei
 * 时间:2016/12/20
 * 版本:
 ***/
public class HourSelector extends LinearLayout {
    private Context mContext;
    private boolean isChecked=false;
    private ImageView check;
    private boolean canNoClick;
    private CompoundButton.OnCheckedChangeListener mListener;
    private OnClickListener mClickListener;
    private  int hour;
    private  int minter;
    private  int hourColor;

    public HourSelector(Context context) {
        super(context);
    }

    public HourSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        TypedArray mTypedArray = null;
        if (attrs!=null){
            mTypedArray = getResources().obtainAttributes(attrs,
                    R.styleable.HourSelector);
            hour = mTypedArray.getInteger(R.styleable.HourSelector_hour,12);
            hourColor = mTypedArray.getResourceId(R.styleable.HourSelector_hourColor,0);
            minter=mTypedArray.getInteger(R.styleable.HourSelector_minter,0);

        }
        init();
    }
    private CycleWheelView mCycleWheelViewHour;
    private CycleWheelView mCycleWheelViewMinute;
    public void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
       View mView = inflater.inflate( R.layout.hour_selector, this);
        mCycleWheelViewHour = (CycleWheelView)mView.findViewById(R.id.cwv_hour);
        mCycleWheelViewMinute = (CycleWheelView)mView.findViewById(R.id.cwv_minute);
        setHour();
        setMinute();
    }
    private String months;
    /**
     * 小时
     */
    private void setHour(){
        List<String> list =new ArrayList<>();
        for(int i=0;i<24;i++){
            NumberFormat formatter = new DecimalFormat("00");
            String xx = formatter.format(i);
            list.add(i,xx);
        }
        mCycleWheelViewHour.setLabels(list,null);
        try {
            mCycleWheelViewHour.setWheelSize(5);
        } catch (CycleWheelView.CycleWheelViewException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");
        if(hour==12){
            String date = sDateFormat.format(new Date());
            hour=Integer.valueOf(date);
        }
        if(hour==00){
            hour=23;
        }
        mCycleWheelViewHour.setLabelSelectSize(16f);

        mCycleWheelViewHour.setLabelSize(14f);
        mCycleWheelViewHour.setAlphaGradual(0.7f);
        mCycleWheelViewHour.setCycleEnable(false);
        mCycleWheelViewHour.selection(hour+1);
        mCycleWheelViewHour.setLabelColor(Color.parseColor("#b3b3b3"));
        mCycleWheelViewHour.setDivider(Color.parseColor("#e3e3e3"), 1);
        mCycleWheelViewHour.setLabelSelectColor(Color.parseColor("#3399ff"));
        mCycleWheelViewHour.setSolid(Color.WHITE, Color.WHITE);
    }


    public String getSelector(){
        String hour=mCycleWheelViewHour.getSelectLabel();
        String minute=mCycleWheelViewMinute.getSelectLabel();
        return (hour+":"+minute);
    }
    /**
     * 分钟
     */
    private void setMinute(){
        List<String> list =new ArrayList<>();
        for(int i=0;i<60;i++){
            NumberFormat formatter = new DecimalFormat("00");
            String xx = formatter.format(i);
            list.add(i,xx);
        }
        mCycleWheelViewMinute.setLabels(list,null);
        try {
            mCycleWheelViewMinute.setWheelSize(5);
        } catch (CycleWheelView.CycleWheelViewException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("mm");

        if(minter==0){
            String date = sDateFormat.format(new Date());
            minter=Integer.valueOf(date);
        }


        mCycleWheelViewMinute.setLabelSelectSize(16f);
        mCycleWheelViewMinute.setLabelSize(14f);
        mCycleWheelViewMinute.setAlphaGradual(0.7f);
        mCycleWheelViewMinute.setCycleEnable(false);
        mCycleWheelViewMinute.setSelection(minter);
        mCycleWheelViewMinute.setLabelColor(Color.parseColor("#b3b3b3"));
        mCycleWheelViewMinute.setDivider(Color.parseColor("#e3e3e3"), 1);
        mCycleWheelViewMinute.setLabelSelectColor(Color.parseColor("#3399ff"));
        mCycleWheelViewMinute.setSolid(Color.WHITE, Color.WHITE);
    }




}
