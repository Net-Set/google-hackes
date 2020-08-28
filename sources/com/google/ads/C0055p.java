package com.google.ads;

import android.webkit.WebView;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.p */
public final class C0055p implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("errors");
        C0061a.m164e("Invalid " + ((String) hashMap.get("type")) + " request error: " + str);
        C0036c g = dVar.mo854g();
        if (g != null) {
            g.mo827a(ErrorCode.INVALID_REQUEST);
        }
    }
}
