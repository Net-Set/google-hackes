package android.support.p000v4.android.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.android.accessibilityservice.AccessibilityServiceInfoCompat */
public class AccessibilityServiceInfoCompat {
    public static final int FEEDBACK_ALL_MASK = -1;
    private static final AccessibilityServiceInfoVersionImpl IMPL;

    /* renamed from: android.support.v4.android.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoIcsImpl */
    static class AccessibilityServiceInfoIcsImpl extends AccessibilityServiceInfoStubImpl {
        AccessibilityServiceInfoIcsImpl() {
        }

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompatIcs.getCanRetrieveWindowContent(info);
        }

        public String getDescription(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompatIcs.getDescription(info);
        }

        public String getId(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompatIcs.getId(info);
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompatIcs.getResolveInfo(info);
        }

        public String getSettingsActivityName(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompatIcs.getSettingsActivityName(info);
        }
    }

    /* renamed from: android.support.v4.android.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoStubImpl */
    static class AccessibilityServiceInfoStubImpl implements AccessibilityServiceInfoVersionImpl {
        AccessibilityServiceInfoStubImpl() {
        }

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
            return false;
        }

        public String getDescription(AccessibilityServiceInfo info) {
            return null;
        }

        public String getId(AccessibilityServiceInfo info) {
            return null;
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
            return null;
        }

        public String getSettingsActivityName(AccessibilityServiceInfo info) {
            return null;
        }
    }

    /* renamed from: android.support.v4.android.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoVersionImpl */
    interface AccessibilityServiceInfoVersionImpl {
        boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo);

        String getDescription(AccessibilityServiceInfo accessibilityServiceInfo);

        String getId(AccessibilityServiceInfo accessibilityServiceInfo);

        ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo);

        String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityServiceInfoIcsImpl();
        } else {
            IMPL = new AccessibilityServiceInfoStubImpl();
        }
    }

    private AccessibilityServiceInfoCompat() {
    }

    public static String getId(AccessibilityServiceInfo info) {
        return IMPL.getId(info);
    }

    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
        return IMPL.getResolveInfo(info);
    }

    public static String getSettingsActivityName(AccessibilityServiceInfo info) {
        return IMPL.getSettingsActivityName(info);
    }

    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
        return IMPL.getCanRetrieveWindowContent(info);
    }

    public static String getDescription(AccessibilityServiceInfo info) {
        return IMPL.getDescription(info);
    }

    public static String feedbackTypeToString(int feedbackType) {
        switch (feedbackType) {
            case -1:
                return "FEEDBACK_ALL";
            case 1:
                return "FEEDBACK_SPOKEN";
            case 2:
                return "FEEDBACK_HAPTIC";
            case 4:
                return "FEEDBACK_AUDIBLE";
            case 8:
                return "FEEDBACK_VISUAL";
            case 16:
                return "FEEDBACK_GENERIC";
            default:
                return null;
        }
    }

    public static String flagToString(int flag) {
        switch (flag) {
            case 1:
                return "DEFAULT";
            default:
                return null;
        }
    }
}
