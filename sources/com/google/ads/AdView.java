package com.google.ads;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.ads.util.AdUtil;
import com.google.ads.util.C0061a;

public class AdView extends RelativeLayout implements C0026Ad {

    /* renamed from: a */
    private C0042d f27a;

    public AdView(Activity activity, AdSize adSize, String adUnitId) {
        super(activity.getApplicationContext());
        m11a((Context) activity, adSize, (AttributeSet) null);
        m12b(activity, adSize, null);
        m7a(activity, adSize, adUnitId);
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m8a(context, attrs);
    }

    public AdView(Context context, AttributeSet attrs, int i) {
        this(context, attrs);
    }

    /* renamed from: a */
    private void m9a(Context context, String str, int i, AdSize adSize, AttributeSet attributeSet) {
        if (getChildCount() == 0) {
            TextView textView = attributeSet == null ? new TextView(context) : new TextView(context, attributeSet);
            textView.setGravity(17);
            textView.setText(str);
            textView.setTextColor(i);
            textView.setBackgroundColor(-16777216);
            LinearLayout linearLayout = attributeSet == null ? new LinearLayout(context) : new LinearLayout(context, attributeSet);
            linearLayout.setGravity(17);
            LinearLayout linearLayout2 = attributeSet == null ? new LinearLayout(context) : new LinearLayout(context, attributeSet);
            linearLayout2.setGravity(17);
            linearLayout2.setBackgroundColor(i);
            int applyDimension = (int) TypedValue.applyDimension(1, (float) adSize.getWidth(), context.getResources().getDisplayMetrics());
            int applyDimension2 = (int) TypedValue.applyDimension(1, (float) adSize.getHeight(), context.getResources().getDisplayMetrics());
            linearLayout.addView(textView, applyDimension - 2, applyDimension2 - 2);
            linearLayout2.addView(linearLayout);
            addView(linearLayout2, applyDimension, applyDimension2);
        }
    }

    /* renamed from: a */
    private boolean m11a(Context context, AdSize adSize, AttributeSet attributeSet) {
        if (AdUtil.m148c(context)) {
            return true;
        }
        m10a(context, "You must have AdActivity declared in AndroidManifest.xml with configChanges.", adSize, attributeSet);
        return false;
    }

    /* renamed from: b */
    private boolean m12b(Context context, AdSize adSize, AttributeSet attributeSet) {
        if (AdUtil.m146b(context)) {
            return true;
        }
        m10a(context, "You must have INTERNET and ACCESS_NETWORK_STATE permissions in AndroidManifest.xml.", adSize, attributeSet);
        return false;
    }

    public void destroy() {
        this.f27a.mo848b();
    }

    /* renamed from: a */
    private void m10a(Context context, String str, AdSize adSize, AttributeSet attributeSet) {
        C0061a.m160b(str);
        m9a(context, str, -65536, adSize, attributeSet);
        if (isInEditMode()) {
            return;
        }
        if (context instanceof Activity) {
            m7a((Activity) context, adSize, "");
        } else {
            C0061a.m160b("AdView was initialized with a Context that wasn't an Activity.");
        }
    }

