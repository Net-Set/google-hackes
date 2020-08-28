package com.google.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.ads.util.C0061a;

public class AdActivity extends Activity implements OnClickListener {
    public static final String BASE_URL_PARAM = "baseurl";
    public static final String HTML_PARAM = "html";
    public static final String INTENT_ACTION_PARAM = "i";
    public static final String ORIENTATION_PARAM = "o";
    public static final String TYPE_PARAM = "m";
    public static final String URL_PARAM = "u";

    /* renamed from: a */
    private static final Object f0a = new Object();

    /* renamed from: b */
    private static AdActivity f1b = null;

    /* renamed from: c */
    private static C0042d f2c = null;

    /* renamed from: d */
    private static AdActivity f3d = null;

    /* renamed from: e */
    private static AdActivity f4e = null;

    /* renamed from: f */
    private C0047h f5f;

    /* renamed from: g */
    private boolean f6g;

    /* renamed from: h */
    private long f7h;

    /* renamed from: i */
    private RelativeLayout f8i;

    /* renamed from: j */
    private AdActivity f9j = null;

    /* renamed from: k */
    private boolean f10k;

    /* renamed from: l */
    private C0045g f11l;

    /* renamed from: a */
    private void m5a(String str) {
        C0061a.m160b(str);
        finish();
    }

    /* renamed from: a */
    private void m6a(String str, Throwable th) {
        C0061a.m157a(str, th);
        finish();
    }

    public C0045g getAdVideoView() {
        return this.f11l;
    }

    public C0047h getOpeningAdWebView() {
        if (this.f9j != null) {
            return this.f9j.f5f;
        }
        synchronized (f0a) {
            if (f2c == null) {
                C0061a.m164e("currentAdManager was null while trying to get the opening AdWebView.");
                return null;
            }
            C0047h i = f2c.mo856i();
            if (i != this.f5f) {
                return i;
            }
            return null;
        }
    }

    public static boolean isShowing() {
        boolean z;
        synchronized (f0a) {
            z = f3d != null;
        }
        return z;
    }

    /* JADX INFO: used method not loaded: com.google.ads.util.a.a(java.lang.String):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.google.ads.util.a.a(java.lang.String, java.lang.Throwable):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0024, code lost:
        r1 = new android.content.Intent(r0.getApplicationContext(), com.google.ads.AdActivity.class);
        r1.putExtra("com.google.ads.AdOpener", r5.mo874a());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        com.google.ads.util.C0061a.m156a("Launching AdActivity.");
        r0.startActivity(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0042, code lost:
        com.google.ads.util.C0061a.m157a(r0.getMessage(), (java.lang.Throwable) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000a, code lost:
        r0 = r4.mo852e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r0 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        com.google.ads.util.C0061a.m164e("activity was null while launching an AdActivity.");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void launchAdActivity(com.google.ads.C0042d r4, com.google.ads.C0043e r5) {
        /*
            java.lang.Object r0 = f0a
            monitor-enter(r0)
            com.google.ads.d r1 = f2c     // Catch:{ all -> 0x0021 }
            if (r1 != 0) goto L_0x0016
            f2c = r4     // Catch:{ all -> 0x0021 }
        L_0x0009:
            monitor-exit(r0)
            android.app.Activity r0 = r4.mo852e()
            if (r0 != 0) goto L_0x0024
            java.lang.String r0 = "activity was null while launching an AdActivity."
            com.google.ads.util.C0061a.m164e(r0)
        L_0x0015:
            return
        L_0x0016:
            com.google.ads.d r1 = f2c     // Catch:{ all -> 0x0021 }
            if (r1 == r4) goto L_0x0009
            java.lang.String r1 = "Tried to launch a new AdActivity with a different AdManager."
            com.google.ads.util.C0061a.m160b(r1)     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            goto L_0x0015
        L_0x0021:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        L_0x0024:
            android.content.Intent r1 = new android.content.Intent
            android.content.Context r2 = r0.getApplicationContext()
            java.lang.Class<com.google.ads.AdActivity> r3 = com.google.ads.AdActivity.class
            r1.<init>(r2, r3)
            java.lang.String r2 = "com.google.ads.AdOpener"
            android.os.Bundle r3 = r5.mo874a()
            r1.putExtra(r2, r3)
            java.lang.String r2 = "Launching AdActivity."
            com.google.ads.util.C0061a.m156a(r2)     // Catch:{ ActivityNotFoundException -> 0x0041 }
            r0.startActivity(r1)     // Catch:{ ActivityNotFoundException -> 0x0041 }
            goto L_0x0015
        L_0x0041:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            com.google.ads.util.C0061a.m157a(r1, r0)
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.AdActivity.launchAdActivity(com.google.ads.d, com.google.ads.e):void");
    }

    /* renamed from: a */
    private void m3a(C0042d dVar) {
        this.f5f = null;
        this.f7h = SystemClock.elapsedRealtime();
        this.f10k = true;
        synchronized (f0a) {
            if (f1b == null) {
                f1b = this;
                dVar.mo867t();
            }
        }
    }

