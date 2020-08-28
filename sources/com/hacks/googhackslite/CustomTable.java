package com.hacks.googhackslite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CustomTable {
    private static final String DATABASE_CREATE = "create table custom (_id integer primary key autoincrement, name text not null, f_in_on text not null, f_in_off text not null f_out_on text not null, f_out_off text not null);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(CustomTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS custom");
        onCreate(database);
    }
}
