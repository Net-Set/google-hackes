package com.google.ads;

import android.app.Activity;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.webkit.WebView;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.util.HashMap;

/* renamed from: com.google.ads.v */
public final class C0066v implements C0049j {
    /* renamed from: a */
    private static int m170a(HashMap<String, String> hashMap, String str, int i, DisplayMetrics displayMetrics) {
        String str2 = (String) hashMap.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return (int) TypedValue.applyDimension(1, (float) Integer.parseInt(str2), displayMetrics);
        } catch (NumberFormatException e) {
            C0061a.m156a("Could not parse \"" + str + "\" in a video gmsg: " + str2);
            return i;
        }
    }

    /* renamed from: a */
    public final void mo818a(C0042d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = (String) hashMap.get("action");
        if (str == null) {
            C0061a.m156a("No \"action\" parameter in a video gmsg.");
        } else if (webView instanceof C0047h) {
            C0047h hVar = (C0047h) webView;
            AdActivity b = hVar.mo910b();
            if (b == null) {
                C0061a.m156a("Could not get adActivity for a video gmsg.");
                return;
            }
            boolean equals = str.equals("new");
            boolean equals2 = str.equals("position");
            if (equals || equals2) {
                DisplayMetrics a = AdUtil.m130a((Activity) b);
                int a2 = m170a(hashMap, "x", 0, a);
                int a3 = m170a(hashMap, "y", 0, a);
                int a4 = m170a(hashMap, "w", -1, a);
                int a5 = m170a(hashMap, "h", -1, a);
                if (!equals || b.getAdVideoView() != null) {
                    b.moveAdVideoView(a2, a3, a4, a5);
                } else {
                    b.newAdVideoView(a2, a3, a4, a5);
                }
            } else {
                C0045g adVideoView = b.getAdVideoView();
                if (adVideoView == null) {
                    C0029a.m15a(hVar, "onVideoEvent", "{'event': 'error', 'what': 'no_video_view'}");
                } else if (str.equals("click")) {
                    DisplayMetrics a6 = AdUtil.m130a((Activity) b);
                    int a7 = m170a(hashMap, "x", 0, a6);
                    int a8 = m170a(hashMap, "y", 0, a6);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    adVideoView.mo896a(MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, (float) a7, (float) a8, 0));
                } else if (str.equals("controls")) {
                    String str2 = (String) hashMap.get("enabled");
                    if (str2 == null) {
                        C0061a.m156a("No \"enabled\" parameter in a controls video gmsg.");
                    } else if (str2.equals("true")) {
                        adVideoView.mo898a(true);
                    } else {
                        adVideoView.mo898a(false);
                    }
                } else if (str.equals("currentTime")) {
                    String str3 = (String) hashMap.get("time");
                    if (str3 == null) {
                        C0061a.m156a("No \"time\" parameter in a currentTime video gmsg.");
                        return;
                    }
                    try {
                        adVideoView.mo895a((int) (Float.parseFloat(str3) * 1000.0f));
                    } catch (NumberFormatException e) {
                        C0061a.m156a("Could not parse \"time\" parameter: " + str3);
                    }
                } else if (str.equals("hide")) {
                    adVideoView.setVisibility(4);
                } else if (str.equals("load")) {
                    adVideoView.mo894a();
                } else if (str.equals("pause")) {
                    adVideoView.mo899b();
                } else if (str.equals("play")) {
                    adVideoView.mo900c();
                } else if (str.equals("show")) {
                    adVideoView.setVisibility(0);
                } else if (str.equals("src")) {
                    adVideoView.mo897a((String) hashMap.get("src"));
                } else {
                    C0061a.m156a("Unknown video action: " + str);
                }
            }
        } else {
            C0061a.m156a("Could not get adWebView for a video gmsg.");
        }
    }
}
