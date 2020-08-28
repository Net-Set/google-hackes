package com.google.ads;

import android.content.Context;

/* renamed from: com.google.ads.w */
public final class C0067w implements Runnable {

    /* renamed from: a */
    private Context f162a;

    /* renamed from: b */
    private String f163b;

    public C0067w(String str, Context context) {
        this.f163b = str;
        this.f162a = context;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0068 }
            r0.<init>()     // Catch:{ IOException -> 0x0068 }
            java.lang.String r1 = "Pinging URL: "
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x0068 }
            java.lang.String r1 = r4.f163b     // Catch:{ IOException -> 0x0068 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x0068 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x0068 }
            com.google.ads.util.C0061a.m156a(r0)     // Catch:{ IOException -> 0x0068 }
            java.net.URL r0 = new java.net.URL     // Catch:{ IOException -> 0x0068 }
            java.lang.String r1 = r4.f163b     // Catch:{ IOException -> 0x0068 }
            r0.<init>(r1)     // Catch:{ IOException -> 0x0068 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ IOException -> 0x0068 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IOException -> 0x0068 }
            android.content.Context r1 = r4.f162a     // Catch:{ all -> 0x0063 }
            com.google.ads.util.AdUtil.m137a(r0, r1)     // Catch:{ all -> 0x0063 }
            r1 = 1
            r0.setInstanceFollowRedirects(r1)     // Catch:{ all -> 0x0063 }
            r0.connect()     // Catch:{ all -> 0x0063 }
            int r1 = r0.getResponseCode()     // Catch:{ all -> 0x0063 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 < r2) goto L_0x003d
            r2 = 300(0x12c, float:4.2E-43)
            if (r1 < r2) goto L_0x005f
        L_0x003d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0063 }
            r2.<init>()     // Catch:{ all -> 0x0063 }
            java.lang.String r3 = "Did not receive 2XX (got "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0063 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0063 }
            java.lang.String r2 = ") from pinging URL: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0063 }
            java.lang.String r2 = r4.f163b     // Catch:{ all -> 0x0063 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0063 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0063 }
            com.google.ads.util.C0061a.m164e(r1)     // Catch:{ all -> 0x0063 }
        L_0x005f:
            r0.disconnect()     // Catch:{ IOException -> 0x0068 }
        L_0x0062:
            return
        L_0x0063:
            r1 = move-exception
            r0.disconnect()     // Catch:{ IOException -> 0x0068 }
            throw r1     // Catch:{ IOException -> 0x0068 }
        L_0x0068:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unable to ping the URL: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r4.f163b
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.google.ads.util.C0061a.m161b(r1, r0)
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.C0067w.run():void");
    }
}
