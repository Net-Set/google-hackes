package com.hacks.googhackslite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "custom";
    private static final int DATABASE_VERSION = 1;

    public CustomDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        CustomTable.onCreate(database);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        CustomTable.onUpgrade(database, oldVersion, newVersion);
    }
}
