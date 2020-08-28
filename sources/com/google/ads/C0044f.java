package com.google.ads;

import android.os.SystemClock;
import com.google.ads.util.C0061a;
import java.util.LinkedList;

/* renamed from: com.google.ads.f */
public final class C0044f {

    /* renamed from: f */
    private static long f95f = 0;

    /* renamed from: a */
    public String f96a;

    /* renamed from: b */
    private LinkedList<Long> f97b = new LinkedList<>();

    /* renamed from: c */
    private long f98c;

    /* renamed from: d */
    private long f99d;

    /* renamed from: e */
    private LinkedList<Long> f100e = new LinkedList<>();

    /* renamed from: g */
    private String f101g;

    /* renamed from: h */
    private boolean f102h = false;

    /* renamed from: i */
    private boolean f103i = false;

    C0044f() {
        mo877a();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo877a() {
        this.f97b.clear();
        this.f98c = 0;
        this.f99d = 0;
        this.f100e.clear();
        this.f101g = null;
        this.f102h = false;
        this.f103i = false;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo879b() {
        C0061a.m163d("Ad clicked.");
        this.f97b.add(Long.valueOf(SystemClock.elapsedRealtime()));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final void mo881c() {
        C0061a.m163d("Ad request loaded.");
        this.f98c = SystemClock.elapsedRealtime();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public final void mo882d() {
        C0061a.m163d("Ad request started.");
        this.f99d = SystemClock.elapsedRealtime();
        f95f++;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public final long mo883e() {
        if (this.f97b.size() != this.f100e.size()) {
            return -1;
        }
        return (long) this.f97b.size();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: f */
    public final String mo884f() {
        if (this.f97b.isEmpty() || this.f97b.size() != this.f100e.size()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.f97b.size()) {
                return sb.toString();
            }
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(((Long) this.f100e.get(i2)).longValue() - ((Long) this.f97b.get(i2)).longValue()));
            i = i2 + 1;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: g */
    public final String mo885g() {
        if (this.f97b.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.f97b.size()) {
                return sb.toString();
            }
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(((Long) this.f97b.get(i2)).longValue() - this.f98c));
            i = i2 + 1;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: h */
    public final long mo886h() {
        return this.f98c - this.f99d;
    }

    /* renamed from: i */
    static long m82i() {
        return f95f;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: j */
    public final String mo887j() {
        return this.f101g;
    }

    /* renamed from: a */
    public final void mo878a(String str) {
        C0061a.m163d("Prior ad identifier = " + str);
        this.f101g = str;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: k */
    public final boolean mo888k() {
        return this.f102h;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: l */
    public final void mo889l() {
        C0061a.m163d("Interstitial network error.");
        this.f102h = true;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: m */
    public final boolean mo890m() {
        return this.f103i;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: n */
    public final void mo891n() {
        C0061a.m163d("Interstitial no fill.");
        this.f103i = true;
    }

    /* renamed from: o */
    public final void mo892o() {
        C0061a.m163d("Landing page dismissed.");
        this.f100e.add(Long.valueOf(SystemClock.elapsedRealtime()));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: p */
    public final String mo893p() {
        return this.f96a;
    }

    /* renamed from: b */
    public final void mo880b(String str) {
        C0061a.m163d("Prior impression ticket = " + str);
        this.f96a = str;
    }
}
