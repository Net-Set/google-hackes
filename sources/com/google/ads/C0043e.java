package com.google.ads;

import android.os.Bundle;
import java.io.Serializable;
import java.util.HashMap;

/* renamed from: com.google.ads.e */
public final class C0043e {

    /* renamed from: a */
    private String f93a;

    /* renamed from: b */
    private HashMap<String, String> f94b;

    public C0043e(Bundle bundle) {
        this.f93a = bundle.getString("action");
        Serializable serializable = bundle.getSerializable("params");
        this.f94b = serializable instanceof HashMap ? (HashMap) serializable : null;
    }

    public C0043e(String str) {
        this.f93a = str;
    }

    public C0043e(String str, HashMap<String, String> hashMap) {
        this(str);
        this.f94b = hashMap;
    }

    /* renamed from: a */
    public final Bundle mo874a() {
        Bundle bundle = new Bundle();
        bundle.putString("action", this.f93a);
        bundle.putSerializable("params", this.f94b);
        return bundle;
    }

    /* renamed from: b */
    public final String mo875b() {
        return this.f93a;
    }

    /* renamed from: c */
    public final HashMap<String, String> mo876c() {
        return this.f94b;
    }
}
