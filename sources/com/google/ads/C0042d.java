package com.google.ads;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.preference.PreferenceManager;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: com.google.ads.d */
public final class C0042d {

    /* renamed from: a */
    private static final Object f71a = new Object();

    /* renamed from: b */
    private WeakReference<Activity> f72b;

    /* renamed from: c */
    private C0026Ad f73c;

    /* renamed from: d */
    private AdListener f74d = null;

    /* renamed from: e */
    private C0036c f75e = null;

    /* renamed from: f */
    private AdRequest f76f = null;

    /* renamed from: g */
    private AdSize f77g;

    /* renamed from: h */
    private C0044f f78h = new C0044f();

    /* renamed from: i */
    private String f79i;

    /* renamed from: j */
    private C0047h f80j;

    /* renamed from: k */
    private C0048i f81k;

    /* renamed from: l */
    private Handler f82l = new Handler();

    /* renamed from: m */
    private long f83m;

    /* renamed from: n */
    private boolean f84n = false;

    /* renamed from: o */
    private boolean f85o = false;

    /* renamed from: p */
    private SharedPreferences f86p;

    /* renamed from: q */
    private long f87q = 0;

    /* renamed from: r */
    private C0068x f88r;

    /* renamed from: s */
    private boolean f89s;

    /* renamed from: t */
    private LinkedList<String> f90t;

    /* renamed from: u */
    private LinkedList<String> f91u;

    /* renamed from: v */
    private int f92v;

