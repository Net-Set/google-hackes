package com.google.ads;

import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;
import java.util.Locale;

/* renamed from: com.google.ads.l */
public final class C0051l implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get(AdActivity.URL_PARAM);
        if (str == null) {
            C0061a.m164e("Could not get URL from click gmsg.");
            return;
        }
        C0044f l = dVar.mo859l();
        if (l != null) {
            Uri parse = Uri.parse(str);
            String host = parse.getHost();
            if (host != null && host.toLowerCase(Locale.US).endsWith(".admob.com")) {
                String str2 = null;
                String path = parse.getPath();
                if (path != null) {
                    String[] split = path.split("/");
                    if (split.length >= 4) {
                        str2 = split[2] + "/" + split[3];
                    }
                }
                l.mo880b(str2);
            }
        }
        new Thread(new C0067w(str, webView.getContext().getApplicationContext())).start();
    }
}
