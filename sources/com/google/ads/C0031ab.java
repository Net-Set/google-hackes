package com.google.ads;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.ab */
public final class C0031ab implements C0049j {

    /* renamed from: com.google.ads.ab$a */
    private static class C0032a implements OnClickListener {
        public final void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    /* renamed from: com.google.ads.ab$b */
    public enum C0033b {
        AD("ad"),
        APP("app");
        

        /* renamed from: c */
        public String f34c;

        private C0033b(String str) {
            this.f34c = str;
        }
    }

    /* renamed from: com.google.ads.ab$c */
    private static class C0034c implements OnClickListener {

        /* renamed from: a */
        private C0042d f35a;

        public C0034c(C0042d dVar) {
            this.f35a = dVar;
        }

        public final void onClick(DialogInterface dialogInterface, int i) {
            HashMap hashMap = new HashMap();
            hashMap.put(AdActivity.URL_PARAM, "market://details?id=com.google.android.apps.plus");
            AdActivity.launchAdActivity(this.f35a, new C0043e("intent", hashMap));
        }
    }

    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("a");
        if (str != null) {
            if (str.equals("resize")) {
                C0029a.m14a(webView, "(G_resizeIframe(" + ((String) hashMap.get(AdActivity.URL_PARAM)) + "))");
                return;
            } else if (str.equals("state")) {
                C0070z.m173a(dVar.mo852e(), webView, (String) hashMap.get(AdActivity.URL_PARAM));
                return;
            }
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.google.android.apps.plus", "com.google.android.apps.circles.platform.PlusOneActivity"));
        Activity e = dVar.mo852e();
        if (e == null) {
            C0061a.m164e("Activity was null when responding to +1 action");
        } else if (C0030aa.m23a(intent, e.getApplicationContext())) {
            AdActivity.launchAdActivity(dVar, new C0043e("plusone", hashMap));
        } else if (!C0030aa.m23a(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.apps.plus")), e.getApplicationContext())) {
        } else {
            if (TextUtils.isEmpty((CharSequence) hashMap.get("d")) || TextUtils.isEmpty((CharSequence) hashMap.get(AdActivity.ORIENTATION_PARAM)) || TextUtils.isEmpty((CharSequence) hashMap.get("c"))) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AdActivity.URL_PARAM, "market://details?id=com.google.android.apps.plus");
                AdActivity.launchAdActivity(dVar, new C0043e("intent", hashMap2));
                return;
            }
            Builder builder = new Builder(e);
            builder.setMessage((CharSequence) hashMap.get("d")).setPositiveButton((CharSequence) hashMap.get(AdActivity.ORIENTATION_PARAM), new C0034c(dVar)).setNegativeButton((CharSequence) hashMap.get("c"), new C0032a());
            builder.create().show();
        }
    }
}
