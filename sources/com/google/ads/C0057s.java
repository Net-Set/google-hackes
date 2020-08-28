package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.s */
public final class C0057s implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("afma_notify_dt");
        C0061a.m162c("Received log message: <\"string\": \"" + ((String) hashMap.get("string")) + "\", \"afmaNotifyDt\": \"" + str + "\">");
    }
}
