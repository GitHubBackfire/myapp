package com.example.backfire.myapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

    /**
     * 解压文件
     * @param zipFile
     * 压缩包所在地址
     * @param targetDir
     */
    public static void singleZip(String zipFile, String targetDir) {
        int BUFFER = 4096; // 这里缓冲区我们使用4KB，
        String strEntry; // 保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; // 缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            ZipEntry entry; // 每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    File entryFile = new File(targetDir +"/"+strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception e) {

        }
    }



}
