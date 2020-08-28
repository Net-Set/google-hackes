package com.google.ads.util;

import android.content.Context;
import android.util.DisplayMetrics;

/* renamed from: com.google.ads.util.c */
public final class C0065c {
    private C0065c() {
    }

    /* renamed from: a */
    public static int m168a(Context context, DisplayMetrics displayMetrics) {
        return m167a(context, displayMetrics.density, displayMetrics.heightPixels);
    }

    /* renamed from: b */
    public static int m169b(Context context, DisplayMetrics displayMetrics) {
        return m167a(context, displayMetrics.density, displayMetrics.widthPixels);
    }

    /* renamed from: a */
    private static int m167a(Context context, float f, int i) {
        if ((context.getApplicationInfo().flags & 8192) != 0) {
            return (int) (((float) i) / f);
        }
        return i;
    }
}
