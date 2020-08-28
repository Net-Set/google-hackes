package com.google.ads.util;

import android.util.Log;
import com.google.ads.AdRequest;

/* renamed from: com.google.ads.util.a */
public final class C0061a {
    private C0061a() {
    }

    /* renamed from: a */
    public static void m156a(String str) {
        if (m159a(AdRequest.LOGTAG, 3)) {
            Log.d(AdRequest.LOGTAG, str);
        }
    }

    /* renamed from: b */
    public static void m160b(String str) {
        if (m159a(AdRequest.LOGTAG, 6)) {
            Log.e(AdRequest.LOGTAG, str);
        }
    }

    /* renamed from: a */
    public static void m157a(String str, Throwable th) {
        if (m159a(AdRequest.LOGTAG, 6)) {
            Log.e(AdRequest.LOGTAG, str, th);
        }
    }

    /* renamed from: c */
    public static void m162c(String str) {
        if (m159a(AdRequest.LOGTAG, 4)) {
            Log.i(AdRequest.LOGTAG, str);
        }
    }

    /* renamed from: d */
    public static void m163d(String str) {
        if (m159a(AdRequest.LOGTAG, 2)) {
            Log.v(AdRequest.LOGTAG, str);
        }
    }

    /* renamed from: e */
    public static void m164e(String str) {
        if (m159a(AdRequest.LOGTAG, 5)) {
            Log.w(AdRequest.LOGTAG, str);
        }
    }

    /* renamed from: a */
    public static void m158a(Throwable th) {
        if (m159a(AdRequest.LOGTAG, 5)) {
            Log.w(AdRequest.LOGTAG, th);
        }
    }

    /* renamed from: b */
    public static void m161b(String str, Throwable th) {
        if (m159a(AdRequest.LOGTAG, 5)) {
            Log.w(AdRequest.LOGTAG, str, th);
        }
    }

    /* renamed from: a */
    private static boolean m159a(String str, int i) {
        return (i >= 5) || Log.isLoggable(str, i);
    }
}