    /* renamed from: a */
    private void m8a(Context context, AttributeSet attributeSet) {
        AdSize adSize;
        if (attributeSet != null) {
            String attributeValue = attributeSet.getAttributeValue("http://schemas.android.com/apk/lib/com.google.ads", "adSize");
            if (attributeValue == null) {
                m10a(context, "AdView missing required XML attribute \"adSize\".", AdSize.BANNER, attributeSet);
                return;
            }
            if ("BANNER".equals(attributeValue)) {
                adSize = AdSize.BANNER;
            } else if ("IAB_MRECT".equals(attributeValue)) {
                adSize = AdSize.IAB_MRECT;
            } else if ("IAB_BANNER".equals(attributeValue)) {
                adSize = AdSize.IAB_BANNER;
            } else if ("IAB_LEADERBOARD".equals(attributeValue)) {
                adSize = AdSize.IAB_LEADERBOARD;
            } else {
                m10a(context, "Invalid \"adSize\" value in XML layout: " + attributeValue + ".", AdSize.BANNER, attributeSet);
                return;
            }
            String attributeValue2 = attributeSet.getAttributeValue("http://schemas.android.com/apk/lib/com.google.ads", "testDevices");
            if (attributeValue2 != null && attributeValue2.startsWith("@string/")) {
                String substring = attributeValue2.substring("@string/".length());
                String packageName = context.getPackageName();
                TypedValue typedValue = new TypedValue();
                try {
                    getResources().getValue(packageName + ":string/" + substring, typedValue, true);
                    if (typedValue.string != null) {
                        attributeValue2 = typedValue.string.toString();
                    } else {
                        m10a(context, "\"testDevices\" was not a string: \"" + attributeValue2 + "\".", adSize, attributeSet);
                        return;
                    }
                } catch (NotFoundException e) {
                    m10a(context, "Could not find resource for \"testDevices\": \"" + attributeValue2 + "\".", adSize, attributeSet);
                    return;
                }
            }
            String attributeValue3 = attributeSet.getAttributeValue("http://schemas.android.com/apk/lib/com.google.ads", "adUnitId");
            if (attributeValue3 == null) {
                m10a(context, "AdView missing required XML attribute \"adUnitId\".", adSize, attributeSet);
            } else if (isInEditMode()) {
                m9a(context, "Ads by Google", -1, adSize, attributeSet);
            } else {
                if (attributeValue3.startsWith("@string/")) {
                    String substring2 = attributeValue3.substring("@string/".length());
                    String packageName2 = context.getPackageName();
                    TypedValue typedValue2 = new TypedValue();
                    try {
                        getResources().getValue(packageName2 + ":string/" + substring2, typedValue2, true);
                        if (typedValue2.string != null) {
                            attributeValue3 = typedValue2.string.toString();
                        } else {
                            m10a(context, "\"adUnitId\" was not a string: \"" + attributeValue3 + "\".", adSize, attributeSet);
                            return;
                        }
                    } catch (NotFoundException e2) {
                        m10a(context, "Could not find resource for \"adUnitId\": \"" + attributeValue3 + "\".", adSize, attributeSet);
                        return;
                    }
                }
                boolean attributeBooleanValue = attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/lib/com.google.ads", "loadAdOnCreate", false);
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    m11a((Context) activity, adSize, attributeSet);
                    m12b(activity, adSize, attributeSet);
                    m7a(activity, adSize, attributeValue3);
                    if (attributeBooleanValue) {
                        AdRequest adRequest = new AdRequest();
                        if (attributeValue2 != null) {
                            for (String trim : attributeValue2.split(",")) {
                                String trim2 = trim.trim();
                                if (trim2.length() != 0) {
                                    if (trim2.equals("TEST_EMULATOR")) {
                                        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
                                    } else {
                                        adRequest.addTestDevice(trim2);
                                    }
                                }
                            }
                        }
                        String attributeValue4 = attributeSet.getAttributeValue("http://schemas.android.com/apk/lib/com.google.ads", "keywords");
                        if (attributeValue4 != null) {
                            for (String trim3 : attributeValue4.split(",")) {
                                String trim4 = trim3.trim();
                                if (trim4.length() != 0) {
                                    adRequest.addKeyword(trim4);
                                }
                            }
                        }
                        loadAd(adRequest);
                        return;
                    }
                    return;
                }
                C0061a.m160b("AdView was initialized with a Context that wasn't an Activity.");
            }
        }
    }

    /* renamed from: a */
    private void m7a(Activity activity, AdSize adSize, String str) {
        this.f27a = new C0042d(activity, this, adSize, str, false);
        setGravity(17);
        setLayoutParams(new LayoutParams(-2, -2));
        addView(this.f27a.mo856i(), (int) TypedValue.applyDimension(1, (float) adSize.getWidth(), activity.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(1, (float) adSize.getHeight(), activity.getResources().getDisplayMetrics()));
    }

    public boolean isReady() {
        if (this.f27a == null) {
            return false;
        }
        return this.f27a.mo862o();
    }

    public boolean isRefreshing() {
        return this.f27a.mo863p();
    }

    public void loadAd(AdRequest adRequest) {
        if (isRefreshing()) {
            this.f27a.mo850c();
        }
        this.f27a.mo844a(adRequest);
    }

    public void setAdListener(AdListener adListener) {
        this.f27a.mo842a(adListener);
    }

    public void stopLoading() {
        this.f27a.mo873z();
    }
}
