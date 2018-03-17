package com.canplay.medical.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.canplay.medical.bean.Contract;
import com.canplay.medical.view.SelectImageDialog;

import java.io.File;

/**
 * Created by Leo on 2017/4/20.
 */

public class ChooseImgUtil implements SelectImageDialog.DialogClickListener{

    private Fragment fragment;

    private Activity activity;

    private SelectImageDialog headDialog;

    private Uri iconUri;

    private Uri cropImageUri;

    private File cropFile;
    private File file;

    public ChooseImgUtil(Activity activity){
        //头像
        this.activity = activity;
        headDialog = new SelectImageDialog(activity);
        headDialog.setDialogClicListen(this);
    }

    public ChooseImgUtil(Fragment fragment,Activity activity){
        this.fragment = fragment;
        this.activity = activity;
    }

    public SelectImageDialog getHeadDialog(){
        return headDialog;
    }

    @Override
    public void buttonFirstClick(){
        //拍照
        if(headDialog != null && headDialog.isShowing()){
            headDialog.dismiss();
        }
        if(PermissionsCheckUtil.checkCamearPermission(activity, false) && PermissionsCheckUtil.checkStoragePermission(activity, false)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //7.0以上需要另外操作
                File dirFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
                String authorities = "com.canplay.medical.fileprovider";
                //通过FileProvider创建一个content类型的Uri，作了修改!!!!
                iconUri = FileProvider.getUriForFile(activity, authorities, file);
                Intent intent = new Intent();
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);//将拍取的照片保存到指定URI
                activity.startActivityForResult(intent, Contract.REQUEST_CODE_TAKE_PHOTO);
            }else{
                // 设置图片的输出路径
                File dirFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
                iconUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                activity.startActivityForResult(intent, Contract.REQUEST_CODE_TAKE_PHOTO);
            }
        }else{
            PermissionsCheckUtil.requestCameraStoragePermission(activity);
        }
    }

    public void buttonFirstClick(int a){
        //拍照
        if(PermissionsCheckUtil.checkCamearPermission(activity, false) && PermissionsCheckUtil.checkStoragePermission(activity, false)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //7.0以上需要另外操作
                File dirFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
                String authorities = "com.canplay.medical.fileprovider";
                //通过FileProvider创建一个content类型的Uri，作了修改!!!!
                iconUri = FileProvider.getUriForFile(fragment.getActivity(), authorities, file);
                Intent intent = new Intent();
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);//将拍取的照片保存到指定URI
                fragment.startActivityForResult(intent, Contract.REQUEST_CODE_TAKE_PHOTO);
            }else{
                // 设置图片的输出路径
                File dirFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
                iconUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                fragment.startActivityForResult(intent, Contract.REQUEST_CODE_TAKE_PHOTO);
            }
        }else{
            PermissionsCheckUtil.requestCameraStoragePermission(activity);
        }
    }

    @Override
    public void buttonTwoClick(){
        //选择照片
        if(headDialog != null && headDialog.isShowing()){
            headDialog.dismiss();
        }
        if(PermissionsCheckUtil.checkStoragePermission(activity, false)){
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //7.0以上需要另外操作
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, Contract.REQUEST_CODE_CHOOSE_IMAGE);
        }else{
            PermissionsCheckUtil.requestStoragePermission(activity);
        }
    }

    public void buttonTwoClick(int a){
        //选择照片
        if(PermissionsCheckUtil.checkStoragePermission(activity, false)){
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //7.0以上需要另外操作
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            fragment.startActivityForResult(intent, Contract.REQUEST_CODE_CHOOSE_IMAGE);
        }else{
            PermissionsCheckUtil.requestStoragePermission(activity);
        }
    }

    @Override
    public void cancleClick(){
        //取消
        if(headDialog != null && headDialog.isShowing()){
            headDialog.dismiss();
        }
    }

    /**
     * 裁减图片操作(拍照)
     */
    public void startCropImage(int x, int y){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(iconUri, "image/*");
        // 使图片处于可裁剪状态
        intent.putExtra("crop", "true");
        // 裁剪框的比例（根据需要显示的图片比例进行设置）
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        // 让裁剪框支持缩放
        intent.putExtra("scale", true);
        //拉伸
        intent.putExtra("scaleUpIfNeeded", true);
        // 裁剪后图片的大小（注意和上面的裁剪比例保持一致）
        intent.putExtra("outputX", CanplayUtils.dip2px(x, activity));
        intent.putExtra("outputY", CanplayUtils.dip2px(y, activity));
        // 传递原图路径
        File file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
        if(!file.exists()){
            file.mkdirs();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //7.0以上需要另外操作
            //cropImageUri = FileProvider.getUriForFile(activity, "com.canplay.medical.fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg"));
            cropFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
            cropImageUri = Uri.fromFile(cropFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        }else{
            cropFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
            cropImageUri = Uri.fromFile(cropFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        }
        // 设置裁剪区域的形状，默认为矩形，也可设置为原形
        //intent.putExtra("circleCrop", true);
        // 设置图片的输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // return-data=true传递的为缩略图，小米手机默认传递大图，所以会导致onActivityResult调用失败
        intent.putExtra("return-data", false);
        // 是否需要人脸识别
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, Contract.REQUEST_CODE_CROP_IMAGE);
    }

    /**
     * 裁减图片操作(选择图片)
     *
     * @param uri
     */
    public void startCropImage(Uri uri, int x, int y){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 使图片处于可裁剪状态
        intent.putExtra("crop", "true");
        // 裁剪框的比例（根据需要显示的图片比例进行设置）
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        // 让裁剪框支持缩放
        intent.putExtra("scale", true);
        //拉伸
        intent.putExtra("scaleUpIfNeeded", true);
        // 裁剪后图片的大小（注意和上面的裁剪比例保持一致）
        intent.putExtra("outputX", CanplayUtils.dip2px(x, activity));
        intent.putExtra("outputY", CanplayUtils.dip2px(y, activity));
        // 传递原图路径
        File file = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/");
        if(!file.exists()){
            file.mkdirs();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //7.0以上需要另外操作
            //cropImageUri = FileProvider.getUriForFile(activity, "com.canplay.medical.fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg"));
            cropFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
            cropImageUri = Uri.fromFile(cropFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        }else{
            cropFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/" + Contract.TEMP + "/" + System.currentTimeMillis() + ".jpg");
            cropImageUri = Uri.fromFile(cropFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        }
        // 设置裁剪区域的形状，默认为矩形，也可设置为原形
        //intent.putExtra("circleCrop", true);
        // 设置图片的输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // return-data=true传递的为缩略图，小米手机默认传递大图，所以会导致onActivityResult调用失败
        intent.putExtra("return-data", false);
        // 是否需要人脸识别
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, Contract.REQUEST_CODE_CROP_IMAGE);
    }

    /**
     * 获得裁剪后的图片
     *
     * @return
     */
    public File getCropFile(){
        return cropFile;
    }

    /**
     * 获得拍照后的图片
     *
     * @return
     */
    public File getCameraFile(){
        return file;
    }
}
