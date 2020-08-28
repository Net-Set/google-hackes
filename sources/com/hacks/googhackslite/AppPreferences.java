package com.hacks.googhackslite;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "com.hacks.googhackslite_preferences";
    private SharedPreferences SharedPrefs;
    private Editor prefsEditor = this.SharedPrefs.edit();

    public AppPreferences(Context context) {
        this.SharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, 0);
    }

    public String getPrefs() {
        return this.SharedPrefs.getString("first_time", "");
    }

    public void setPrefs(String text) {
        this.prefsEditor.putString("first_time", text);
        this.prefsEditor.commit();
    }
}
