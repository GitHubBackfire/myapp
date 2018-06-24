package com.example.backfire.myapp.widget.readview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 确定每一个子条目所包含的内容
 * Created by backfire on 2018/6/16.
 */

public class CalculateSingleItemContentHelper {

    /**
     * 调整图片大小
     * @param context
     * @param bitmap
     * @return
     */
    public static Bitmap adjustBitmapSize(Context context, Bitmap bitmap){
        int maxHeight = context.getResources().getDisplayMetrics().heightPixels * 1/3 ;
        int maxWidth = context.getResources().getDisplayMetrics().widthPixels * 9/19;
        Bitmap newBitmap;
        Matrix matrix = new Matrix();
        float scale = 1;
        if(bitmap.getHeight() > maxHeight && bitmap.getWidth() <= maxWidth){
            scale = maxHeight / bitmap.getHeight();
            matrix.postScale(scale, scale);
            newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return newBitmap;
        }
        else if(bitmap.getHeight() <= maxHeight && bitmap.getWidth() > maxWidth){
            scale = maxWidth / bitmap.getWidth();
            matrix.postScale(scale, scale);
            newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return newBitmap;
        }
        else if(bitmap.getHeight() > maxHeight && bitmap.getWidth() > maxWidth){
            int scaleHeight = maxHeight / bitmap.getHeight();
            int scaleWidth = maxWidth / bitmap.getWidth();
            scale = scaleHeight > scaleWidth ? scaleWidth : scaleHeight;
            matrix.postScale(scale, scale);
            newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return newBitmap;

        }
        return null;
    }



}
