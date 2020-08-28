package com.google.ads;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

/* renamed from: com.google.ads.b */
public final class C0035b implements Runnable {

    /* renamed from: a */
    private C0036c f36a;

    /* renamed from: b */
    private C0042d f37b;

    /* renamed from: c */
    private volatile boolean f38c;

    /* renamed from: d */
    private boolean f39d;

    /* renamed from: e */
    private String f40e;

    C0035b(C0036c cVar, C0042d dVar) {
        this.f36a = cVar;
        this.f37b = dVar;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo821a() {
        this.f38c = true;
    }

    /* renamed from: a */
    private void m25a(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Tracking-Urls");
        if (headerField != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(headerField);
            while (stringTokenizer.hasMoreTokens()) {
                this.f37b.mo846a(stringTokenizer.nextToken());
            }
        }
        m26b(httpURLConnection);
        String headerField2 = httpURLConnection.getHeaderField("X-Afma-Refresh-Rate");
        if (headerField2 != null) {
            try {
                float parseFloat = Float.parseFloat(headerField2);
                if (parseFloat > 0.0f) {
                    this.f37b.mo839a(parseFloat);
                    if (!this.f37b.mo863p()) {
                        this.f37b.mo851d();
                    }
                } else if (this.f37b.mo863p()) {
                    this.f37b.mo850c();
                }
            } catch (NumberFormatException e) {
                C0061a.m161b("Could not get refresh value: " + headerField2, e);
            }
        }
        String headerField3 = httpURLConnection.getHeaderField("X-Afma-Interstitial-Timeout");
        if (headerField3 != null) {
            try {
                this.f37b.mo841a((long) (Float.parseFloat(headerField3) * 1000.0f));
            } catch (NumberFormatException e2) {
                C0061a.m161b("Could not get timeout value: " + headerField3, e2);
            }
        }
        String headerField4 = httpURLConnection.getHeaderField("X-Afma-Orientation");
        if (headerField4 != null) {
            if (headerField4.equals("portrait")) {
                this.f36a.mo826a(AdUtil.m141b());
            } else if (headerField4.equals("landscape")) {
                this.f36a.mo826a(AdUtil.m128a());
            }
        }
        if (!TextUtils.isEmpty(httpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life"))) {
            try {
                this.f37b.mo849b(Long.parseLong(httpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life")));
            } catch (NumberFormatException e3) {
                C0061a.m164e("Got bad value of Doritos cookie cache life from header: " + httpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life") + ". Using default value instead.");
            }
        }
    }

    /* renamed from: b */
    private void m26b(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Click-Tracking-Urls");
        if (headerField != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(headerField);
            while (stringTokenizer.hasMoreTokens()) {
                this.f36a.mo829a(stringTokenizer.nextToken());
            }
        }
    }

    /* renamed from: a */
    public final void mo823a(boolean z) {
        this.f39d = z;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo822a(String str) {
        this.f40e = str;
        this.f38c = false;
        new Thread(this).start();
    }

    public final void run() {
        HttpURLConnection httpURLConnection;
        while (!this.f38c) {
            try {
                httpURLConnection = (HttpURLConnection) new URL(this.f40e).openConnection();
                Activity e = this.f37b.mo852e();
                if (e == null) {
                    C0061a.m162c("activity was null in AdHtmlLoader.");
                    this.f36a.mo827a(ErrorCode.INTERNAL_ERROR);
                    httpURLConnection.disconnect();
                    return;
                }
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(e);
                if (this.f39d && !TextUtils.isEmpty(defaultSharedPreferences.getString("drt", ""))) {
                    if (AdUtil.f133a == 8) {
                        httpURLConnection.addRequestProperty("X-Afma-drt-Cookie", defaultSharedPreferences.getString("drt", ""));
                    } else {
                        httpURLConnection.addRequestProperty("Cookie", defaultSharedPreferences.getString("drt", ""));
                    }
                }
                AdUtil.m137a(httpURLConnection, e.getApplicationContext());
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (300 <= responseCode && responseCode < 400) {
                    String headerField = httpURLConnection.getHeaderField("Location");
                    if (headerField == null) {
                        C0061a.m162c("Could not get redirect location from a " + responseCode + " redirect.");
                        this.f36a.mo827a(ErrorCode.INTERNAL_ERROR);
                        httpURLConnection.disconnect();
                        return;
                    }
                    m25a(httpURLConnection);
                    this.f40e = headerField;
                    httpURLConnection.disconnect();
                } else if (responseCode == 200) {
                    m25a(httpURLConnection);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 4096);
                    StringBuilder sb = new StringBuilder();
                    while (!this.f38c) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                        sb.append("\n");
                    }
                    String sb2 = sb.toString();
                    C0061a.m156a("Response content is: " + sb2);
                    if (sb2 == null || sb2.trim().length() <= 0) {
                        C0061a.m156a("Response message is null or zero length: " + sb2);
                        this.f36a.mo827a(ErrorCode.NO_FILL);
                        httpURLConnection.disconnect();
                        return;
                    }
                    this.f36a.mo830a(sb2, this.f40e);
                    httpURLConnection.disconnect();
                    return;
                } else if (responseCode == 400) {
                    C0061a.m162c("Bad request");
                    this.f36a.mo827a(ErrorCode.INVALID_REQUEST);
                    httpURLConnection.disconnect();
                    return;
                } else {
                    C0061a.m162c("Invalid response code: " + responseCode);
                    this.f36a.mo827a(ErrorCode.INTERNAL_ERROR);
                    httpURLConnection.disconnect();
                    return;
                }
            } catch (MalformedURLException e2) {
                C0061a.m157a("Received malformed ad url from javascript.", (Throwable) e2);
                this.f36a.mo827a(ErrorCode.INTERNAL_ERROR);
                return;
            } catch (IOException e3) {
                C0061a.m161b("IOException connecting to ad url.", e3);
                this.f36a.mo827a(ErrorCode.NETWORK_ERROR);
                return;
            } catch (Exception e4) {
                C0061a.m157a("An unknown error occurred in AdHtmlLoader.", (Throwable) e4);
                this.f36a.mo827a(ErrorCode.INTERNAL_ERROR);
                return;
            } catch (Throwable th) {
                httpURLConnection.disconnect();
                throw th;
            }
        }
    }
}
