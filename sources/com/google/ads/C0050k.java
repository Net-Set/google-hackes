package com.google.ads;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.util.C0061a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.ads.k */
public final class C0050k implements C0049j {
    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        boolean z;
        String str = (String) hashMap.get("urls");
        if (str == null) {
            C0061a.m164e("Could not get the urls param from canOpenURLs gmsg.");
            return;
        }
        String[] split = str.split(",");
        HashMap hashMap2 = new HashMap();
        PackageManager packageManager = webView.getContext().getPackageManager();
        for (String str2 : split) {
            String[] split2 = str2.split(";", 2);
            if (packageManager.resolveActivity(new Intent(split2.length >= 2 ? split2[1] : "android.intent.action.VIEW", Uri.parse(split2[0])), 65536) != null) {
                z = true;
            } else {
                z = false;
            }
            hashMap2.put(str2, Boolean.valueOf(z));
        }
        C0029a.m16a(webView, (Map<String, Boolean>) hashMap2);
    }
}
