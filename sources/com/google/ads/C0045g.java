package com.google.ads;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.ads.util.C0061a;
import java.lang.ref.WeakReference;

/* renamed from: com.google.ads.g */
public final class C0045g extends FrameLayout implements OnCompletionListener, OnErrorListener, OnPreparedListener {

    /* renamed from: a */
    public MediaController f104a = null;

    /* renamed from: b */
    private WeakReference<AdActivity> f105b;

    /* renamed from: c */
    private C0047h f106c;

    /* renamed from: d */
    private long f107d = 0;

    /* renamed from: e */
    private VideoView f108e;

    /* renamed from: f */
    private String f109f = null;

    /* renamed from: com.google.ads.g$a */
    private static class C0046a implements Runnable {

        /* renamed from: a */
        private WeakReference<C0045g> f110a;

        /* renamed from: b */
        private Handler f111b = new Handler();

        public C0046a(C0045g gVar) {
            this.f110a = new WeakReference<>(gVar);
        }

        public final void run() {
            C0045g gVar = (C0045g) this.f110a.get();
            if (gVar == null) {
                C0061a.m163d("The video must be gone, so cancelling the timeupdate task.");
                return;
            }
            gVar.mo902e();
            this.f111b.postDelayed(this, 250);
        }

        /* renamed from: a */
        public final void mo906a() {
            this.f111b.postDelayed(this, 250);
        }
    }

    public C0045g(AdActivity adActivity, C0047h hVar) {
        super(adActivity);
        this.f105b = new WeakReference<>(adActivity);
        this.f106c = hVar;
        this.f108e = new VideoView(adActivity);
        addView(this.f108e, new LayoutParams(-1, -1, 17));
        new C0046a(this).mo906a();
        this.f108e.setOnCompletionListener(this);
        this.f108e.setOnPreparedListener(this);
        this.f108e.setOnErrorListener(this);
    }

    /* renamed from: a */
    public final void mo894a() {
        if (!TextUtils.isEmpty(this.f109f)) {
            this.f108e.setVideoPath(this.f109f);
        } else {
            C0029a.m15a(this.f106c, "onVideoEvent", "{'event': 'error', 'what': 'no_src'}");
        }
    }

    /* renamed from: a */
    public final void mo898a(boolean z) {
        AdActivity adActivity = (AdActivity) this.f105b.get();
        if (adActivity == null) {
            C0061a.m164e("adActivity was null while trying to enable controls on a video.");
        } else if (z) {
            if (this.f104a == null) {
                this.f104a = new MediaController(adActivity);
            }
            this.f108e.setMediaController(this.f104a);
        } else {
            if (this.f104a != null) {
                this.f104a.hide();
            }
            this.f108e.setMediaController(null);
        }
    }

    /* renamed from: a */
    public final void mo897a(String str) {
        this.f109f = str;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        C0029a.m15a(this.f106c, "onVideoEvent", "{'event': 'ended'}");
    }

    public final boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        C0061a.m164e("Video threw error! <what:" + what + ", extra:" + extra + ">");
        C0029a.m15a(this.f106c, "onVideoEvent", "{'event': 'error', 'what': '" + what + "', 'extra': '" + extra + "'}");
        return true;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        C0029a.m15a(this.f106c, "onVideoEvent", "{'event': 'canplaythrough', 'duration': '" + (((float) this.f108e.getDuration()) / 1000.0f) + "'}");
    }

    /* renamed from: b */
    public final void mo899b() {
        this.f108e.pause();
    }

    /* renamed from: c */
    public final void mo900c() {
        this.f108e.start();
    }

    /* renamed from: a */
    public final void mo895a(int i) {
        this.f108e.seekTo(i);
    }

    /* renamed from: a */
    public final void mo896a(MotionEvent motionEvent) {
        this.f108e.onTouchEvent(motionEvent);
    }

    /* renamed from: d */
    public final void mo901d() {
        this.f108e.stopPlayback();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public final void mo902e() {
        long currentPosition = (long) this.f108e.getCurrentPosition();
        if (this.f107d != currentPosition) {
            C0029a.m15a(this.f106c, "onVideoEvent", "{'event': 'timeupdate', 'time': " + (((float) currentPosition) / 1000.0f) + "}");
            this.f107d = currentPosition;
        }
    }
}