    public void moveAdVideoView(int x, int y, int width, int height) {
        if (this.f11l != null) {
            this.f11l.setLayoutParams(m1a(x, y, width, height));
            this.f11l.requestLayout();
        }
    }

    public void newAdVideoView(int x, int y, int width, int height) {
        if (this.f11l == null) {
            this.f11l = new C0045g(this, this.f5f);
            this.f8i.addView(this.f11l, 0, m1a(x, y, width, height));
            synchronized (f0a) {
                if (f2c == null) {
                    C0061a.m164e("currentAdManager was null while trying to get the opening AdWebView.");
                } else {
                    f2c.mo857j().mo916a();
                }
            }
        }
    }

    /* renamed from: a */
    private static LayoutParams m1a(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = new LayoutParams(i3, i4);
        layoutParams.setMargins(i, i2, 0, 0);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        return layoutParams;
    }

    public void onClick(View view) {
        finish();
    }

    /* JADX INFO: used method not loaded: com.google.ads.e.<init>(android.os.Bundle):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.google.ads.util.a.a(java.lang.String):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        r9.f8i = null;
        r9.f10k = false;
        r9.f11l = null;
        r0 = getIntent().getBundleExtra("com.google.ads.AdOpener");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0052, code lost:
        if (r0 != null) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0054, code lost:
        m5a("Could not get the Bundle used to create AdActivity.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0064, code lost:
        r1 = new com.google.ads.C0043e(r0);
        r0 = r1.mo875b();
        r7 = r1.mo876c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0077, code lost:
        if (r0.equals("plusone") == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0079, code lost:
        r1 = new android.content.Intent();
        r1.setComponent(new android.content.ComponentName("com.google.android.apps.plus", "com.google.android.apps.circles.platform.PlusOneActivity"));
        r1.addCategory("android.intent.category.LAUNCHER");
        r1.putExtras(getIntent().getExtras());
        r1.putExtra("com.google.circles.platform.intent.extra.ENTITY", (java.lang.String) r7.get(URL_PARAM));
        r1.putExtra("com.google.circles.platform.intent.extra.ENTITY_TYPE", com.google.ads.C0031ab.C0033b.f31a.f34c);
        r1.putExtra("com.google.circles.platform.intent.extra.ACTION", (java.lang.String) r7.get("a"));
        m3a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        com.google.ads.util.C0061a.m156a("Launching Google+ intent from AdActivity.");
        startActivityForResult(r1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ca, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00cb, code lost:
        m6a(r0.getMessage(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d9, code lost:
        if (r0.equals("intent") == false) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00db, code lost:
        if (r7 != null) goto L_0x00e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00dd, code lost:
        m5a("Could not get the paramMap in launchIntent()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e4, code lost:
        r0 = (java.lang.String) r7.get(URL_PARAM);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ec, code lost:
        if (r0 != null) goto L_0x00f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ee, code lost:
        m5a("Could not get the URL parameter in launchIntent().");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f5, code lost:
        r1 = (java.lang.String) r7.get(INTENT_ACTION_PARAM);
        r2 = (java.lang.String) r7.get(TYPE_PARAM);
        r3 = android.net.Uri.parse(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0109, code lost:
        if (r1 != null) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010b, code lost:
        r0 = new android.content.Intent("android.intent.action.VIEW", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0112, code lost:
        if (r2 == null) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0114, code lost:
        r0.setDataAndType(r3, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0117, code lost:
        m3a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        com.google.ads.util.C0061a.m156a("Launching an intent from AdActivity.");
        startActivity(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0124, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0125, code lost:
        m6a(r0.getMessage(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x012e, code lost:
        r0 = new android.content.Intent(r1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0134, code lost:
        r9.f8i = new android.widget.RelativeLayout(getApplicationContext());
        r9.f8i.setGravity(17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x014c, code lost:
        if (r0.equals("webapp") == false) goto L_0x01cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x014e, code lost:
        r9.f5f = new com.google.ads.C0047h(getApplicationContext(), null);
        r0 = new com.google.ads.C0048i(r6, com.google.ads.C0029a.f30b, true, true);
        r0.mo918c();
        r9.f5f.setWebViewClient(r0);
        r0 = (java.lang.String) r7.get(URL_PARAM);
        r1 = (java.lang.String) r7.get(BASE_URL_PARAM);
        r2 = (java.lang.String) r7.get(HTML_PARAM);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0180, code lost:
        if (r0 == null) goto L_0x01a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0182, code lost:
        r9.f5f.loadUrl(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0187, code lost:
        r0 = (java.lang.String) r7.get(ORIENTATION_PARAM);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0195, code lost:
        if ("p".equals(r0) == false) goto L_0x01b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0197, code lost:
        r0 = com.google.ads.util.AdUtil.m141b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x019b, code lost:
        m4a(r9.f5f, false, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01a2, code lost:
        if (r2 == null) goto L_0x01ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01a4, code lost:
        r9.f5f.loadDataWithBaseURL(r1, r2, "text/html", "utf-8", null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01ae, code lost:
        m5a("Could not get the URL or HTML parameter to show a web app.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01bb, code lost:
        if ("l".equals(r0) == false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01bd, code lost:
        r0 = com.google.ads.util.AdUtil.m128a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01c4, code lost:
        if (r9 != f3d) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01c6, code lost:
        r0 = r6.mo860m();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01cb, code lost:
        r0 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01d3, code lost:
        if (r0.equals("interstitial") == false) goto L_0x01e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01d5, code lost:
        r9.f5f = r6.mo856i();
        m4a(r9.f5f, true, r6.mo860m());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01e6, code lost:
        m5a("Unknown AdOpener, <action: " + r0 + ">");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r10) {
        /*
            r9 = this;
            r3 = 1
            r5 = 0
            r8 = 0
            super.onCreate(r10)
            r9.f6g = r8
            java.lang.Object r1 = f0a
            monitor-enter(r1)
            com.google.ads.d r0 = f2c     // Catch:{ all -> 0x0061 }
            if (r0 == 0) goto L_0x005a
            com.google.ads.d r6 = f2c     // Catch:{ all -> 0x0061 }
            com.google.ads.AdActivity r0 = f3d     // Catch:{ all -> 0x0061 }
            if (r0 != 0) goto L_0x001a
            f3d = r9     // Catch:{ all -> 0x0061 }
            r6.mo866s()     // Catch:{ all -> 0x0061 }
        L_0x001a:
            com.google.ads.AdActivity r0 = r9.f9j     // Catch:{ all -> 0x0061 }
            if (r0 != 0) goto L_0x0026
            com.google.ads.AdActivity r0 = f4e     // Catch:{ all -> 0x0061 }
            if (r0 == 0) goto L_0x0026
            com.google.ads.AdActivity r0 = f4e     // Catch:{ all -> 0x0061 }
            r9.f9j = r0     // Catch:{ all -> 0x0061 }
        L_0x0026:
            f4e = r9     // Catch:{ all -> 0x0061 }
            com.google.ads.Ad r0 = r6.mo853f()     // Catch:{ all -> 0x0061 }
            boolean r2 = r0 instanceof com.google.ads.AdView     // Catch:{ all -> 0x0061 }
            if (r2 == 0) goto L_0x0034
            com.google.ads.AdActivity r2 = f3d     // Catch:{ all -> 0x0061 }
            if (r2 == r9) goto L_0x003e
        L_0x0034:
            boolean r0 = r0 instanceof com.google.ads.InterstitialAd     // Catch:{ all -> 0x0061 }
            if (r0 == 0) goto L_0x0041
            com.google.ads.AdActivity r0 = r9.f9j     // Catch:{ all -> 0x0061 }
            com.google.ads.AdActivity r2 = f3d     // Catch:{ all -> 0x0061 }
            if (r0 != r2) goto L_0x0041
        L_0x003e:
            r6.mo868u()     // Catch:{ all -> 0x0061 }
        L_0x0041:
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            r9.f8i = r5
            r9.f10k = r8
            r9.f11l = r5
            android.content.Intent r0 = r9.getIntent()
            java.lang.String r1 = "com.google.ads.AdOpener"
            android.os.Bundle r0 = r0.getBundleExtra(r1)
            if (r0 != 0) goto L_0x0064
            java.lang.String r0 = "Could not get the Bundle used to create AdActivity."
            r9.m5a(r0)
        L_0x0059:
            return
        L_0x005a:
            java.lang.String r0 = "Could not get currentAdManager."
            r9.m5a(r0)     // Catch:{ all -> 0x0061 }
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            goto L_0x0059
        L_0x0061:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        L_0x0064:
            com.google.ads.e r1 = new com.google.ads.e
            r1.<init>(r0)
            java.lang.String r0 = r1.mo875b()
            java.util.HashMap r7 = r1.mo876c()
            java.lang.String r1 = "plusone"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x00d3
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            android.content.ComponentName r0 = new android.content.ComponentName
            java.lang.String r2 = "com.google.android.apps.plus"
            java.lang.String r3 = "com.google.android.apps.circles.platform.PlusOneActivity"
            r0.<init>(r2, r3)
            r1.setComponent(r0)
            java.lang.String r0 = "android.intent.category.LAUNCHER"
            r1.addCategory(r0)
            android.content.Intent r0 = r9.getIntent()
            android.os.Bundle r0 = r0.getExtras()
            r1.putExtras(r0)
            java.lang.String r2 = "com.google.circles.platform.intent.extra.ENTITY"
            java.lang.String r0 = "u"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r1.putExtra(r2, r0)
            java.lang.String r0 = "com.google.circles.platform.intent.extra.ENTITY_TYPE"
            com.google.ads.ab$b r2 = com.google.ads.C0031ab.C0033b.AD
            java.lang.String r2 = r2.f34c
            r1.putExtra(r0, r2)
            java.lang.String r2 = "com.google.circles.platform.intent.extra.ACTION"
            java.lang.String r0 = "a"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r1.putExtra(r2, r0)
            r9.m3a(r6)
            java.lang.String r0 = "Launching Google+ intent from AdActivity."
            com.google.ads.util.C0061a.m156a(r0)     // Catch:{ ActivityNotFoundException -> 0x00ca }
            r0 = 0
            r9.startActivityForResult(r1, r0)     // Catch:{ ActivityNotFoundException -> 0x00ca }
            goto L_0x0059
        L_0x00ca:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            r9.m6a(r1, r0)
            goto L_0x0059
        L_0x00d3:
            java.lang.String r1 = "intent"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0134
            if (r7 != 0) goto L_0x00e4
            java.lang.String r0 = "Could not get the paramMap in launchIntent()"
            r9.m5a(r0)
            goto L_0x0059
        L_0x00e4:
            java.lang.String r0 = "u"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x00f5
            java.lang.String r0 = "Could not get the URL parameter in launchIntent()."
            r9.m5a(r0)
            goto L_0x0059
        L_0x00f5:
            java.lang.String r1 = "i"
            java.lang.Object r1 = r7.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "m"
            java.lang.Object r2 = r7.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            android.net.Uri r3 = android.net.Uri.parse(r0)
            if (r1 != 0) goto L_0x012e
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.VIEW"
            r0.<init>(r1, r3)
        L_0x0112:
            if (r2 == 0) goto L_0x0117
            r0.setDataAndType(r3, r2)
        L_0x0117:
            r9.m3a(r6)
            java.lang.String r1 = "Launching an intent from AdActivity."
            com.google.ads.util.C0061a.m156a(r1)     // Catch:{ ActivityNotFoundException -> 0x0124 }
            r9.startActivity(r0)     // Catch:{ ActivityNotFoundException -> 0x0124 }
            goto L_0x0059
        L_0x0124:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            r9.m6a(r1, r0)
            goto L_0x0059
        L_0x012e:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>(r1, r3)
            goto L_0x0112
        L_0x0134:
            android.widget.RelativeLayout r1 = new android.widget.RelativeLayout
            android.content.Context r2 = r9.getApplicationContext()
            r1.<init>(r2)
            r9.f8i = r1
            android.widget.RelativeLayout r1 = r9.f8i
            r2 = 17
            r1.setGravity(r2)
            java.lang.String r1 = "webapp"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x01cd
            com.google.ads.h r0 = new com.google.ads.h
            android.content.Context r1 = r9.getApplicationContext()
            r0.<init>(r1, r5)
            r9.f5f = r0
            com.google.ads.i r0 = new com.google.ads.i
            java.util.Map<java.lang.String, com.google.ads.j> r1 = com.google.ads.C0029a.f30b
            r0.<init>(r6, r1, r3, r3)
            r0.mo918c()
            com.google.ads.h r1 = r9.f5f
            r1.setWebViewClient(r0)
            java.lang.String r0 = "u"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "baseurl"
            java.lang.Object r1 = r7.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "html"
            java.lang.Object r2 = r7.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            if (r0 == 0) goto L_0x01a2
            com.google.ads.h r1 = r9.f5f
            r1.loadUrl(r0)
        L_0x0187:
            java.lang.String r0 = "o"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "p"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x01b5
            int r0 = com.google.ads.util.AdUtil.m141b()
        L_0x019b:
            com.google.ads.h r1 = r9.f5f
            r9.m4a(r1, r8, r0)
            goto L_0x0059
        L_0x01a2:
            if (r2 == 0) goto L_0x01ae
            com.google.ads.h r0 = r9.f5f
            java.lang.String r3 = "text/html"
            java.lang.String r4 = "utf-8"
            r0.loadDataWithBaseURL(r1, r2, r3, r4, r5)
            goto L_0x0187
        L_0x01ae:
            java.lang.String r0 = "Could not get the URL or HTML parameter to show a web app."
            r9.m5a(r0)
            goto L_0x0059
        L_0x01b5:
            java.lang.String r1 = "l"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x01c2
            int r0 = com.google.ads.util.AdUtil.m128a()
            goto L_0x019b
        L_0x01c2:
            com.google.ads.AdActivity r0 = f3d
            if (r9 != r0) goto L_0x01cb
            int r0 = r6.mo860m()
            goto L_0x019b
        L_0x01cb:
            r0 = -1
            goto L_0x019b
        L_0x01cd:
            java.lang.String r1 = "interstitial"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x01e6
            com.google.ads.h r0 = r6.mo856i()
            r9.f5f = r0
            int r0 = r6.mo860m()
            com.google.ads.h r1 = r9.f5f
            r9.m4a(r1, r3, r0)
            goto L_0x0059
        L_0x01e6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unknown AdOpener, <action: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ">"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r9.m5a(r0)
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.AdActivity.onCreate(android.os.Bundle):void");
    }

    /* renamed from: a */
    private void m4a(C0047h hVar, boolean z, int i) {
        requestWindowFeature(1);
        getWindow().setFlags(AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END, AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END);
        if (hVar.getParent() != null) {
            m5a("Interstitial created with an AdWebView that has a parent.");
        } else if (hVar.mo910b() != null) {
            m5a("Interstitial created with an AdWebView that is already in use by another AdActivity.");
        } else {
            setRequestedOrientation(i);
            hVar.mo909a(this);
            ImageButton imageButton = new ImageButton(getApplicationContext());
            imageButton.setImageResource(17301527);
            imageButton.setBackgroundColor(0);
            imageButton.setOnClickListener(this);
            imageButton.setPadding(0, 0, 0, 0);
            int applyDimension = (int) TypedValue.applyDimension(1, 32.0f, getResources().getDisplayMetrics());
            FrameLayout frameLayout = new FrameLayout(getApplicationContext());
            frameLayout.addView(imageButton, applyDimension, applyDimension);
            this.f8i.addView(hVar, -1, -1);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(10);
            layoutParams.addRule(9);
            this.f8i.addView(frameLayout, layoutParams);
            this.f8i.setKeepScreenOn(true);
            setContentView(this.f8i);
            if (z) {
                C0029a.m13a((WebView) hVar);
            }
        }
    }

