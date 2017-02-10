package com.shizhen5452.justlook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Create by EminemShi on 2017/2/9
 */

public class DBUtils {

    private static DBUtils sDbUtils;
    private SQLiteDatabase mIsreadDb;
    private SQLiteDatabase mZhihuDB;

    public DBUtils(Context context) {
        mIsreadDb = new IsReadDBHelper(context).getWritableDatabase();
        mZhihuDB = new ZhihuDBHelper(context).getWritableDatabase();
    }

    public static DBUtils getDB(Context context) {
        if (sDbUtils==null)
        sDbUtils = new DBUtils(context);
        return sDbUtils;
    }

    public void putIsRead(String table, String key, int value) {
        ContentValues values=new ContentValues();
        values.put("key",key);
        values.put("is_read",value);
        mIsreadDb.insert(table,null,values);
    }

    public boolean isRead(String table, String key, int value) {
        boolean isRead=false;
        Cursor cursor = mIsreadDb.query(table, null, "key=?", new String[]{key}, null, null, null);
        while (cursor.moveToNext()) {
            int is_read = cursor.getInt(cursor.getColumnIndex("is_read"));
            if (is_read == value) {
                isRead=true;
            }
        }
        cursor.close();
        return isRead;
    }

    public void putIsBookmark(String table, String key, int value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key",key);
        contentValues.put("is_bookmarked",value);
        mZhihuDB.insert(table,null,contentValues);
    }

    public void deleteIsBookmark(String table, String key) {
        mZhihuDB.delete(table,"key=?",new String[]{key});
    }

    public boolean isBookmarked(String table,String key,int value) {
        boolean isBookmarked=false;
        Cursor cursor = mZhihuDB.query(table, null, "key=?", new String[]{key}, null, null, null);
        while (cursor.moveToNext()) {
            int is_bookmarked = cursor.getInt(cursor.getColumnIndex("is_bookmarked"));
            if (is_bookmarked == value) {
                isBookmarked=true;
            }
        }
        cursor.close();
        return isBookmarked;
    }

}
