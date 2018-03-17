package com.canplay.medical.util;

import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.canplay.medical.bean.Contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by zhangbo on 2016/10/10.
 */
public class HttpDataCachManager{

    private static HttpDataCachManager instance;

    /**
     * 返回单例对象
     *
     * @return HttpDataCachManager
     */
    public static HttpDataCachManager getInstance(){
        if(instance == null){
            synchronized(HttpDataCachManager.class){
                if(instance == null){
                    instance = new HttpDataCachManager();
                }
            }
        }
        return instance;
    }

    /**
     * 保存缓存信息
     *
     * @param jsonArray
     * @throws Exception
     */
    public void setCachData(String jsonArray, String type){
        if(jsonArray != null){
            try{
                SpUtil.getInstance().putString(type, jsonArray);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 保存缓存信息
     *
     * @param jsonArray
     * @throws Exception
     */
    public void setCachData(boolean jsonArray, String type){
        try{
            SpUtil.getInstance().putBoolean(type, jsonArray);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存信息
     *
     * @param type
     * @throws Exception
     */
    public <T> T getCacheData(String type, TypeReference<List<T>> classTyoe){
        try{
            return (T) JSON.parseObject(SpUtil.getInstance().getString(type), classTyoe);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存信息
     *
     * @param type
     * @throws Exception
     */
    public <T> T getCacheData(String type){
        try{
            return (T) JSON.parse(SpUtil.getInstance().getString(type));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static String personalMessageFillePath = Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/data/person.txt";

    private static String systemMessageFillePath = Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/data/system.txt";

    public void saveNewEntityToJson(String jsonStr, int type){
        FileOutputStream fos = null;
        try{
            createJsonFile();
            if(type == 0){
                fos = new FileOutputStream(systemMessageFillePath);
            }else if(type == 1){
                fos = new FileOutputStream(personalMessageFillePath);
            }
            fos.write(jsonStr.getBytes());
            fos.flush();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(null != fos)
                    fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /*
    public <T> T getJsonToEntity(TypeReference<T> classType) {
        try {
            createJsonFile();
            String jsonStr = "";
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(entityFillePath));
            if (inputReader != null) {
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = bufReader.readLine()) != null) {
                    builder.append(line);
                }
                jsonStr = jsonStr + builder.toString();
                bufReader.close();
                inputReader.close();
            }
            if (jsonStr == null || "".equals(jsonStr)) {
                return null;
            }
            String json = jsonStr.toString().replace("\t", "").toString();
            return (T) JSON.parseObject(json, classType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public <T> T getJsonToEntityList(TypeReference<List<T>> classType, int type){
        try{
            createJsonFile();
            String jsonStr = "";
            InputStreamReader inputReader;
            if(type == 0){
                inputReader = new InputStreamReader(new FileInputStream(systemMessageFillePath));
                if(inputReader != null){
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    StringBuilder builder = new StringBuilder();
                    while((line = bufReader.readLine()) != null){
                        builder.append(line);
                    }
                    jsonStr = jsonStr + builder.toString();
                    bufReader.close();
                    inputReader.close();
                }
            }else if(type == 1){
                inputReader = new InputStreamReader(new FileInputStream(personalMessageFillePath));
                if(inputReader != null){
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    StringBuilder builder = new StringBuilder();
                    while((line = bufReader.readLine()) != null){
                        builder.append(line);
                    }
                    jsonStr = jsonStr + builder.toString();
                    bufReader.close();
                    inputReader.close();
                }
            }
            if(jsonStr == null || "".equals(jsonStr)){
                return null;
            }
            String json = jsonStr.toString().replace("\t", "").toString();
            return (T) JSON.parseObject(json, classType);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void createJsonFile(){
        if(!Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)){
            //判断是否存在SD卡
            return;
        }
        File dirFile = new File(Environment.getExternalStorageDirectory() + "/" + Contract.SOFT_WARE_TYPE + "/data");
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
    }
}
