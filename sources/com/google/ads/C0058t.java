package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.t */
public final class C0058t implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("a");
        if (str == null) {
            C0061a.m156a("Could not get the action parameter for open GMSG.");
        } else if (str.equals("webapp")) {
            AdActivity.launchAdActivity(dVar, new C0043e("webapp", hashMap));
        } else {
            AdActivity.launchAdActivity(dVar, new C0043e("intent", hashMap));
        }
    }
}