    public void onDestroy() {
        if (this.f8i != null) {
            this.f8i.removeAllViews();
        }
        if (isFinishing()) {
            m2a();
            if (this.f5f != null) {
                this.f5f.stopLoading();
                this.f5f.destroy();
                this.f5f = null;
            }
        }
        super.onDestroy();
    }

    public void onPause() {
        if (isFinishing()) {
            m2a();
        }
        super.onPause();
    }

    /* renamed from: a */
    private void m2a() {
        if (!this.f6g) {
            if (this.f5f != null) {
                C0029a.m19b((WebView) this.f5f);
                this.f5f.mo909a(null);
            }
            if (this.f11l != null) {
                this.f11l.mo901d();
                this.f11l = null;
            }
            if (this == f1b) {
                f1b = null;
            }
            f4e = this.f9j;
            synchronized (f0a) {
                if (!(f2c == null || this.f5f == null)) {
                    if (this.f5f == f2c.mo856i()) {
                        f2c.mo838a();
                    }
                    this.f5f.stopLoading();
                }
                if (this == f3d) {
                    f3d = null;
                    if (f2c != null) {
                        f2c.mo865r();
                        f2c = null;
                    } else {
                        C0061a.m164e("currentAdManager is null while trying to destroy AdActivity.");
                    }
                }
            }
            this.f6g = true;
            C0061a.m156a("AdActivity is closing.");
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (this.f10k && hasFocus && SystemClock.elapsedRealtime() - this.f7h > 250) {
            C0061a.m163d("Launcher AdActivity got focus and is closing.");
            finish();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (!(getOpeningAdWebView() == null || data == null || data.getExtras() == null || data.getExtras().getString("com.google.circles.platform.result.extra.CONFIRMATION") == null || data.getExtras().getString("com.google.circles.platform.result.extra.ACTION") == null)) {
            String string = data.getExtras().getString("com.google.circles.platform.result.extra.CONFIRMATION");
            String string2 = data.getExtras().getString("com.google.circles.platform.result.extra.ACTION");
            if (string.equals("yes")) {
                if (string2.equals("insert")) {
                    C0070z.m174a(getOpeningAdWebView(), true);
                } else if (string2.equals("delete")) {
                    C0070z.m174a(getOpeningAdWebView(), false);
                }
            }
        }
        finish();
    }
}
