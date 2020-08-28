package com.google.ads;

import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.google.ads.a */
public final class C0029a {

    /* renamed from: a */
    public static final Map<String, C0049j> f29a;

    /* renamed from: b */
    public static final Map<String, C0049j> f30b;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("/invalidRequest", new C0055p());
        hashMap.put("/loadAdURL", new C0056q());
        f29a = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("/open", new C0058t());
        hashMap2.put("/canOpenURLs", new C0050k());
        hashMap2.put("/close", new C0052m());
        hashMap2.put("/evalInOpener", new C0053n());
        hashMap2.put("/log", new C0057s());
        hashMap2.put("/click", new C0051l());
        hashMap2.put("/httpTrack", new C0054o());
        hashMap2.put("/touch", new C0059u());
        hashMap2.put("/video", new C0066v());
        hashMap2.put("/plusOne", new C0031ab());
        f30b = Collections.unmodifiableMap(hashMap2);
    }

    private C0029a() {
    }

    /* renamed from: a */
    static void m17a(C0042d dVar, Map<String, C0049j> map, Uri uri, WebView webView) {
        String str;
        HashMap b = AdUtil.m144b(uri);
        if (b == null) {
            C0061a.m164e("An error occurred while parsing the message parameters.");
            return;
        }
        if (m21c(uri)) {
            String host = uri.getHost();
            if (host == null) {
                C0061a.m164e("An error occurred while parsing the AMSG parameters.");
                str = null;
            } else if (host.equals("launch")) {
                b.put("a", "intent");
                b.put(AdActivity.URL_PARAM, b.get("url"));
                b.remove("url");
                str = "/open";
            } else if (host.equals("closecanvas")) {
                str = "/close";
            } else if (host.equals("log")) {
                str = "/log";
            } else {
                C0061a.m164e("An error occurred while parsing the AMSG: " + uri.toString());
                str = null;
            }
        } else if (m20b(uri)) {
            str = uri.getPath();
        } else {
            C0061a.m164e("Message was neither a GMSG nor an AMSG.");
            str = null;
        }
        if (str == null) {
            C0061a.m164e("An error occurred while parsing the message.");
            return;
        }
        C0049j jVar = (C0049j) map.get(str);
        if (jVar == null) {
            C0061a.m164e("No AdResponse found, <message: " + str + ">");
        } else {
            jVar.mo818a(dVar, b, webView);
        }
    }

    /* renamed from: a */
    public static boolean m18a(Uri uri) {
        if (uri == null || !uri.isHierarchical()) {
            return false;
        }
        if (m20b(uri) || m21c(uri)) {
            return true;
        }
        return false;
    }

    /* renamed from: b */
    private static boolean m20b(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equals("gmsg")) {
            return false;
        }
        String authority = uri.getAuthority();
        if (authority == null || !authority.equals("mobileads.google.com")) {
            return false;
        }
        return true;
    }

    /* renamed from: c */
    private static boolean m21c(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equals("admob")) {
            return false;
        }
        return true;
    }

    /* renamed from: a */
    public static void m15a(WebView webView, String str, String str2) {
        String str3 = "AFMA_ReceiveMessage";
        if (str2 != null) {
            m14a(webView, str3 + "('" + str + "', " + str2 + ");");
        } else {
            m14a(webView, str3 + "('" + str + "');");
        }
    }

    /* renamed from: a */
    public static void m14a(WebView webView, String str) {
        C0061a.m163d("Sending JS to a WebView: " + str);
        webView.loadUrl("javascript:" + str);
    }

    /* renamed from: a */
    public static void m16a(WebView webView, Map<String, Boolean> map) {
        m15a(webView, "openableURLs", new JSONObject(map).toString());
    }

    /* renamed from: a */
    public static void m13a(WebView webView) {
        m15a(webView, "onshow", "{'version': 'afma-sdk-a-v4.3.1'}");
    }

    /* renamed from: b */
    public static void m19b(WebView webView) {
        m15a(webView, "onhide", null);
    }
}
