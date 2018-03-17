package com.canplay.medical.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public abstract class BasePopView extends MPopupWindow implements View.OnClickListener{
    	protected Activity activity;
	public View popView;
	public View line;
	public MPopupWindow pop;
	protected LayoutInflater infalter;

	public BasePopView(Activity activity) {
		this.activity = activity;
		infalter = LayoutInflater.from(activity);
		popView = initPopView(infalter);
	}
   public int type;//1代表全透
	protected abstract View initPopView(LayoutInflater infalter);

	// 显示选择图片的popview
	public void showPopView() {
		pop = new MPopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		ColorDrawable dw;
		if(type!=0){
			 dw = new ColorDrawable(0x00000000);
		}else {
			dw = new ColorDrawable(0xb0000000);
		}

		// 设置SelectPicPopupWindow弹出窗体的背景
		pop.setBackgroundDrawable(dw);

		pop.showAsDropDown(line);
	}
	public void dismiss() {
		if (pop != null && pop.isShowing()) {
			pop.dismiss();
		}
	}
}