package com.shizhen5452.justlook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shizhen5452.justlook.utils.Constant;

/**
 * Create by EminemShi on 2017/2/10
 */

public class ZhihuDBHelper extends SQLiteOpenHelper {

    public ZhihuDBHelper(Context context) {
        super(context, Constant.ZHIHU_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Constant.ZHIHU+"(id integer primary key autoincrement,key text unique,is_bookmarked integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
