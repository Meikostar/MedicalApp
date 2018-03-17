package com.canplay.medical.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by peter on 2016/9/12.
 */
public abstract class BaseFragment extends Fragment{

    private static BaseFragment fragment = null;

    public static <T extends BaseFragment> BaseFragment newInstance(Bundle paramBundle, Class<T> paramClass) throws InstantiationException, IllegalAccessException, java.lang.InstantiationException{
        fragment = paramClass.newInstance();
        fragment.setArguments(paramBundle);
        return fragment;
    }

    public void release(){
        if(fragment != null){
            fragment = null;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public static BaseFragment getBaseFragment(){
        if(fragment != null){
            return fragment;
        }
        return null;
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
    }

    private String oldMsg;
    protected Toast toast = null;
    private long oneTime = 0;
    private long twoTime = 0;

    public void showToast(String s){
        if(toast == null){
            toast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        }else{
            twoTime = System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public void showToast(int resId){
        showToast(getActivity().getString(resId));
    }

    @Override
    public void onDestroy(){
        if(toast != null){
            toast.cancel();
            toast = null;
        }
        super.onDestroy();
    }
}
