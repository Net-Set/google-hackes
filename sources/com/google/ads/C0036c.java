package com.google.ads;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.searchads.SearchAdRequest;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.google.ads.c */
public final class C0036c implements Runnable {

    /* renamed from: a */
    private String f41a = null;

    /* renamed from: b */
    private String f42b = null;

    /* renamed from: c */
    private C0035b f43c;

    /* renamed from: d */
    private C0042d f44d;

    /* renamed from: e */
    private AdRequest f45e;

    /* renamed from: f */
    private WebView f46f;

    /* renamed from: g */
    private String f47g = null;

    /* renamed from: h */
    private LinkedList<String> f48h = new LinkedList<>();

    /* renamed from: i */
    private volatile boolean f49i;

    /* renamed from: j */
    private ErrorCode f50j = null;

    /* renamed from: k */
    private boolean f51k = false;

    /* renamed from: l */
    private int f52l = -1;

    /* renamed from: m */
    private Thread f53m;

    /* renamed from: n */
    private boolean f54n;

    /* renamed from: com.google.ads.c$a */
    private class C0037a implements Runnable {

        /* renamed from: b */
        private final C0042d f56b;

        /* renamed from: c */
        private final WebView f57c;

        /* renamed from: d */
        private final C0035b f58d;

        /* renamed from: e */
        private final ErrorCode f59e;

        /* renamed from: f */
        private final boolean f60f;

        public C0037a(C0042d dVar, WebView webView, C0035b bVar, ErrorCode errorCode, boolean z) {
            this.f56b = dVar;
            this.f57c = webView;
            this.f58d = bVar;
            this.f59e = errorCode;
            this.f60f = z;
        }

        public final void run() {
            if (this.f57c != null) {
                this.f57c.stopLoading();
                this.f57c.destroy();
            }
            if (this.f58d != null) {
                this.f58d.mo821a();
            }
            if (this.f60f) {
                C0047h i = this.f56b.mo856i();
                i.stopLoading();
                i.setVisibility(8);
            }
            this.f56b.mo843a(this.f59e);
        }
    }

    /* renamed from: com.google.ads.c$b */
    private class C0038b extends Exception {
        public C0038b(String str) {
            super(str);
        }
    }

    /* renamed from: com.google.ads.c$c */
    private class C0039c implements Runnable {

        /* renamed from: b */
        private final String f63b;

        /* renamed from: c */
        private final String f64c;

        /* renamed from: d */
        private final WebView f65d;

        public C0039c(WebView webView, String str, String str2) {
            this.f65d = webView;
            this.f63b = str;
            this.f64c = str2;
        }

        public final void run() {
            if (this.f64c != null) {
                this.f65d.loadDataWithBaseURL(this.f63b, this.f64c, "text/html", "utf-8", null);
            } else {
                this.f65d.loadUrl(this.f63b);
            }
        }
    }

    /* renamed from: com.google.ads.c$d */
    private class C0040d extends Exception {
        public C0040d(String str) {
            super(str);
        }
    }

    /* renamed from: com.google.ads.c$e */
    private class C0041e implements Runnable {

        /* renamed from: b */
        private final C0042d f68b;

        /* renamed from: c */
        private final LinkedList<String> f69c;

        /* renamed from: d */
        private final int f70d;

        public C0041e(C0042d dVar, LinkedList<String> linkedList, int i) {
            this.f68b = dVar;
            this.f69c = linkedList;
            this.f70d = i;
        }

        public final void run() {
            this.f68b.mo847a(this.f69c);
            this.f68b.mo840a(this.f70d);
            this.f68b.mo864q();
        }
    }

