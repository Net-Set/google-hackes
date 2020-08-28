package com.google.ads;

import com.google.ads.util.C0061a;
import java.lang.ref.WeakReference;

/* renamed from: com.google.ads.x */
public final class C0068x implements Runnable {

    /* renamed from: a */
    private WeakReference<C0042d> f164a;

    public C0068x(C0042d dVar) {
        this.f164a = new WeakReference<>(dVar);
    }

    public final void run() {
        C0042d dVar = (C0042d) this.f164a.get();
        if (dVar == null) {
            C0061a.m156a("The ad must be gone, so cancelling the refresh timer.");
        } else {
            dVar.mo871x();
        }
    }
}
