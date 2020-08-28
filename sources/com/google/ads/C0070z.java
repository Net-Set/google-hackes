package com.google.ads;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.lang.ref.WeakReference;
import java.util.Date;

/* renamed from: com.google.ads.z */
public final class C0070z {

    /* renamed from: com.google.ads.z$a */
    private static class C0071a implements Runnable {

        /* renamed from: a */
        private WeakReference<Activity> f169a;

        public C0071a(Activity activity) {
            this.f169a = new WeakReference<>(activity);
        }

        public final void run() {
            String str;
            try {
                Activity activity = (Activity) this.f169a.get();
                if (activity == null) {
                    C0061a.m156a("Activity was null while making a doritos cookie request.");
                    return;
                }
                Cursor query = activity.getContentResolver().query(C0069y.f166b, C0069y.f168d, null, null, null);
                if (query == null || !query.moveToFirst() || query.getColumnNames().length <= 0) {
                    C0061a.m156a("Google+ app not installed, not storing doritos cookie");
                    str = null;
                } else {
                    str = query.getString(query.getColumnIndex(query.getColumnName(0)));
                }
                Editor edit = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).edit();
                if (!TextUtils.isEmpty(str)) {
                    edit.putString("drt", str);
                    edit.putLong("drt_ts", new Date().getTime());
                } else {
                    edit.putString("drt", "");
                    edit.putLong("drt_ts", 0);
                }
                edit.commit();
            } catch (Exception e) {
                C0061a.m157a("An unknown error occurred while sending a doritos request.", (Throwable) e);
            }
        }
    }

    /* renamed from: com.google.ads.z$b */
    private static class C0072b implements Runnable {

        /* renamed from: a */
        private WeakReference<Activity> f170a;

        /* renamed from: b */
        private WebView f171b;

        /* renamed from: c */
        private String f172c;

        public C0072b(Activity activity, WebView webView, String str) {
            this.f170a = new WeakReference<>(activity);
            this.f172c = str;
            this.f171b = webView;
        }

        public final void run() {
            boolean z;
            try {
                Uri withAppendedPath = Uri.withAppendedPath(C0069y.f165a, this.f172c);
                Activity activity = (Activity) this.f170a.get();
                if (activity == null) {
                    C0061a.m156a("Activity was null while getting the +1 button state.");
                    return;
                }
                Cursor query = activity.getContentResolver().query(withAppendedPath, C0069y.f167c, null, null, null);
                if (query == null || !query.moveToFirst()) {
                    C0061a.m156a("Google+ app not installed, showing ad as not +1'd");
                    z = false;
                } else {
                    z = query.getInt(query.getColumnIndex("has_plus1")) == 1;
                }
                this.f171b.post(new C0073c(this.f171b, z));
            } catch (Exception e) {
                C0061a.m157a("An unknown error occurred while updating the +1 state.", (Throwable) e);
            }
        }
    }

    /* renamed from: com.google.ads.z$c */
    private static class C0073c implements Runnable {

        /* renamed from: a */
        private boolean f173a;

        /* renamed from: b */
        private WebView f174b;

        public C0073c(WebView webView, boolean z) {
            this.f174b = webView;
            this.f173a = z;
        }

        public final void run() {
            C0070z.m174a(this.f174b, this.f173a);
        }
    }

    private C0070z() {
    }

    /* renamed from: a */
    public static void m174a(WebView webView, boolean z) {
        C0029a.m14a(webView, "(G_updatePlusOne(" + z + "))");
    }

    /* renamed from: a */
    public static void m173a(Activity activity, WebView webView, String str) {
        new Thread(new C0072b(activity, webView, str)).start();
    }

    /* renamed from: a */
    public static void m172a(Activity activity) {
        new Thread(new C0071a(activity)).start();
    }
}
