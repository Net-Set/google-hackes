package com.google.ads;

public class AdSize {
    public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
    public static final AdSize IAB_BANNER = new AdSize(468, 60, "468x60_as");
    public static final AdSize IAB_LEADERBOARD = new AdSize(728, 90, "728x90_as");
    public static final AdSize IAB_MRECT = new AdSize(300, 250, "300x250_as");

    /* renamed from: a */
    private int f24a;

    /* renamed from: b */
    private int f25b;

    /* renamed from: c */
    private String f26c;

    public AdSize(int width, int height) {
        this(width, height, null);
    }

    private AdSize(int width, int height, String format) {
        this.f24a = width;
        this.f25b = height;
        this.f26c = format;
    }

    public int getWidth() {
        return this.f24a;
    }

    public int getHeight() {
        return this.f25b;
    }

    public String toString() {
        return this.f26c;
    }
}
