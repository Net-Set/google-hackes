package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.q */
public final class C0056q implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("url");
        String str2 = (String) hashMap.get("afma_notify_dt");
        boolean equals = "1".equals(hashMap.get("drt_include"));
        C0061a.m162c("Received ad url: <\"url\": \"" + str + "\", \"afmaNotifyDt\": \"" + str2 + "\">");
        C0036c g = dVar.mo854g();
        if (g != null) {
            g.mo831a(equals);
            g.mo833b(str);
        }
    }
}
