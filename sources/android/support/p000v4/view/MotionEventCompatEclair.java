package android.support.p000v4.view;

import android.view.MotionEvent;

/* renamed from: android.support.v4.view.MotionEventCompatEclair */
class MotionEventCompatEclair {
    MotionEventCompatEclair() {
    }

    public static int findPointerIndex(MotionEvent event, int pointerId) {
        return event.findPointerIndex(pointerId);
    }

    public static int getPointerId(MotionEvent event, int pointerIndex) {
        return event.getPointerId(pointerIndex);
    }

    public static float getX(MotionEvent event, int pointerIndex) {
        return event.getX(pointerIndex);
    }

    public static float getY(MotionEvent event, int pointerIndex) {
        return event.getY(pointerIndex);
    }
}
