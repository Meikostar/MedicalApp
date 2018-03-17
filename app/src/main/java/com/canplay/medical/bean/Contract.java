package com.canplay.medical.bean;

/**
 * Created by leo on 2016/9/23.
 */
public class Contract {
    public static final int PAGE_SIZE = 10;//每页显示数量
    public static final int DAY_PAGE_SIZE = 7;//每页显示数量
    public static final int PAGE_CONSULTA_SIZE = 100;//聊天每页显示数量
    public static final int CACHE_SIZE = 100;//缓存数据条目上限
    public static final String EXTERNALSTORAGESTATELOST = "找不到该设备SD卡";
    public static final String SOFT_WARE_TYPE = "";
    public static final String TEMP = "temp";
    public static final String APP_LOG = "AppLog";

    public static final int REQUEST_CODE_TAKE_PHOTO = 800;//从相机拍照
    public static final int REQUEST_CODE_CHOOSE_IMAGE = 801;//从相册获取照片
    public static final int REQUEST_CODE_CROP_IMAGE = 802;//裁剪
    public static final int REQUEST_DEL_IMAGE = 803;//删除照片后回传


    public static final String IS_FIRST= "IS_FIRST";//是否是第一次进入APP

    public static final int CITY_LIST = 100;    //省市列表

}
