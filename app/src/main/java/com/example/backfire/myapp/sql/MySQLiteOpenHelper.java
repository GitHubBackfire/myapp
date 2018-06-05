package com.example.backfire.myapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by backfire on 2017/11/8.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final static String name ="temp.db";
    private final static Integer version = 1 ;
    public MySQLiteOpenHelper(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