    public C0036c(C0042d dVar) {
        this.f44d = dVar;
        Activity e = dVar.mo852e();
        if (e != null) {
            this.f46f = new C0047h(e, null);
            this.f46f.setWebViewClient(new C0048i(dVar, C0029a.f29a, false, false));
            this.f46f.setVisibility(8);
            this.f46f.setWillNotDraw(true);
            this.f43c = new C0035b(this, dVar);
            return;
        }
        this.f46f = null;
        this.f43c = null;
        C0061a.m164e("activity was null while trying to create an AdLoader.");
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized void mo829a(String str) {
        this.f48h.add(str);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo825a() {
        C0061a.m156a("AdLoader cancelled.");
        this.f46f.stopLoading();
        this.f46f.destroy();
        if (this.f53m != null) {
            this.f53m.interrupt();
            this.f53m = null;
        }
        this.f43c.mo821a();
        this.f49i = true;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo828a(AdRequest adRequest) {
        this.f45e = adRequest;
        this.f49i = false;
        this.f53m = new Thread(this);
        this.f53m.start();
    }

    public final void run() {
        synchronized (this) {
            if (this.f46f == null || this.f43c == null) {
                C0061a.m164e("adRequestWebView was null while trying to load an ad.");
                m31a(ErrorCode.INTERNAL_ERROR, false);
                return;
            }
            Activity e = this.f44d.mo852e();
            if (e == null) {
                C0061a.m164e("activity was null while forming an ad request.");
                m31a(ErrorCode.INTERNAL_ERROR, false);
                return;
            }
            long n = this.f44d.mo861n();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Object obj = this.f45e.getRequestMap(e).get("extras");
            if (obj instanceof Map) {
                Map map = (Map) obj;
                Object obj2 = map.get("_adUrl");
                if (obj2 instanceof String) {
                    this.f41a = (String) obj2;
                }
                Object obj3 = map.get("_orientation");
                if (obj3 instanceof String) {
                    if (obj3.equals("p")) {
                        this.f52l = 1;
                    } else if (obj3.equals("l")) {
                        this.f52l = 0;
                    }
                }
            }
            if (this.f41a == null) {
                try {
                    this.f44d.mo845a((Runnable) new C0039c(this.f46f, null, m30a(this.f45e, e)));
                    long elapsedRealtime2 = n - (SystemClock.elapsedRealtime() - elapsedRealtime);
                    if (elapsedRealtime2 > 0) {
                        try {
                            wait(elapsedRealtime2);
                        } catch (InterruptedException e2) {
                            C0061a.m156a("AdLoader InterruptedException while getting the URL: " + e2);
                            return;
                        }
                    }
                    try {
                        if (!this.f49i) {
                            if (this.f50j != null) {
                                m31a(this.f50j, false);
                                return;
                            } else if (this.f47g == null) {
                                C0061a.m162c("AdLoader timed out after " + n + "ms while getting the URL.");
                                m31a(ErrorCode.NETWORK_ERROR, false);
                                return;
                            } else {
                                this.f43c.mo823a(this.f54n);
                                this.f43c.mo822a(this.f47g);
                                long elapsedRealtime3 = n - (SystemClock.elapsedRealtime() - elapsedRealtime);
                                if (elapsedRealtime3 > 0) {
                                    try {
                                        wait(elapsedRealtime3);
                                    } catch (InterruptedException e3) {
                                        C0061a.m156a("AdLoader InterruptedException while getting the HTML: " + e3);
                                        return;
                                    }
                                }
                                if (!this.f49i) {
                                    if (this.f50j != null) {
                                        m31a(this.f50j, false);
                                        return;
                                    } else if (this.f42b == null) {
                                        C0061a.m162c("AdLoader timed out after " + n + "ms while getting the HTML.");
                                        m31a(ErrorCode.NETWORK_ERROR, false);
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    } catch (Exception e4) {
                        C0061a.m157a("An unknown error occurred in AdLoader.", (Throwable) e4);
                        m31a(ErrorCode.INTERNAL_ERROR, true);
                    }
                } catch (C0040d e5) {
                    C0061a.m162c("Unable to connect to network: " + e5);
                    m31a(ErrorCode.NETWORK_ERROR, false);
                    return;
                } catch (C0038b e6) {
                    C0061a.m162c("Caught internal exception: " + e6);
                    m31a(ErrorCode.INTERNAL_ERROR, false);
                    return;
                }
            }
            C0047h i = this.f44d.mo856i();
            this.f44d.mo857j().mo917b();
            this.f44d.mo845a((Runnable) new C0039c(i, this.f41a, this.f42b));
            long elapsedRealtime4 = n - (SystemClock.elapsedRealtime() - elapsedRealtime);
            if (elapsedRealtime4 > 0) {
                try {
                    wait(elapsedRealtime4);
                } catch (InterruptedException e7) {
                    C0061a.m156a("AdLoader InterruptedException while loading the HTML: " + e7);
                    return;
                }
            }
            if (this.f51k) {
                this.f44d.mo845a((Runnable) new C0041e(this.f44d, this.f48h, this.f52l));
            } else {
                C0061a.m162c("AdLoader timed out after " + n + "ms while loading the HTML.");
                m31a(ErrorCode.NETWORK_ERROR, true);
            }
        }
    }

    /* renamed from: a */
    private String m30a(AdRequest adRequest, Activity activity) throws C0038b, C0040d {
        Context applicationContext = activity.getApplicationContext();
        Map requestMap = adRequest.getRequestMap(applicationContext);
        C0044f l = this.f44d.mo859l();
        long h = l.mo886h();
        if (h > 0) {
            requestMap.put("prl", Long.valueOf(h));
        }
        String g = l.mo885g();
        if (g != null) {
            requestMap.put("ppcl", g);
        }
        String f = l.mo884f();
        if (f != null) {
            requestMap.put("pcl", f);
        }
        long e = l.mo883e();
        if (e > 0) {
            requestMap.put("pcc", Long.valueOf(e));
        }
        requestMap.put("preqs", Long.valueOf(C0044f.m82i()));
        String j = l.mo887j();
        if (j != null) {
            requestMap.put("pai", j);
        }
        if (l.mo888k()) {
            requestMap.put("aoi_timeout", "true");
        }
        if (l.mo890m()) {
            requestMap.put("aoi_nofill", "true");
        }
        String p = l.mo893p();
        if (p != null) {
            requestMap.put("pit", p);
        }
        l.mo877a();
        l.mo882d();
        if (this.f44d.mo853f() instanceof InterstitialAd) {
            requestMap.put("format", "interstitial_mb");
        } else {
            AdSize k = this.f44d.mo858k();
            String adSize = k.toString();
            if (adSize != null) {
                requestMap.put("format", adSize);
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("w", Integer.valueOf(k.getWidth()));
                hashMap.put("h", Integer.valueOf(k.getHeight()));
                requestMap.put("ad_frame", hashMap);
            }
        }
        requestMap.put("slotname", this.f44d.mo855h());
        requestMap.put("js", "afma-sdk-a-v4.3.1");
        try {
            int i = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionCode;
            String f2 = AdUtil.m152f(applicationContext);
            if (!TextUtils.isEmpty(f2)) {
                requestMap.put("mv", f2);
            }
            requestMap.put("msid", applicationContext.getPackageName());
            requestMap.put("app_name", i + ".android." + applicationContext.getPackageName());
            requestMap.put("isu", AdUtil.m131a(applicationContext));
            String d = AdUtil.m149d(applicationContext);
            if (d == null) {
                throw new C0040d("NETWORK_ERROR");
            }
            requestMap.put("net", d);
            String e2 = AdUtil.m151e(applicationContext);
            if (!(e2 == null || e2.length() == 0)) {
                requestMap.put("cap", e2);
            }
            requestMap.put("u_audio", Integer.valueOf(AdUtil.m153g(applicationContext).ordinal()));
            DisplayMetrics a = AdUtil.m130a(activity);
            requestMap.put("u_sd", Float.valueOf(a.density));
            requestMap.put("u_h", Integer.valueOf(AdUtil.m129a(applicationContext, a)));
            requestMap.put("u_w", Integer.valueOf(AdUtil.m142b(applicationContext, a)));
            requestMap.put("hl", Locale.getDefault().getLanguage());
            if (AdUtil.m147c()) {
                requestMap.put("simulator", Integer.valueOf(1));
            }
            String str = (this.f45e instanceof SearchAdRequest ? "<html><head><script src=\"http://www.gstatic.com/safa/sdk-core-v40.js\"></script><script>" : "<html><head><script src=\"http://media.admob.com/sdk-core-v40.js\"></script><script>") + "AFMA_buildAdURL" + "(" + AdUtil.m134a(requestMap) + ");" + "</script></head><body></body></html>";
            C0061a.m162c("adRequestUrlHtml: " + str);
            return str;
        } catch (NameNotFoundException e3) {
            throw new C0038b("NameNotFoundException");
        }
    }

    /* renamed from: a */
    private void m31a(ErrorCode errorCode, boolean z) {
        this.f43c.mo821a();
        this.f44d.mo845a((Runnable) new C0037a(this.f44d, this.f46f, this.f43c, errorCode, z));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized void mo830a(String str, String str2) {
        this.f41a = str2;
        this.f42b = str;
        notify();
    }

    /* renamed from: b */
    public final synchronized void mo833b(String str) {
        this.f47g = str;
        notify();
    }

    /* renamed from: a */
    public final synchronized void mo827a(ErrorCode errorCode) {
        this.f50j = errorCode;
        notify();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final synchronized void mo832b() {
        this.f51k = true;
        notify();
    }

    /* renamed from: a */
    public final synchronized void mo826a(int i) {
        this.f52l = i;
    }

    /* renamed from: a */
    public final void mo831a(boolean z) {
        this.f54n = z;
    }
}
