package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.MenuItem;

/* renamed from: android.support.v4.view.MenuCompat */
public class MenuCompat {
    static final MenuVersionImpl IMPL;

    /* renamed from: android.support.v4.view.MenuCompat$BaseMenuVersionImpl */
    static class BaseMenuVersionImpl implements MenuVersionImpl {
        BaseMenuVersionImpl() {
        }

        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.MenuCompat$HoneycombMenuVersionImpl */
    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        HoneycombMenuVersionImpl() {
        }

        public boolean setShowAsAction(MenuItem item, int actionEnum) {
            MenuCompatHoneycomb.setShowAsAction(item, actionEnum);
            return true;
        }
    }

    /* renamed from: android.support.v4.view.MenuCompat$MenuVersionImpl */
    interface MenuVersionImpl {
        boolean setShowAsAction(MenuItem menuItem, int i);
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombMenuVersionImpl();
        } else {
            IMPL = new BaseMenuVersionImpl();
        }
    }

    public static boolean setShowAsAction(MenuItem item, int actionEnum) {
        return IMPL.setShowAsAction(item, actionEnum);
    }
}
