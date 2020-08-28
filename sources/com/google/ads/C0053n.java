package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.n */
public final class C0053n implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("js");
        if (str == null) {
            C0061a.m160b("Could not get the JS to evaluate.");
        }
        if (webView instanceof C0047h) {
            AdActivity b = ((C0047h) webView).mo910b();
            if (b == null) {
                C0061a.m160b("Could not get the AdActivity from the AdWebView.");
                return;
            }
            C0047h openingAdWebView = b.getOpeningAdWebView();
            if (openingAdWebView == null) {
                C0061a.m160b("Could not get the opening WebView.");
            } else {
                C0029a.m14a((WebView) openingAdWebView, str);
            }
        } else {
            C0061a.m160b("Trying to evaluate JS in a WebView that isn't an AdWebView");
        }
    }
}
