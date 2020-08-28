package com.google.ads;

import android.app.Activity;
import com.google.ads.util.C0061a;

public class InterstitialAd implements C0026Ad {

    /* renamed from: a */
    private C0042d f28a;

    public InterstitialAd(Activity activity, String adUnitId) {
        this(activity, adUnitId, false);
    }

    public InterstitialAd(Activity activity, String adUnitId, boolean shortTimeout) {
        this.f28a = new C0042d(activity, this, null, adUnitId, shortTimeout);
    }

    public boolean isReady() {
        return this.f28a.mo862o();
    }

    public void loadAd(AdRequest adRequest) {
        this.f28a.mo844a(adRequest);
    }

    public void show() {
        if (isReady()) {
            this.f28a.mo872y();
            this.f28a.mo869v();
            AdActivity.launchAdActivity(this.f28a, new C0043e("interstitial"));
            return;
        }
        C0061a.m162c("Cannot show interstitial because it is not loaded and ready.");
    }

    public void setAdListener(AdListener adListener) {
        this.f28a.mo842a(adListener);
    }

    public void stopLoading() {
        this.f28a.mo873z();
    }
}
