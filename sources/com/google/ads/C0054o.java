package com.google.ads;

import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.o */
public final class C0054o implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get(AdActivity.URL_PARAM);
        if (str == null) {
            C0061a.m164e("Could not get URL from click gmsg.");
        } else {
            new Thread(new C0067w(str, webView.getContext().getApplicationContext())).start();
        }
    }
}
