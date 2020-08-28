package com.google.ads.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import com.google.ads.AdRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.nio.charset.UnsupportedCharsetException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AdUtil {

    /* renamed from: a */
    public static final int f133a;

    /* renamed from: b */
    private static Boolean f134b = null;

    /* renamed from: c */
    private static String f135c = null;

    /* renamed from: d */
    private static String f136d;

    /* renamed from: e */
    private static String f137e = null;

    /* renamed from: f */
    private static AudioManager f138f;

    /* renamed from: g */
    private static boolean f139g = true;

    /* renamed from: h */
    private static boolean f140h = false;

    /* renamed from: i */
    private static String f141i = null;

    public static class UserActivityReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                AdUtil.m138a(true);
            } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                AdUtil.m138a(false);
            }
        }
    }

    /* renamed from: com.google.ads.util.AdUtil$a */
    public enum C0060a {
        INVALID,
        SPEAKER,
        HEADPHONES,
        VIBRATE,
        EMULATOR,
        OTHER
    }

    static {
        int i;
        try {
            i = Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException e) {
            C0061a.m164e("The Android SDK version couldn't be parsed to an int: " + VERSION.SDK);
            C0061a.m164e("Defaulting to Android SDK version 3.");
            i = 3;
        }
        f133a = i;
    }

    private AdUtil() {
    }

    /* renamed from: a */
    public static String m131a(Context context) {
        String str;
        if (f135c == null) {
            String string = Secure.getString(context.getContentResolver(), "android_id");
            if (string == null || m147c()) {
                str = m133a("emulator");
            } else {
                str = m133a(string);
            }
            if (str == null) {
                return null;
            }
            f135c = str.toUpperCase(Locale.US);
        }
        return f135c;
    }

    /* renamed from: a */
    public static int m128a() {
        if (f133a >= 9) {
            return 6;
        }
        return 0;
    }

    /* renamed from: b */
    public static int m141b() {
        if (f133a >= 9) {
            return 7;
        }
        return 1;
    }

    /* renamed from: a */
    public static int m129a(Context context, DisplayMetrics displayMetrics) {
        if (f133a >= 4) {
            return C0065c.m168a(context, displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /* renamed from: b */
    public static int m142b(Context context, DisplayMetrics displayMetrics) {
        if (f133a >= 4) {
            return C0065c.m169b(context, displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    /* renamed from: b */
    public static boolean m146b(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        if (packageManager.checkPermission("android.permission.INTERNET", packageName) == -1) {
            C0061a.m160b("INTERNET permissions must be enabled in AndroidManifest.xml.");
            return false;
        } else if (packageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", packageName) != -1) {
            return true;
        } else {
            C0061a.m160b("ACCESS_NETWORK_STATE permissions must be enabled in AndroidManifest.xml.");
            return false;
        }
    }

    /* renamed from: a */
    private static boolean m139a(int i, int i2, String str) {
        if ((i & i2) != 0) {
            return true;
        }
        C0061a.m160b("The android:configChanges value of the com.google.ads.AdActivity must include " + str + ".");
        return false;
    }

    /* renamed from: c */
    public static boolean m148c(Context context) {
        if (f134b != null) {
            return f134b.booleanValue();
        }
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(new Intent(context, AdActivity.class), 65536);
        f134b = Boolean.valueOf(true);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            C0061a.m160b("Could not find com.google.ads.AdActivity, please make sure it is registered in AndroidManifest.xml.");
            f134b = Boolean.valueOf(false);
        } else {
            if (!m139a(resolveActivity.activityInfo.configChanges, 16, "keyboard")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, 32, "keyboardHidden")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER, "orientation")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT, "screenLayout")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START, "uiMode")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END, "screenSize")) {
                f134b = Boolean.valueOf(false);
            }
            if (!m139a(resolveActivity.activityInfo.configChanges, AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED, "smallestScreenSize")) {
                f134b = Boolean.valueOf(false);
            }
        }
        return f134b.booleanValue();
    }

    /* renamed from: c */
    public static boolean m147c() {
        return "unknown".equals(Build.BOARD) && "generic".equals(Build.DEVICE) && "generic".equals(Build.BRAND);
    }

    /* renamed from: a */
    public static boolean m140a(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    /* renamed from: a */
    public static String m133a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes(), 0, str.length());
            return String.format("%032X", new Object[]{new BigInteger(1, instance.digest())});
        } catch (NoSuchAlgorithmException e) {
            return str.substring(0, 32);
        }
    }

    /* renamed from: d */
    public static String m149d(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        switch (activeNetworkInfo.getType()) {
            case 0:
                return "ed";
            case 1:
                return "wi";
            default:
                return "unknown";
        }
    }

    /* renamed from: e */
    public static String m151e(Context context) {
        if (f136d == null) {
            StringBuilder sb = new StringBuilder();
            PackageManager packageManager = context.getPackageManager();
            List queryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=donuts")), 65536);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                sb.append(AdActivity.TYPE_PARAM);
            }
            List queryIntentActivities2 = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pname:com.google")), 65536);
            if (queryIntentActivities2 == null || queryIntentActivities2.size() == 0) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("a");
            }
            List queryIntentActivities3 = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("tel://6509313940")), 65536);
            if (queryIntentActivities3 == null || queryIntentActivities3.size() == 0) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("t");
            }
            f136d = sb.toString();
        }
        return f136d;
    }

    /* renamed from: f */
    public static String m152f(Context context) {
        if (f137e != null) {
            return f137e;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveActivity = packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.ads")), 0);
            if (resolveActivity == null) {
                return null;
            }
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            if (activityInfo == null) {
                return null;
            }
            PackageInfo packageInfo = packageManager.getPackageInfo(activityInfo.packageName, 0);
            if (packageInfo == null) {
                return null;
            }
            String str = packageInfo.versionCode + "." + activityInfo.packageName;
            f137e = str;
            return str;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /* renamed from: g */
    public static C0060a m153g(Context context) {
        if (f138f == null) {
            f138f = (AudioManager) context.getSystemService("audio");
        }
        C0060a aVar = C0060a.OTHER;
        int mode = f138f.getMode();
        if (m147c()) {
            return C0060a.EMULATOR;
        }
        if (f138f.isMusicActive() || f138f.isSpeakerphoneOn() || mode == 2 || mode == 1) {
            return C0060a.VIBRATE;
        }
        int ringerMode = f138f.getRingerMode();
        if (ringerMode == 0 || ringerMode == 1) {
            return C0060a.VIBRATE;
        }
        return C0060a.SPEAKER;
    }

    /* renamed from: a */
    public static DisplayMetrics m130a(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /* renamed from: a */
    public static String m132a(Location location) {
        if (location == null) {
            return null;
        }
        return "e1+" + m143b(String.format("role: 6 producer: 24 historical_role: 1 historical_producer: 12 timestamp: %d latlng < latitude_e7: %d longitude_e7: %d> radius: %d", new Object[]{Long.valueOf(location.getTime() * 1000), Long.valueOf((long) (location.getLatitude() * 1.0E7d)), Long.valueOf((long) (location.getLongitude() * 1.0E7d)), Long.valueOf((long) (location.getAccuracy() * 1000.0f))}));
    }

    /* renamed from: b */
    private static String m143b(String str) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, new SecretKeySpec(new byte[]{10, 55, -112, -47, -6, 7, 11, 75, -7, -121, 121, 69, 80, -61, 15, 5}, "AES"));
            byte[] iv = instance.getIV();
            byte[] doFinal = instance.doFinal(str.getBytes());
            byte[] bArr = new byte[(iv.length + doFinal.length)];
            System.arraycopy(iv, 0, bArr, 0, iv.length);
            System.arraycopy(doFinal, 0, bArr, iv.length, doFinal.length);
            return C0062b.m165a(bArr);
        } catch (GeneralSecurityException e) {
            return null;
        }
    }

    /* renamed from: b */
    public static HashMap<String, String> m144b(Uri uri) {
        String[] split;
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String encodedQuery = uri.getEncodedQuery();
        if (encodedQuery == null) {
            return hashMap;
        }
        try {
            for (String str : encodedQuery.split("&")) {
                int indexOf = str.indexOf(61);
                if (indexOf == -1) {
                    return null;
                }
                hashMap.put(URLDecoder.decode(str.substring(0, indexOf), "utf-8"), URLDecoder.decode(str.substring(indexOf + 1), "utf-8"));
            }
            return hashMap;
        } catch (UnsupportedEncodingException e) {
            C0061a.m158a((Throwable) e);
            return null;
        } catch (UnsupportedCharsetException e2) {
            C0061a.m158a((Throwable) e2);
            return null;
        } catch (IllegalArgumentException e3) {
            C0061a.m158a((Throwable) e3);
            return null;
        }
    }

    /* renamed from: d */
    public static boolean m150d() {
        return f139g;
    }

    /* renamed from: a */
    public static void m138a(boolean z) {
        f139g = z;
    }

    /* renamed from: h */
    public static void m154h(Context context) {
        if (!f140h) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(new UserActivityReceiver(), intentFilter);
            f140h = true;
        }
    }

    /* renamed from: i */
    private static String m155i(Context context) {
        if (f141i == null) {
            String userAgentString = new WebView(context).getSettings().getUserAgentString();
            if (userAgentString == null || userAgentString.length() == 0 || userAgentString.equals("Java0")) {
                String property = System.getProperty("os.name", "Linux");
                String str = "Android " + VERSION.RELEASE;
                Locale locale = Locale.getDefault();
                String lowerCase = locale.getLanguage().toLowerCase(Locale.US);
                if (lowerCase.length() == 0) {
                    lowerCase = "en";
                }
                String lowerCase2 = locale.getCountry().toLowerCase(Locale.US);
                if (lowerCase2.length() > 0) {
                    lowerCase = lowerCase + "-" + lowerCase2;
                }
                userAgentString = "Mozilla/5.0 (" + property + "; U; " + str + "; " + lowerCase + "; " + (Build.MODEL + " Build/" + Build.ID) + ") AppleWebKit/0.0 (KHTML, like " + "Gecko) Version/0.0 Mobile Safari/0.0";
            }
            f141i = userAgentString + " (Mobile; " + "afma-sdk-a-v" + AdRequest.VERSION + ")";
        }
        return f141i;
    }

    /* renamed from: a */
    public static void m136a(WebView webView) {
        webView.getSettings().setUserAgentString(m155i(webView.getContext().getApplicationContext()));
    }

    /* renamed from: a */
    public static void m137a(HttpURLConnection httpURLConnection, Context context) {
        httpURLConnection.setRequestProperty("User-Agent", m155i(context));
    }

    /* renamed from: a */
    public static String m134a(Map<String, Object> map) {
        boolean z = false;
        try {
            return m145b(map).toString();
        } catch (JSONException e) {
            C0061a.m161b("JsonException in serialization: ", e);
            return z;
        }
    }

    /* renamed from: b */
    private static JSONObject m145b(Map<String, Object> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map == null || map.isEmpty()) {
            return jSONObject;
        }
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if ((obj instanceof String) || (obj instanceof Integer) || (obj instanceof Double) || (obj instanceof Long) || (obj instanceof Float)) {
                jSONObject.put(str, obj);
            } else if (obj instanceof Map) {
                try {
                    jSONObject.put(str, m145b((Map) obj));
                } catch (ClassCastException e) {
                    C0061a.m161b("Unknown map type in json serialization: ", e);
                }
            } else if (obj instanceof Set) {
                try {
                    jSONObject.put(str, m135a((Set) obj));
                } catch (ClassCastException e2) {
                    C0061a.m161b("Unknown map type in json serialization: ", e2);
                }
            } else {
                C0061a.m164e("Unknown value in json serialization: " + obj);
            }
        }
        return jSONObject;
    }

    /* renamed from: a */
    private static JSONArray m135a(Set<Object> set) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        if (set == null || set.isEmpty()) {
            return jSONArray;
        }
        for (Object next : set) {
            if ((next instanceof String) || (next instanceof Integer) || (next instanceof Double) || (next instanceof Long) || (next instanceof Float)) {
                jSONArray.put(next);
            } else if (next instanceof Map) {
                try {
                    jSONArray.put(m145b((Map) next));
                } catch (ClassCastException e) {
                    C0061a.m161b("Unknown map type in json serialization: ", e);
                }
            } else if (next instanceof Set) {
                try {
                    jSONArray.put(m135a((Set) next));
                } catch (ClassCastException e2) {
                    C0061a.m161b("Unknown map type in json serialization: ", e2);
                }
            } else {
                C0061a.m164e("Unknown value in json serialization: " + next);
            }
        }
        return jSONArray;
    }
}