    public C0042d(Activity activity, C0026Ad ad, AdSize adSize, String str, boolean z) {
        this.f72b = new WeakReference<>(activity);
        this.f73c = ad;
        this.f77g = adSize;
        this.f79i = str;
        this.f89s = z;
        synchronized (f71a) {
            this.f86p = activity.getApplicationContext().getSharedPreferences("GoogleAdMobAdsPrefs", 0);
            if (z) {
                long j = this.f86p.getLong("Timeout" + str, -1);
                if (j < 0) {
                    this.f83m = 5000;
                } else {
                    this.f83m = j;
                }
            } else {
                this.f83m = 60000;
            }
        }
        this.f88r = new C0068x(this);
        this.f90t = new LinkedList<>();
        this.f91u = new LinkedList<>();
        mo838a();
        AdUtil.m154h(activity.getApplicationContext());
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized void mo846a(String str) {
        C0061a.m156a("Adding a tracking URL: " + str);
        this.f90t.add(str);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized void mo847a(LinkedList<String> linkedList) {
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            C0061a.m156a("Adding a click tracking URL: " + ((String) it.next()));
        }
        this.f91u = linkedList;
    }

    /* renamed from: a */
    public final synchronized void mo838a() {
        Activity e = mo852e();
        if (e == null) {
            C0061a.m156a("activity was null while trying to create an AdWebView.");
        } else {
            this.f80j = new C0047h(e.getApplicationContext(), this.f77g);
            this.f80j.setVisibility(8);
            if (this.f73c instanceof AdView) {
                this.f81k = new C0048i(this, C0029a.f30b, true, false);
            } else {
                this.f81k = new C0048i(this, C0029a.f30b, true, true);
            }
            this.f80j.setWebViewClient(this.f81k);
        }
    }

    /* renamed from: b */
    public final synchronized void mo848b() {
        mo842a((AdListener) null);
        mo873z();
        this.f80j.destroy();
    }

    /* renamed from: c */
    public final synchronized void mo850c() {
        if (this.f85o) {
            C0061a.m156a("Disabling refreshing.");
            this.f82l.removeCallbacks(this.f88r);
            this.f85o = false;
        } else {
            C0061a.m156a("Refreshing is already disabled.");
        }
    }

    /* renamed from: d */
    public final synchronized void mo851d() {
        if (!(this.f73c instanceof AdView)) {
            C0061a.m156a("Tried to enable refreshing on something other than an AdView.");
        } else if (!this.f85o) {
            C0061a.m156a("Enabling refreshing every " + this.f87q + " milliseconds.");
            this.f82l.postDelayed(this.f88r, this.f87q);
            this.f85o = true;
        } else {
            C0061a.m156a("Refreshing is already enabled.");
        }
    }

    /* renamed from: e */
    public final Activity mo852e() {
        return (Activity) this.f72b.get();
    }

    /* renamed from: f */
    public final C0026Ad mo853f() {
        return this.f73c;
    }

    /* renamed from: g */
    public final synchronized C0036c mo854g() {
        return this.f75e;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: h */
    public final String mo855h() {
        return this.f79i;
    }

    /* renamed from: i */
    public final synchronized C0047h mo856i() {
        return this.f80j;
    }

    /* renamed from: j */
    public final synchronized C0048i mo857j() {
        return this.f81k;
    }

    /* renamed from: k */
    public final AdSize mo858k() {
        return this.f77g;
    }

    /* renamed from: l */
    public final C0044f mo859l() {
        return this.f78h;
    }

    /* renamed from: a */
    public final synchronized void mo840a(int i) {
        this.f92v = i;
    }

    /* renamed from: m */
    public final synchronized int mo860m() {
        return this.f92v;
    }

    /* renamed from: n */
    public final long mo861n() {
        return this.f83m;
    }

    /* renamed from: A */
    private synchronized boolean m41A() {
        return this.f75e != null;
    }

    /* renamed from: o */
    public final synchronized boolean mo862o() {
        return this.f84n;
    }

    /* renamed from: p */
    public final synchronized boolean mo863p() {
        return this.f85o;
    }

    /* renamed from: a */
    public final synchronized void mo844a(AdRequest adRequest) {
        boolean z = false;
        synchronized (this) {
            if (m41A()) {
                C0061a.m164e("loadAd called while the ad is already loading, so aborting.");
            } else if (AdActivity.isShowing()) {
                C0061a.m164e("loadAd called while an interstitial or landing page is displayed, so aborting");
            } else {
                Activity e = mo852e();
                if (e == null) {
                    C0061a.m164e("activity is null while trying to load an ad.");
                } else if (AdUtil.m148c(e.getApplicationContext()) && AdUtil.m146b(e.getApplicationContext())) {
                    long j = this.f86p.getLong("GoogleAdMobDoritosLife", 60000);
                    SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(e.getApplicationContext());
                    if (C0030aa.m22a(e) && (!defaultSharedPreferences.contains("drt") || !defaultSharedPreferences.contains("drt_ts") || defaultSharedPreferences.getLong("drt_ts", 0) < new Date().getTime() - j)) {
                        z = true;
                    }
                    if (z) {
                        C0070z.m172a(e);
                    }
                    this.f84n = false;
                    this.f90t.clear();
                    this.f76f = adRequest;
                    this.f75e = new C0036c(this);
                    this.f75e.mo828a(adRequest);
                }
            }
        }
    }

    /* renamed from: a */
    public final synchronized void mo843a(ErrorCode errorCode) {
        this.f75e = null;
        if (this.f73c instanceof InterstitialAd) {
            if (errorCode == ErrorCode.NO_FILL) {
                this.f78h.mo891n();
            } else if (errorCode == ErrorCode.NETWORK_ERROR) {
                this.f78h.mo889l();
            }
        }
        C0061a.m162c("onFailedToReceiveAd(" + errorCode + ")");
        if (this.f74d != null) {
            this.f74d.onFailedToReceiveAd(this.f73c, errorCode);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: q */
    public final synchronized void mo864q() {
        this.f75e = null;
        this.f84n = true;
        this.f80j.setVisibility(0);
        this.f78h.mo881c();
        if (this.f73c instanceof AdView) {
            mo869v();
        }
        C0061a.m162c("onReceiveAd()");
        if (this.f74d != null) {
            this.f74d.onReceiveAd(this.f73c);
        }
    }

    /* renamed from: r */
    public final synchronized void mo865r() {
        this.f78h.mo892o();
        C0061a.m162c("onDismissScreen()");
        if (this.f74d != null) {
            this.f74d.onDismissScreen(this.f73c);
        }
    }

    /* renamed from: s */
    public final synchronized void mo866s() {
        C0061a.m162c("onPresentScreen()");
        if (this.f74d != null) {
            this.f74d.onPresentScreen(this.f73c);
        }
    }

    /* renamed from: t */
    public final synchronized void mo867t() {
        C0061a.m162c("onLeaveApplication()");
        if (this.f74d != null) {
            this.f74d.onLeaveApplication(this.f73c);
        }
    }

    /* renamed from: u */
    public final void mo868u() {
        this.f78h.mo879b();
        m42B();
    }

    /* renamed from: v */
    public final synchronized void mo869v() {
        Activity activity = (Activity) this.f72b.get();
        if (activity == null) {
            C0061a.m164e("activity was null while trying to ping tracking URLs.");
        } else {
            Iterator it = this.f90t.iterator();
            while (it.hasNext()) {
                new Thread(new C0067w((String) it.next(), activity.getApplicationContext())).start();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: w */
    public final synchronized boolean mo870w() {
        return !this.f91u.isEmpty();
    }

    /* renamed from: B */
    private synchronized void m42B() {
        Activity activity = (Activity) this.f72b.get();
        if (activity == null) {
            C0061a.m164e("activity was null while trying to ping click tracking URLs.");
        } else {
            Iterator it = this.f91u.iterator();
            while (it.hasNext()) {
                new Thread(new C0067w((String) it.next(), activity.getApplicationContext())).start();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo845a(Runnable runnable) {
        this.f82l.post(runnable);
    }

    /* renamed from: x */
    public final synchronized void mo871x() {
        if (this.f76f == null) {
            C0061a.m156a("Tried to refresh before calling loadAd().");
        } else if (this.f73c instanceof AdView) {
            if (!((AdView) this.f73c).isShown() || !AdUtil.m150d()) {
                C0061a.m156a("Not refreshing because the ad is not visible.");
            } else {
                C0061a.m162c("Refreshing ad.");
                mo844a(this.f76f);
            }
            this.f82l.postDelayed(this.f88r, this.f87q);
        } else {
            C0061a.m156a("Tried to refresh an ad that wasn't an AdView.");
        }
    }

    /* renamed from: a */
    public final synchronized void mo842a(AdListener adListener) {
        this.f74d = adListener;
    }

    /* renamed from: a */
    public final void mo841a(long j) {
        synchronized (f71a) {
            Editor edit = this.f86p.edit();
            edit.putLong("Timeout" + this.f79i, j);
            edit.commit();
            if (this.f89s) {
                this.f83m = j;
            }
        }
    }

    /* renamed from: y */
    public final synchronized void mo872y() {
        this.f84n = false;
    }

    /* renamed from: a */
    public final synchronized void mo839a(float f) {
        this.f87q = (long) (1000.0f * f);
    }

    /* renamed from: b */
    public final synchronized void mo849b(long j) {
        if (j > 0) {
            this.f86p.edit().putLong("GoogleAdMobDoritosLife", j).commit();
        }
    }

    /* renamed from: z */
    public final synchronized void mo873z() {
        if (this.f75e != null) {
            this.f75e.mo825a();
            this.f75e = null;
        }
        this.f80j.stopLoading();
    }
}
