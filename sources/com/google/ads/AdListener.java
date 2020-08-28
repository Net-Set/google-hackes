package com.google.ads;

import com.google.ads.AdRequest.ErrorCode;

public interface AdListener {
    void onDismissScreen(C0026Ad ad);

    void onFailedToReceiveAd(C0026Ad ad, ErrorCode errorCode);

    void onLeaveApplication(C0026Ad ad);

    void onPresentScreen(C0026Ad ad);

    void onReceiveAd(C0026Ad ad);
}
