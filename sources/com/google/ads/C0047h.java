package com.google.ads;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.webkit.WebView;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.lang.ref.WeakReference;

/* renamed from: com.google.ads.h */
public final class C0047h extends WebView {

    /* renamed from: a */
    private WeakReference<AdActivity> f112a = null;

    /* renamed from: b */
    private AdSize f113b;

    public C0047h(Context context, AdSize adSize) {
        super(context);
        this.f113b = adSize;
        setBackgroundColor(0);
        AdUtil.m136a((WebView) this);
        getSettings().setJavaScriptEnabled(true);
        setScrollBarStyle(0);
    }

    /* renamed from: a */
    public final void mo908a() {
        AdActivity b = mo910b();
        if (b != null) {
            b.finish();
        }
    }

    /* renamed from: b */
    public final AdActivity mo910b() {
        if (this.f112a != null) {
            return (AdActivity) this.f112a.get();
        }
        return null;
    }

    /* renamed from: a */
    public final void mo909a(AdActivity adActivity) {
        this.f112a = new WeakReference<>(adActivity);
    }

    public final void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        try {
            super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
        } catch (Exception e) {
            C0061a.m157a("An error occurred while loading data in AdWebView:", (Throwable) e);
        }
    }

    public final void loadUrl(String url) {
        try {
            super.loadUrl(url);
        } catch (Exception e) {
            C0061a.m157a("An error occurred while loading a URL in AdWebView:", (Throwable) e);
        }
    }

    public final void stopLoading() {
        try {
            super.stopLoading();
        } catch (Exception e) {
            C0061a.m157a("An error occurred while stopping loading in AdWebView:", (Throwable) e);
        }
    }

    public final void destroy() {
        try {
            super.destroy();
        } catch (Exception e) {
            C0061a.m157a("An error occurred while destroying an AdWebView:", (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (this.f113b == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int mode = MeasureSpec.getMode(widthMeasureSpec);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = MeasureSpec.getMode(heightMeasureSpec);
            int size2 = MeasureSpec.getSize(heightMeasureSpec);
            float f = getContext().getResources().getDisplayMetrics().density;
            int width = (int) (((float) this.f113b.getWidth()) * f);
            int height = (int) (((float) this.f113b.getHeight()) * f);
            if (mode == 0 || mode2 == 0) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else if (((float) width) - (6.0f * f) > ((float) size) || height > size2) {
                C0061a.m164e("Not enough space to show ad! Wants: <" + width + ", " + height + ">, Has: <" + size + ", " + size2 + ">");
                setVisibility(8);
                setMeasuredDimension(0, 0);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
