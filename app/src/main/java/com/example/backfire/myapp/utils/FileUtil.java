package com.example.backfire.myapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by backfire on 2018/6/7.
 */

public class FileUtil {
    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        Log.i("issduse",Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())+",");
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 创建本地目录
     */
    public static boolean createLocalFile(Context context, String fileName){
        boolean result = false;
        if(isSdCardAvailable()){
            File sd = Environment.getExternalStorageDirectory();
            String path = sd.getAbsolutePath()+File.separator+fileName;
            Log.i("createpath",path);
            File file = new File(path);
            if(!file.exists()){
               return file.mkdir();
            }
        }else{
            //sd卡不可用
        }
        return  result;
    }

    /**
     * 返回本地epub格式图书目录
     * @return
     */

    public static String getLocalBooksFilePath(){
        if(isSdCardAvailable()){
            return Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+StaticUtil.LOCAL_BOOKS_FILE_NAME;
        }
        else return "";
    }

    /**
     * 拷贝文件
     */
    public static boolean copyFile(File src, String fileName){
        boolean result = false;
        if(src == null || fileName == null || fileName.length() <= 0){
            return  result;
        }
        File dest = new File(getLocalBooksFilePath()+File.separator+fileName);
        Log.i("destpath",dest.getAbsolutePath());
        if(dest != null && dest.exists()){
            dest.delete();
        }
        try{
            dest.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        FileChannel srcChannel = null;
        FileChannel destChannel = null;
        try {
            srcChannel = new FileInputStream(src).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            srcChannel.close();
            destChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }



}
