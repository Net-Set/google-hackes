package com.google.ads;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.ads.i */
public final class C0048i extends WebViewClient {

    /* renamed from: a */
    private C0042d f114a;

    /* renamed from: b */
    private Map<String, C0049j> f115b;

    /* renamed from: c */
    private boolean f116c;

    /* renamed from: d */
    private boolean f117d;

    /* renamed from: e */
    private boolean f118e = false;

    /* renamed from: f */
    private boolean f119f = false;

    public C0048i(C0042d dVar, Map<String, C0049j> map, boolean z, boolean z2) {
        this.f114a = dVar;
        this.f115b = map;
        this.f116c = z;
        this.f117d = z2;
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
        String str;
        C0061a.m156a("shouldOverrideUrlLoading(\"" + url + "\")");
        Uri parse = Uri.parse(url);
        HashMap b = AdUtil.m144b(parse);
        if (b == null) {
            C0061a.m164e("An error occurred while parsing the url parameters.");
            return true;
        }
        String str2 = (String) b.get("ai");
        if (str2 != null) {
            this.f114a.mo859l().mo878a(str2);
        }
        if (C0029a.m18a(parse)) {
            C0029a.m17a(this.f114a, this.f115b, parse, webView);
            return true;
        } else if (this.f117d) {
            if (AdUtil.m140a(parse)) {
                return super.shouldOverrideUrlLoading(webView, url);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(AdActivity.URL_PARAM, url);
            AdActivity.launchAdActivity(this.f114a, new C0043e("intent", hashMap));
            return true;
        } else if (this.f116c) {
            if (!this.f114a.mo870w() || !AdUtil.m140a(parse)) {
                str = "intent";
            } else {
                str = "webapp";
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put(AdActivity.URL_PARAM, parse.toString());
            AdActivity.launchAdActivity(this.f114a, new C0043e(str, hashMap2));
            return true;
        } else {
            C0061a.m164e("URL is not a GMSG and can't handle URL: " + url);
            return true;
        }
    }

    public final void onPageFinished(WebView view, String str) {
        if (this.f118e) {
            C0036c g = this.f114a.mo854g();
            if (g != null) {
                g.mo832b();
            } else {
                C0061a.m156a("adLoader was null while trying to setFinishedLoadingHtml().");
            }
            this.f118e = false;
        }
        if (this.f119f) {
            C0029a.m13a(view);
            this.f119f = false;
        }
    }

    /* renamed from: a */
    public final void mo916a() {
        this.f117d = false;
    }

    /* renamed from: b */
    public final void mo917b() {
        this.f118e = true;
    }

    /* renamed from: c */
    public final void mo918c() {
        this.f119f = true;
    }
}
