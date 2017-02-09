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
    private SQLiteDatabase mDb;

    public DBUtils(Context context) {
        mDb = new IsReadDBHelper(context).getWritableDatabase();
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
        mDb.insert(table,null,values);
    }

    public boolean isRead(String table, String key, int value) {
        boolean isRead=false;
        Cursor cursor = mDb.query(table, null, "key=?", new String[]{key}, null, null, null);
        while (cursor.moveToNext()) {
            int is_read = cursor.getInt(cursor.getColumnIndex("is_read"));
            if (is_read == value) {
                isRead=true;
            }
        }
        cursor.close();
        return isRead;
    }

}
