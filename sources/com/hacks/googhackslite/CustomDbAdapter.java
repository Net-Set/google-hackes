package com.hacks.googhackslite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CustomDbAdapter {
    private static final String DB_TABLE = "custom";
    public static final String KEY_F_IN_OFF = "f_in_off";
    public static final String KEY_F_IN_ON = "f_in_on";
    public static final String KEY_F_OUT_OFF = "f_out_off";
    public static final String KEY_F_OUT_ON = "f_out_on";
    public static final String KEY_NAME = "name";
    public static final String KEY_ROWID = "_id";
    private Context context;

    /* renamed from: db */
    private SQLiteDatabase f175db;
    private CustomDbHelper dbHelper;

    public CustomDbAdapter(Context context2) {
        this.context = context2;
    }

    public CustomDbAdapter open() throws SQLException {
        this.dbHelper = new CustomDbHelper(this.context);
        this.f175db = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public long createTodo(String name, String f_in_on, String f_in_off, String f_out_on, String f_out_off) {
        return this.f175db.insert(DB_TABLE, null, createContentValues(name, f_in_on, f_in_off, f_out_on, f_out_off));
    }

    public boolean updateTodo(long rowId, String name, String f_in_on, String f_in_off, String f_out_on, String f_out_off) {
        return this.f175db.update(DB_TABLE, createContentValues(name, f_in_on, f_in_off, f_out_on, f_out_off), new StringBuilder("_id=").append(rowId).toString(), null) > 0;
    }

    public boolean deleteTodo(long rowId) {
        return this.f175db.delete(DB_TABLE, new StringBuilder("_id=").append(rowId).toString(), null) > 0;
    }

    public Cursor fetchAllCustom() {
        return this.f175db.query(DB_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_F_IN_ON, KEY_F_IN_OFF, KEY_F_OUT_ON, KEY_F_OUT_OFF}, null, null, null, null, null);
    }

    public Cursor fetchCustom(long rowId) throws SQLException {
        Cursor mCursor = this.f175db.query(true, DB_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_F_IN_ON, KEY_F_IN_OFF, KEY_F_OUT_ON, KEY_F_OUT_OFF}, "_id=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private ContentValues createContentValues(String name, String f_in_on, String f_in_off, String f_out_on, String f_out_off) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_F_IN_ON, f_in_on);
        values.put(KEY_F_IN_OFF, f_in_off);
        values.put(KEY_F_OUT_ON, f_out_on);
        values.put(KEY_F_OUT_OFF, f_out_off);
        return values;
    }
}
