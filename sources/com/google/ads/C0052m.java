package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.m */
public final class C0052m implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        if (webView instanceof C0047h) {
            ((C0047h) webView).mo908a();
        } else {
            C0061a.m160b("Trying to close WebView that isn't an AdWebView");
        }
    }
}
