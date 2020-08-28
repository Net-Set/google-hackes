package com.google.ads;

import android.content.Context;
import android.location.Location;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdRequest {
    public static final String LOGTAG = "Ads";
    public static final String TEST_EMULATOR = AdUtil.m133a("emulator");
    public static final String VERSION = "4.3.1";

    /* renamed from: a */
    private Gender f12a = null;

    /* renamed from: b */
    private String f13b = null;

    /* renamed from: c */
    private Set<String> f14c = null;

    /* renamed from: d */
    private boolean f15d = false;

    /* renamed from: e */
    private Map<String, Object> f16e = null;

    /* renamed from: f */
    private Location f17f = null;

    /* renamed from: g */
    private boolean f18g = false;

    /* renamed from: h */
    private boolean f19h = false;

    /* renamed from: i */
    private Set<String> f20i = null;

    public enum ErrorCode {
        INVALID_REQUEST("Invalid Google Ad request."),
        NO_FILL("Ad request successful, but no ad returned due to lack of ad inventory."),
        NETWORK_ERROR("A network error occurred."),
        INTERNAL_ERROR("There was an internal error.");
        

        /* renamed from: a */
        private String f22a;

        private ErrorCode(String description) {
            this.f22a = description;
        }

        public final String toString() {
            return this.f22a;
        }
    }

    public enum Gender {
        UNKNOWN,
        MALE,
        FEMALE
    }

    public void setGender(Gender gender) {
        this.f12a = gender;
    }

    public void setBirthday(String birthday) {
        this.f13b = birthday;
    }

    public void setPlusOneOptOut(boolean plusOneOptOut) {
        this.f15d = plusOneOptOut;
    }

    public void setKeywords(Set<String> keywords) {
        this.f14c = keywords;
    }

    public void addKeyword(String keyword) {
        if (this.f14c == null) {
            this.f14c = new HashSet();
        }
        this.f14c.add(keyword);
    }

    public void setExtras(Map<String, Object> extras) {
        this.f16e = extras;
    }

    public void addExtra(String key, Object value) {
        if (this.f16e == null) {
            this.f16e = new HashMap();
        }
        this.f16e.put(key, value);
    }

    public void setLocation(Location location) {
        this.f17f = location;
    }

    public void setTesting(boolean testing) {
        this.f18g = testing;
    }

    public Map<String, Object> getRequestMap(Context context) {
        String str;
        HashMap hashMap = new HashMap();
        if (this.f14c != null) {
            hashMap.put("kw", this.f14c);
        }
        if (this.f12a != null) {
            hashMap.put("cust_gender", Integer.valueOf(this.f12a.ordinal()));
        }
        if (this.f13b != null) {
            hashMap.put("cust_age", this.f13b);
        }
        if (this.f17f != null) {
            hashMap.put("uule", AdUtil.m132a(this.f17f));
        }
        if (this.f18g) {
            hashMap.put("testing", Integer.valueOf(1));
        }
        if (this.f15d) {
            hashMap.put("pto", Integer.valueOf(1));
        } else {
            hashMap.put("cipa", Integer.valueOf(C0030aa.m22a(context) ? 1 : 0));
        }
        if (isTestDevice(context)) {
            hashMap.put("adtest", "on");
        } else if (!this.f19h) {
            if (AdUtil.m147c()) {
                str = "AdRequest.TEST_EMULATOR";
            } else {
                str = "\"" + AdUtil.m131a(context) + "\"";
            }
            C0061a.m162c("To get test ads on this device, call adRequest.addTestDevice(" + str + ");");
            this.f19h = true;
        }
        if (this.f16e != null) {
            hashMap.put("extras", this.f16e);
        }
        return hashMap;
    }

    public void addTestDevice(String testDevice) {
        if (this.f20i == null) {
            this.f20i = new HashSet();
        }
        this.f20i.add(testDevice);
    }

    public void setTestDevices(Set<String> testDevices) {
        this.f20i = testDevices;
    }

    public boolean isTestDevice(Context context) {
        if (this.f20i == null) {
            return false;
        }
        String a = AdUtil.m131a(context);
        if (a != null && this.f20i.contains(a)) {
            return true;
        }
        return false;
    }
}
