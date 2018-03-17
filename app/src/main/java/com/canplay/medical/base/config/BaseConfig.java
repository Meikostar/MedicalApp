package com.canplay.medical.base.config;

import android.os.Environment;

import com.canplay.medical.bean.Contract;

public class BaseConfig{

    /*
     * 错误日志输出位置
     */
    public static final String ERROR_LOG_PATH = "";

    /*
     * 设备类型 1、android 2、ios
     */
    public static final int CLIENT_TYPE = 1;

    /*
     * 下载APK目录
     */
    public static final String DOWNLOAD_APK_DIR = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/download/";

    public static final String APK_NAME = "";

    /*
   * 下载文件目录
   */
    public static final String DOWNLOAD_FILE = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/file/";

    /*
  * 缓存图片目录
  */
    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/image/";


    /*
  * 保存图片目录
  */
    public static final String PHOTO_DIR = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/photo/";

    /*
 * TEMP
 */
    public static final String TEMP_DIR = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/temp/";

    /*
* 日志
*/
    public static final String APP_LOG = Environment.getExternalStorageDirectory().getPath() + "/" + Contract.SOFT_WARE_TYPE + "/log/";

    /*轮训日志文件名称*/
    public static final String LOG = "log.txt";

    /*
     * 动画持续时间
     */
    public static final int DURATION = 1500;


    public static final int SNACKBAR_COLOR = 0xff000000;

    public static final float SNACKBAR_ALPHA = 0.8f;

    public static final String SNACKBAR_TEXT_COLOR = "#FFFFFF";
    //图片大小
    public static final long BITMAP_MAX_SIZE = 1024 * 1024;

    //是否为测试
    public static boolean ISTEST = true;
}
