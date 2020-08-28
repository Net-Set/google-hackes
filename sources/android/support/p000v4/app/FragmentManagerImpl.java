package android.support.p000v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.p000v4.app.Fragment.SavedState;
import android.support.p000v4.app.FragmentManager.BackStackEntry;
import android.support.p000v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.p000v4.util.DebugUtils;
import android.support.p000v4.util.LogWriter;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: android.support.v4.app.FragmentManagerImpl */
/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = HONEYCOMB;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String VIEW_STATE_TAG = "android:view_state";
    ArrayList<Fragment> mActive;
    FragmentActivity mActivity;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    ArrayList<Runnable> mPendingActions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;

    FragmentManagerImpl() {
    }

    static {
        boolean z = HONEYCOMB;
        if (VERSION.SDK_INT >= 11) {
            z = true;
        }
        HONEYCOMB = z;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        return execPendingActions();
    }

    public void popBackStack() {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, null, -1, 0);
            }
        }, HONEYCOMB);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mActivity.mHandler, null, -1, 0);
    }

    public void popBackStack(final String name, final int flags) {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, name, -1, flags);
            }
        }, HONEYCOMB);
    }

    public boolean popBackStackImmediate(String name, int flags) {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mActivity.mHandler, name, -1, flags);
    }

    public void popBackStack(final int id, final int flags) {
        if (id < 0) {
            throw new IllegalArgumentException("Bad id: " + id);
        }
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, null, id, flags);
            }
        }, HONEYCOMB);
    }

    public boolean popBackStackImmediate(int id, int flags) {
        checkStateLoss();
        executePendingTransactions();
        if (id >= 0) {
            return popBackStackState(this.mActivity.mHandler, null, id, flags);
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    public BackStackEntry getBackStackEntryAt(int index) {
        return (BackStackEntry) this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(listener);
        }
    }

    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throw new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager");
        }
        bundle.putInt(key, fragment.mIndex);
    }

    public Fragment getFragment(Bundle bundle, String key) {
        int index = bundle.getInt(key, -1);
        if (index == -1) {
            return null;
        }
        if (index >= this.mActive.size()) {
            throw new IllegalStateException("Fragement no longer exists for key " + key + ": index " + index);
        }
        Fragment f = (Fragment) this.mActive.get(index);
        if (f != null) {
            return f;
        }
        throw new IllegalStateException("Fragement no longer exists for key " + key + ": index " + index);
    }

    public SavedState saveFragmentInstanceState(Fragment fragment) {
        if (fragment.mIndex < 0) {
            throw new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager");
        } else if (fragment.mState <= 0) {
            return null;
        } else {
            Bundle result = saveFragmentBasicState(fragment);
            if (result != null) {
                return new SavedState(result);
            }
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        DebugUtils.buildShortClassTag(this.mActivity, sb);
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "    ";
        if (this.mActive != null) {
            int N = this.mActive.size();
            if (N > 0) {
                writer.print(prefix);
                writer.print("Active Fragments in ");
                writer.print(Integer.toHexString(System.identityHashCode(this)));
                writer.println(":");
                for (int i = 0; i < N; i++) {
                    Fragment f = (Fragment) this.mActive.get(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(f);
                    if (f != null) {
                        f.dump(innerPrefix, fd, writer, args);
                    }
                }
            }
        }
        if (this.mAdded != null) {
            int N2 = this.mAdded.size();
            if (N2 > 0) {
                writer.print(prefix);
                writer.println("Added Fragments:");
                for (int i2 = 0; i2 < N2; i2++) {
                    Fragment f2 = (Fragment) this.mAdded.get(i2);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i2);
                    writer.print(": ");
                    writer.println(f2.toString());
                }
            }
        }
        if (this.mCreatedMenus != null) {
            int N3 = this.mCreatedMenus.size();
            if (N3 > 0) {
                writer.print(prefix);
                writer.println("Fragments Created Menus:");
                for (int i3 = 0; i3 < N3; i3++) {
                    Fragment f3 = (Fragment) this.mCreatedMenus.get(i3);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i3);
                    writer.print(": ");
                    writer.println(f3.toString());
                }
            }
        }
        if (this.mBackStack != null) {
            int N4 = this.mBackStack.size();
            if (N4 > 0) {
                writer.print(prefix);
                writer.println("Back Stack:");
                for (int i4 = 0; i4 < N4; i4++) {
                    BackStackRecord bs = (BackStackRecord) this.mBackStack.get(i4);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i4);
                    writer.print(": ");
                    writer.println(bs.toString());
                    bs.dump(innerPrefix, fd, writer, args);
                }
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null) {
                int N5 = this.mBackStackIndices.size();
                if (N5 > 0) {
                    writer.print(prefix);
                    writer.println("Back Stack Indices:");
                    for (int i5 = 0; i5 < N5; i5++) {
                        BackStackRecord bs2 = (BackStackRecord) this.mBackStackIndices.get(i5);
                        writer.print(prefix);
                        writer.print("  #");
                        writer.print(i5);
                        writer.print(": ");
                        writer.println(bs2);
                    }
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                writer.print(prefix);
                writer.print("mAvailBackStackIndices: ");
                writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null) {
            int N6 = this.mPendingActions.size();
            if (N6 > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                for (int i6 = 0; i6 < N6; i6++) {
                    Runnable r = (Runnable) this.mPendingActions.get(i6);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i6);
                    writer.print(": ");
                    writer.println(r);
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            writer.print(prefix);
            writer.print("  mNoTransactionsBecause=");
            writer.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
            writer.print(prefix);
            writer.print("  mAvailIndices: ");
            writer.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    static Animation makeOpenCloseAnimation(Context context, float startScale, float endScale, float startAlpha, float endAlpha) {
        AnimationSet set = new AnimationSet(HONEYCOMB);
        ScaleAnimation scale = new ScaleAnimation(startScale, endScale, startScale, endScale, 1, 0.5f, 1, 0.5f);
        scale.setInterpolator(DECELERATE_QUINT);
        scale.setDuration(220);
        set.addAnimation(scale);
        AlphaAnimation alpha = new AlphaAnimation(startAlpha, endAlpha);
        alpha.setInterpolator(DECELERATE_CUBIC);
        alpha.setDuration(220);
        set.addAnimation(alpha);
        return set;
    }

    static Animation makeFadeAnimation(Context context, float start, float end) {
        AlphaAnimation anim = new AlphaAnimation(start, end);
        anim.setInterpolator(DECELERATE_CUBIC);
        anim.setDuration(220);
        return anim;
    }

    /* access modifiers changed from: 0000 */
    public Animation loadAnimation(Fragment fragment, int transit, boolean enter, int transitionStyle) {
        Animation animObj = fragment.onCreateAnimation(transit, enter, fragment.mNextAnim);
        if (animObj != null) {
            return animObj;
        }
        if (fragment.mNextAnim != 0) {
            Animation anim = AnimationUtils.loadAnimation(this.mActivity, fragment.mNextAnim);
            if (anim != null) {
                return anim;
            }
        }
        if (transit == 0) {
            return null;
        }
        int styleIndex = transitToStyleIndex(transit, enter);
        if (styleIndex < 0) {
            return null;
        }
        switch (styleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mActivity, 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mActivity, 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mActivity, 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mActivity, 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mActivity, 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mActivity, 1.0f, 0.0f);
            default:
                if (transitionStyle == 0 && this.mActivity.getWindow() != null) {
                    transitionStyle = this.mActivity.getWindow().getAttributes().windowAnimations;
                }
                if (transitionStyle == 0) {
                    return null;
                }
                return null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x026e, code lost:
        if (r8.mCalled != false) goto L_0x028f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x028e, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onResume()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x028f, code lost:
        r8.mSavedFragmentState = null;
        r8.mSavedViewState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02a3, code lost:
        if (r9 >= 1) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02a7, code lost:
        if (r7.mDestroyed == false) goto L_0x02b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02ab, code lost:
        if (r8.mAnimatingAway == null) goto L_0x02b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02ad, code lost:
        r3 = r8.mAnimatingAway;
        r8.mAnimatingAway = null;
        r3.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x02b7, code lost:
        if (r8.mAnimatingAway == null) goto L_0x040c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x02b9, code lost:
        r8.mStateAfterAnimating = r9;
        r9 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x030a, code lost:
        if (r9 >= 4) goto L_0x0351;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x030e, code lost:
        if (DEBUG == false) goto L_0x0328;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0310, code lost:
        android.util.Log.v(TAG, "movefrom STARTED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0328, code lost:
        r8.mCalled = HONEYCOMB;
        r8.performStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0330, code lost:
        if (r8.mCalled != false) goto L_0x0351;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0350, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onStop()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0352, code lost:
        if (r9 >= 3) goto L_0x0373;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0356, code lost:
        if (DEBUG == false) goto L_0x0370;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0358, code lost:
        android.util.Log.v(TAG, "movefrom STOPPED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0370, code lost:
        r8.performReallyStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0374, code lost:
        if (r9 >= 2) goto L_0x02a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0378, code lost:
        if (DEBUG == false) goto L_0x0392;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x037a, code lost:
        android.util.Log.v(TAG, "movefrom ACTIVITY_CREATED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0394, code lost:
        if (r8.mView == null) goto L_0x03a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x039c, code lost:
        if (r7.mActivity.isFinishing() != false) goto L_0x03a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x03a0, code lost:
        if (r8.mSavedViewState != null) goto L_0x03a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x03a2, code lost:
        saveFragmentViewState(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x03a5, code lost:
        r8.mCalled = HONEYCOMB;
        r8.performDestroyView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x03ad, code lost:
        if (r8.mCalled != false) goto L_0x03ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03cd, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onDestroyView()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x03d0, code lost:
        if (r8.mView == null) goto L_0x0401;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x03d4, code lost:
        if (r8.mContainer == null) goto L_0x0401;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x03d6, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x03d9, code lost:
        if (r7.mCurState <= 0) goto L_0x03e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x03dd, code lost:
        if (r7.mDestroyed != false) goto L_0x03e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x03df, code lost:
        r0 = loadAnimation(r8, r10, HONEYCOMB, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x03e4, code lost:
        if (r0 == null) goto L_0x03fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x03e6, code lost:
        r2 = r8;
        r8.mAnimatingAway = r8.mView;
        r8.mStateAfterAnimating = r9;
        r0.setAnimationListener(new android.support.p000v4.app.FragmentManagerImpl.C00085(r7));
        r8.mView.startAnimation(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03fa, code lost:
        r8.mContainer.removeView(r8.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0401, code lost:
        r8.mContainer = null;
        r8.mView = null;
        r8.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x040e, code lost:
        if (DEBUG == false) goto L_0x0428;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0410, code lost:
        android.util.Log.v(TAG, "movefrom CREATED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x042a, code lost:
        if (r8.mRetaining != false) goto L_0x0455;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x042c, code lost:
        r8.mCalled = HONEYCOMB;
        r8.onDestroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0434, code lost:
        if (r8.mCalled != false) goto L_0x0455;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0454, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onDestroy()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x0455, code lost:
        r8.mCalled = HONEYCOMB;
        r8.onDetach();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x045d, code lost:
        if (r8.mCalled != false) goto L_0x047e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x047d, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onDetach()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x0480, code lost:
        if (r8.mRetaining != false) goto L_0x0487;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x0482, code lost:
        makeInactive(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x0487, code lost:
        r8.mActivity = null;
        r8.mFragmentManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x011a, code lost:
        if (r9 <= 1) goto L_0x01fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x011e, code lost:
        if (DEBUG == false) goto L_0x0138;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0120, code lost:
        android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x013a, code lost:
        if (r8.mFromLayout != false) goto L_0x01c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x013c, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x013f, code lost:
        if (r8.mContainerId == 0) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0141, code lost:
        r1 = (android.view.ViewGroup) r7.mActivity.findViewById(r8.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x014b, code lost:
        if (r1 != null) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x014f, code lost:
        if (r8.mRestored != false) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0179, code lost:
        throw new java.lang.IllegalArgumentException("No view found for id 0x" + java.lang.Integer.toHexString(r8.mContainerId) + " for fragment " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x017e, code lost:
        r8.mContainer = r1;
        r8.mView = r8.onCreateView(r8.getLayoutInflater(r8.mSavedFragmentState), r1, r8.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0190, code lost:
        if (r8.mView == null) goto L_0x01ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0192, code lost:
        r8.mInnerView = r8.mView;
        r8.mView = android.support.p000v4.app.NoSaveStateFrameLayout.wrap(r8.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x019e, code lost:
        if (r1 == null) goto L_0x01b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01a0, code lost:
        r0 = loadAnimation(r8, r10, true, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01a5, code lost:
        if (r0 == null) goto L_0x01ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01a7, code lost:
        r8.mView.startAnimation(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01ac, code lost:
        r1.addView(r8.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01b3, code lost:
        if (r8.mHidden == false) goto L_0x01bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b5, code lost:
        r8.mView.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01bc, code lost:
        r8.onViewCreated(r8.mView, r8.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01c3, code lost:
        r8.mCalled = HONEYCOMB;
        r8.onActivityCreated(r8.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01cd, code lost:
        if (r8.mCalled != false) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01ed, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onActivityCreated()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ee, code lost:
        r8.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01f4, code lost:
        if (r8.mView == null) goto L_0x01f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01f6, code lost:
        r8.restoreViewState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01f9, code lost:
        r8.mSavedFragmentState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01fd, code lost:
        if (r9 <= 3) goto L_0x0244;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0201, code lost:
        if (DEBUG == false) goto L_0x021b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0203, code lost:
        android.util.Log.v(TAG, "moveto STARTED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x021b, code lost:
        r8.mCalled = HONEYCOMB;
        r8.performStart();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0223, code lost:
        if (r8.mCalled != false) goto L_0x0244;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0243, code lost:
        throw new android.support.p000v4.app.SuperNotCalledException("Fragment " + r8 + " did not call through to super.onStart()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0245, code lost:
        if (r9 <= 4) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0249, code lost:
        if (DEBUG == false) goto L_0x0263;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x024b, code lost:
        android.util.Log.v(TAG, "moveto RESUMED: " + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0263, code lost:
        r8.mCalled = HONEYCOMB;
        r8.mResumed = true;
        r8.onResume();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(android.support.p000v4.app.Fragment r8, int r9, int r10, int r11) {
        /*
            r7 = this;
            boolean r4 = r8.mAdded
            if (r4 != 0) goto L_0x0008
            r4 = 1
            if (r9 <= r4) goto L_0x0008
            r9 = 1
        L_0x0008:
            boolean r4 = r8.mRemoving
            if (r4 == 0) goto L_0x0012
            int r4 = r8.mState
            if (r9 <= r4) goto L_0x0012
            int r9 = r8.mState
        L_0x0012:
            int r4 = r8.mState
            if (r4 >= r9) goto L_0x0297
            boolean r4 = r8.mFromLayout
            if (r4 == 0) goto L_0x001f
            boolean r4 = r8.mInLayout
            if (r4 != 0) goto L_0x001f
        L_0x001e:
            return
        L_0x001f:
            android.view.View r4 = r8.mAnimatingAway
            if (r4 == 0) goto L_0x002d
            r4 = 0
            r8.mAnimatingAway = r4
            int r4 = r8.mStateAfterAnimating
            r5 = 0
            r6 = 0
            r7.moveToState(r8, r4, r5, r6)
        L_0x002d:
            int r4 = r8.mState
            switch(r4) {
                case 0: goto L_0x0035;
                case 1: goto L_0x0119;
                case 2: goto L_0x01fc;
                case 3: goto L_0x01fc;
                case 4: goto L_0x0244;
                default: goto L_0x0032;
            }
        L_0x0032:
            r8.mState = r9
            goto L_0x001e
        L_0x0035:
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0051
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "moveto CREATED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0051:
            android.os.Bundle r4 = r8.mSavedFragmentState
            if (r4 == 0) goto L_0x0078
            android.os.Bundle r4 = r8.mSavedFragmentState
            java.lang.String r5 = "android:view_state"
            android.util.SparseArray r4 = r4.getSparseParcelableArray(r5)
            r8.mSavedViewState = r4
            android.os.Bundle r4 = r8.mSavedFragmentState
            java.lang.String r5 = "android:target_state"
            android.support.v4.app.Fragment r4 = r7.getFragment(r4, r5)
            r8.mTarget = r4
            android.support.v4.app.Fragment r4 = r8.mTarget
            if (r4 == 0) goto L_0x0078
            android.os.Bundle r4 = r8.mSavedFragmentState
            java.lang.String r5 = "android:target_req_state"
            r6 = 0
            int r4 = r4.getInt(r5, r6)
            r8.mTargetRequestCode = r4
        L_0x0078:
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            r8.mActivity = r4
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            android.support.v4.app.FragmentManagerImpl r4 = r4.mFragments
            r8.mFragmentManager = r4
            r4 = 0
            r8.mCalled = r4
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            r8.onAttach(r4)
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x00ad
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onAttach()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x00ad:
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            r4.onAttachFragment(r8)
            boolean r4 = r8.mRetaining
            if (r4 != 0) goto L_0x00e1
            r4 = 0
            r8.mCalled = r4
            android.os.Bundle r4 = r8.mSavedFragmentState
            r8.onCreate(r4)
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x00e1
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onCreate()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x00e1:
            r4 = 0
            r8.mRetaining = r4
            boolean r4 = r8.mFromLayout
            if (r4 == 0) goto L_0x0119
            android.os.Bundle r4 = r8.mSavedFragmentState
            android.view.LayoutInflater r4 = r8.getLayoutInflater(r4)
            r5 = 0
            android.os.Bundle r6 = r8.mSavedFragmentState
            android.view.View r4 = r8.onCreateView(r4, r5, r6)
            r8.mView = r4
            android.view.View r4 = r8.mView
            if (r4 == 0) goto L_0x017a
            android.view.View r4 = r8.mView
            r8.mInnerView = r4
            android.view.View r4 = r8.mView
            android.view.ViewGroup r4 = android.support.p000v4.app.NoSaveStateFrameLayout.wrap(r4)
            r8.mView = r4
            boolean r4 = r8.mHidden
            if (r4 == 0) goto L_0x0112
            android.view.View r4 = r8.mView
            r5 = 8
            r4.setVisibility(r5)
        L_0x0112:
            android.view.View r4 = r8.mView
            android.os.Bundle r5 = r8.mSavedFragmentState
            r8.onViewCreated(r4, r5)
        L_0x0119:
            r4 = 1
            if (r9 <= r4) goto L_0x01fc
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0138
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "moveto ACTIVITY_CREATED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0138:
            boolean r4 = r8.mFromLayout
            if (r4 != 0) goto L_0x01c3
            r1 = 0
            int r4 = r8.mContainerId
            if (r4 == 0) goto L_0x017e
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            int r5 = r8.mContainerId
            android.view.View r1 = r4.findViewById(r5)
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            if (r1 != 0) goto L_0x017e
            boolean r4 = r8.mRestored
            if (r4 != 0) goto L_0x017e
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "No view found for id 0x"
            java.lang.StringBuilder r5 = r5.append(r6)
            int r6 = r8.mContainerId
            java.lang.String r6 = java.lang.Integer.toHexString(r6)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = " for fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x017a:
            r4 = 0
            r8.mInnerView = r4
            goto L_0x0119
        L_0x017e:
            r8.mContainer = r1
            android.os.Bundle r4 = r8.mSavedFragmentState
            android.view.LayoutInflater r4 = r8.getLayoutInflater(r4)
            android.os.Bundle r5 = r8.mSavedFragmentState
            android.view.View r4 = r8.onCreateView(r4, r1, r5)
            r8.mView = r4
            android.view.View r4 = r8.mView
            if (r4 == 0) goto L_0x01ee
            android.view.View r4 = r8.mView
            r8.mInnerView = r4
            android.view.View r4 = r8.mView
            android.view.ViewGroup r4 = android.support.p000v4.app.NoSaveStateFrameLayout.wrap(r4)
            r8.mView = r4
            if (r1 == 0) goto L_0x01b1
            r4 = 1
            android.view.animation.Animation r0 = r7.loadAnimation(r8, r10, r4, r11)
            if (r0 == 0) goto L_0x01ac
            android.view.View r4 = r8.mView
            r4.startAnimation(r0)
        L_0x01ac:
            android.view.View r4 = r8.mView
            r1.addView(r4)
        L_0x01b1:
            boolean r4 = r8.mHidden
            if (r4 == 0) goto L_0x01bc
            android.view.View r4 = r8.mView
            r5 = 8
            r4.setVisibility(r5)
        L_0x01bc:
            android.view.View r4 = r8.mView
            android.os.Bundle r5 = r8.mSavedFragmentState
            r8.onViewCreated(r4, r5)
        L_0x01c3:
            r4 = 0
            r8.mCalled = r4
            android.os.Bundle r4 = r8.mSavedFragmentState
            r8.onActivityCreated(r4)
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x01f2
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onActivityCreated()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x01ee:
            r4 = 0
            r8.mInnerView = r4
            goto L_0x01c3
        L_0x01f2:
            android.view.View r4 = r8.mView
            if (r4 == 0) goto L_0x01f9
            r8.restoreViewState()
        L_0x01f9:
            r4 = 0
            r8.mSavedFragmentState = r4
        L_0x01fc:
            r4 = 3
            if (r9 <= r4) goto L_0x0244
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x021b
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "moveto STARTED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x021b:
            r4 = 0
            r8.mCalled = r4
            r8.performStart()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x0244
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onStart()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0244:
            r4 = 4
            if (r9 <= r4) goto L_0x0032
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0263
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "moveto RESUMED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0263:
            r4 = 0
            r8.mCalled = r4
            r4 = 1
            r8.mResumed = r4
            r8.onResume()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x028f
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onResume()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x028f:
            r4 = 0
            r8.mSavedFragmentState = r4
            r4 = 0
            r8.mSavedViewState = r4
            goto L_0x0032
        L_0x0297:
            int r4 = r8.mState
            if (r4 <= r9) goto L_0x0032
            int r4 = r8.mState
            switch(r4) {
                case 1: goto L_0x02a2;
                case 2: goto L_0x0373;
                case 3: goto L_0x0351;
                case 4: goto L_0x0309;
                case 5: goto L_0x02be;
                default: goto L_0x02a0;
            }
        L_0x02a0:
            goto L_0x0032
        L_0x02a2:
            r4 = 1
            if (r9 >= r4) goto L_0x0032
            boolean r4 = r7.mDestroyed
            if (r4 == 0) goto L_0x02b5
            android.view.View r4 = r8.mAnimatingAway
            if (r4 == 0) goto L_0x02b5
            android.view.View r3 = r8.mAnimatingAway
            r4 = 0
            r8.mAnimatingAway = r4
            r3.clearAnimation()
        L_0x02b5:
            android.view.View r4 = r8.mAnimatingAway
            if (r4 == 0) goto L_0x040c
            r8.mStateAfterAnimating = r9
            r9 = 1
            goto L_0x0032
        L_0x02be:
            r4 = 5
            if (r9 >= r4) goto L_0x0309
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x02dd
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "movefrom RESUMED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x02dd:
            r4 = 0
            r8.mCalled = r4
            r8.onPause()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x0306
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onPause()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0306:
            r4 = 0
            r8.mResumed = r4
        L_0x0309:
            r4 = 4
            if (r9 >= r4) goto L_0x0351
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0328
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "movefrom STARTED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0328:
            r4 = 0
            r8.mCalled = r4
            r8.performStop()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x0351
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onStop()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0351:
            r4 = 3
            if (r9 >= r4) goto L_0x0373
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0370
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "movefrom STOPPED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0370:
            r8.performReallyStop()
        L_0x0373:
            r4 = 2
            if (r9 >= r4) goto L_0x02a2
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0392
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "movefrom ACTIVITY_CREATED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0392:
            android.view.View r4 = r8.mView
            if (r4 == 0) goto L_0x03a5
            android.support.v4.app.FragmentActivity r4 = r7.mActivity
            boolean r4 = r4.isFinishing()
            if (r4 != 0) goto L_0x03a5
            android.util.SparseArray<android.os.Parcelable> r4 = r8.mSavedViewState
            if (r4 != 0) goto L_0x03a5
            r7.saveFragmentViewState(r8)
        L_0x03a5:
            r4 = 0
            r8.mCalled = r4
            r8.performDestroyView()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x03ce
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onDestroyView()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x03ce:
            android.view.View r4 = r8.mView
            if (r4 == 0) goto L_0x0401
            android.view.ViewGroup r4 = r8.mContainer
            if (r4 == 0) goto L_0x0401
            r0 = 0
            int r4 = r7.mCurState
            if (r4 <= 0) goto L_0x03e4
            boolean r4 = r7.mDestroyed
            if (r4 != 0) goto L_0x03e4
            r4 = 0
            android.view.animation.Animation r0 = r7.loadAnimation(r8, r10, r4, r11)
        L_0x03e4:
            if (r0 == 0) goto L_0x03fa
            r2 = r8
            android.view.View r4 = r8.mView
            r8.mAnimatingAway = r4
            r8.mStateAfterAnimating = r9
            android.support.v4.app.FragmentManagerImpl$5 r4 = new android.support.v4.app.FragmentManagerImpl$5
            r4.<init>(r2)
            r0.setAnimationListener(r4)
            android.view.View r4 = r8.mView
            r4.startAnimation(r0)
        L_0x03fa:
            android.view.ViewGroup r4 = r8.mContainer
            android.view.View r5 = r8.mView
            r4.removeView(r5)
        L_0x0401:
            r4 = 0
            r8.mContainer = r4
            r4 = 0
            r8.mView = r4
            r4 = 0
            r8.mInnerView = r4
            goto L_0x02a2
        L_0x040c:
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0428
            java.lang.String r4 = "FragmentManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "movefrom CREATED: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r4, r5)
        L_0x0428:
            boolean r4 = r8.mRetaining
            if (r4 != 0) goto L_0x0455
            r4 = 0
            r8.mCalled = r4
            r8.onDestroy()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x0455
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onDestroy()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0455:
            r4 = 0
            r8.mCalled = r4
            r8.onDetach()
            boolean r4 = r8.mCalled
            if (r4 != 0) goto L_0x047e
            android.support.v4.app.SuperNotCalledException r4 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Fragment "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " did not call through to super.onDetach()"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x047e:
            boolean r4 = r8.mRetaining
            if (r4 != 0) goto L_0x0487
            r7.makeInactive(r8)
            goto L_0x0032
        L_0x0487:
            r4 = 0
            r8.mActivity = r4
            r4 = 0
            r8.mFragmentManager = r4
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int):void");
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(Fragment f) {
        moveToState(f, this.mCurState, 0, 0);
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(int newState, boolean always) {
        moveToState(newState, 0, 0, always);
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(int newState, int transit, int transitStyle, boolean always) {
        if (this.mActivity == null && newState != 0) {
            throw new IllegalStateException("No activity");
        } else if (always || this.mCurState != newState) {
            this.mCurState = newState;
            if (this.mActive != null) {
                for (int i = 0; i < this.mActive.size(); i++) {
                    Fragment f = (Fragment) this.mActive.get(i);
                    if (f != null) {
                        moveToState(f, newState, transit, transitStyle);
                    }
                }
                if (this.mNeedMenuInvalidate && this.mActivity != null && this.mCurState == 5) {
                    this.mActivity.supportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = HONEYCOMB;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeActive(Fragment f) {
        if (f.mIndex < 0) {
            if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
                if (this.mActive == null) {
                    this.mActive = new ArrayList<>();
                }
                f.setIndex(this.mActive.size());
                this.mActive.add(f);
                return;
            }
            f.setIndex(((Integer) this.mAvailIndices.remove(this.mAvailIndices.size() - 1)).intValue());
            this.mActive.set(f.mIndex, f);
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeInactive(Fragment f) {
        if (f.mIndex >= 0) {
            if (DEBUG) {
                Log.v(TAG, "Freeing fragment index " + f.mIndex);
            }
            this.mActive.set(f.mIndex, null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList<>();
            }
            this.mAvailIndices.add(Integer.valueOf(f.mIndex));
            this.mActivity.invalidateSupportFragmentIndex(f.mIndex);
            f.initState();
        }
    }

    public void addFragment(Fragment fragment, boolean moveToStateNow) {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList<>();
        }
        if (DEBUG) {
            Log.v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (!fragment.mDetached) {
            this.mAdded.add(fragment);
            fragment.mAdded = true;
            fragment.mRemoving = HONEYCOMB;
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (moveToStateNow) {
                moveToState(fragment);
            }
        }
    }

    public void removeFragment(Fragment fragment, int transition, int transitionStyle) {
        boolean inactive;
        int i = 0;
        if (DEBUG) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        if (!fragment.isInBackStack()) {
            inactive = true;
        } else {
            inactive = false;
        }
        if (!fragment.mDetached || inactive) {
            this.mAdded.remove(fragment);
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = HONEYCOMB;
            fragment.mRemoving = true;
            if (!inactive) {
                i = 1;
            }
            moveToState(fragment, i, transition, transitionStyle);
        }
    }

    public void hideFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mView != null) {
                Animation anim = loadAnimation(fragment, transition, true, transitionStyle);
                if (anim != null) {
                    fragment.mView.startAnimation(anim);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    public void showFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = HONEYCOMB;
            if (fragment.mView != null) {
                Animation anim = loadAnimation(fragment, transition, true, transitionStyle);
                if (anim != null) {
                    fragment.mView.startAnimation(anim);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(HONEYCOMB);
        }
    }

    public void detachFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                this.mAdded.remove(fragment);
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = HONEYCOMB;
                moveToState(fragment, 1, transition, transitionStyle);
            }
        }
    }

    public void attachFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = HONEYCOMB;
            if (!fragment.mAdded) {
                this.mAdded.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                moveToState(fragment, this.mCurState, transition, transitionStyle);
            }
        }
    }

    public Fragment findFragmentById(int id) {
        if (this.mActive != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.mFragmentId == id) {
                    return f;
                }
            }
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = (Fragment) this.mActive.get(i2);
                if (f2 != null && f2.mFragmentId == id) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String tag) {
        if (!(this.mActive == null || tag == null)) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = (Fragment) this.mActive.get(i2);
                if (f2 != null && tag.equals(f2.mTag)) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String who) {
        if (!(this.mActive == null || who == null)) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mActive.get(i);
                if (f != null && who.equals(f.mWho)) {
                    return f;
                }
            }
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    public void enqueueAction(Runnable action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            checkStateLoss();
        }
        synchronized (this) {
            if (this.mActivity == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList<>();
            }
            this.mPendingActions.add(action);
            if (this.mPendingActions.size() == 1) {
                this.mActivity.mHandler.removeCallbacks(this.mExecCommit);
                this.mActivity.mHandler.post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord bse) {
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList<>();
                }
                int index = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                }
                this.mBackStackIndices.add(bse);
                return index;
            }
            int index2 = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
            if (DEBUG) {
                Log.v(TAG, "Adding back stack index " + index2 + " with " + bse);
            }
            this.mBackStackIndices.set(index2, bse);
            return index2;
        }
    }

    public void setBackStackIndex(int index, BackStackRecord bse) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int N = this.mBackStackIndices.size();
            if (index < N) {
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                }
                this.mBackStackIndices.set(index, bse);
            } else {
                while (N < index) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Adding available back stack index " + N);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(N));
                    N++;
                }
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + index + " with " + bse);
                }
                this.mBackStackIndices.add(bse);
            }
        }
    }

    public void freeBackStackIndex(int index) {
        synchronized (this) {
            this.mBackStackIndices.set(index, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + index);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(index));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005b, code lost:
        r5.mExecutingActions = true;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        if (r1 >= r2) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0061, code lost:
        r5.mTmpActions[r1].run();
        r5.mTmpActions[r1] = null;
        r1 = r1 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean execPendingActions() {
        /*
            r5 = this;
            boolean r3 = r5.mExecutingActions
            if (r3 == 0) goto L_0x000c
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "Recursive entry to executePendingTransactions"
            r3.<init>(r4)
            throw r3
        L_0x000c:
            android.os.Looper r3 = android.os.Looper.myLooper()
            android.support.v4.app.FragmentActivity r4 = r5.mActivity
            android.os.Handler r4 = r4.mHandler
            android.os.Looper r4 = r4.getLooper()
            if (r3 == r4) goto L_0x0022
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "Must be called from main thread of process"
            r3.<init>(r4)
            throw r3
        L_0x0022:
            r0 = 0
        L_0x0023:
            monitor-enter(r5)
            java.util.ArrayList<java.lang.Runnable> r3 = r5.mPendingActions     // Catch:{ all -> 0x0070 }
            if (r3 == 0) goto L_0x0030
            java.util.ArrayList<java.lang.Runnable> r3 = r5.mPendingActions     // Catch:{ all -> 0x0070 }
            int r3 = r3.size()     // Catch:{ all -> 0x0070 }
            if (r3 != 0) goto L_0x0032
        L_0x0030:
            monitor-exit(r5)     // Catch:{ all -> 0x0070 }
            return r0
        L_0x0032:
            java.util.ArrayList<java.lang.Runnable> r3 = r5.mPendingActions     // Catch:{ all -> 0x0070 }
            int r2 = r3.size()     // Catch:{ all -> 0x0070 }
            java.lang.Runnable[] r3 = r5.mTmpActions     // Catch:{ all -> 0x0070 }
            if (r3 == 0) goto L_0x0041
            java.lang.Runnable[] r3 = r5.mTmpActions     // Catch:{ all -> 0x0070 }
            int r3 = r3.length     // Catch:{ all -> 0x0070 }
            if (r3 >= r2) goto L_0x0045
        L_0x0041:
            java.lang.Runnable[] r3 = new java.lang.Runnable[r2]     // Catch:{ all -> 0x0070 }
            r5.mTmpActions = r3     // Catch:{ all -> 0x0070 }
        L_0x0045:
            java.util.ArrayList<java.lang.Runnable> r3 = r5.mPendingActions     // Catch:{ all -> 0x0070 }
            java.lang.Runnable[] r4 = r5.mTmpActions     // Catch:{ all -> 0x0070 }
            r3.toArray(r4)     // Catch:{ all -> 0x0070 }
            java.util.ArrayList<java.lang.Runnable> r3 = r5.mPendingActions     // Catch:{ all -> 0x0070 }
            r3.clear()     // Catch:{ all -> 0x0070 }
            android.support.v4.app.FragmentActivity r3 = r5.mActivity     // Catch:{ all -> 0x0070 }
            android.os.Handler r3 = r3.mHandler     // Catch:{ all -> 0x0070 }
            java.lang.Runnable r4 = r5.mExecCommit     // Catch:{ all -> 0x0070 }
            r3.removeCallbacks(r4)     // Catch:{ all -> 0x0070 }
            monitor-exit(r5)     // Catch:{ all -> 0x0070 }
            r3 = 1
            r5.mExecutingActions = r3
            r1 = 0
        L_0x005f:
            if (r1 >= r2) goto L_0x0073
            java.lang.Runnable[] r3 = r5.mTmpActions
            r3 = r3[r1]
            r3.run()
            java.lang.Runnable[] r3 = r5.mTmpActions
            r4 = 0
            r3[r1] = r4
            int r1 = r1 + 1
            goto L_0x005f
        L_0x0070:
            r3 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0070 }
            throw r3
        L_0x0073:
            r3 = 0
            r5.mExecutingActions = r3
            r0 = 1
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.FragmentManagerImpl.execPendingActions():boolean");
    }

    /* access modifiers changed from: 0000 */
    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                ((OnBackStackChangedListener) this.mBackStackChangeListeners.get(i)).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(state);
        reportBackStackChanged();
    }

    /* access modifiers changed from: 0000 */
    public boolean popBackStackState(Handler handler, String name, int id, int flags) {
        boolean z;
        if (this.mBackStack == null) {
            return HONEYCOMB;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int last = this.mBackStack.size() - 1;
            if (last < 0) {
                return HONEYCOMB;
            }
            ((BackStackRecord) this.mBackStack.remove(last)).popFromBackStack(true);
            reportBackStackChanged();
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                int index2 = this.mBackStack.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss = (BackStackRecord) this.mBackStack.get(index);
                    if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                        break;
                    }
                    index2 = index - 1;
                }
                if (index < 0) {
                    return HONEYCOMB;
                }
                if ((flags & 1) != 0) {
                    index--;
                    while (index >= 0) {
                        BackStackRecord bss2 = (BackStackRecord) this.mBackStack.get(index);
                        if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                            break;
                        }
                        index--;
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return HONEYCOMB;
            }
            ArrayList<BackStackRecord> states = new ArrayList<>();
            for (int i = this.mBackStack.size() - 1; i > index; i--) {
                states.add(this.mBackStack.remove(i));
            }
            int LAST = states.size() - 1;
            for (int i2 = 0; i2 <= LAST; i2++) {
                if (DEBUG) {
                    Log.v(TAG, "Popping back stack state: " + states.get(i2));
                }
                BackStackRecord backStackRecord = (BackStackRecord) states.get(i2);
                if (i2 == LAST) {
                    z = true;
                } else {
                    z = false;
                }
                backStackRecord.popFromBackStack(z);
            }
            reportBackStackChanged();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<Fragment> retainNonConfig() {
        int i;
        ArrayList<Fragment> fragments = null;
        if (this.mActive != null) {
            for (int i2 = 0; i2 < this.mActive.size(); i2++) {
                Fragment f = (Fragment) this.mActive.get(i2);
                if (f != null && f.mRetainInstance) {
                    if (fragments == null) {
                        fragments = new ArrayList<>();
                    }
                    fragments.add(f);
                    f.mRetaining = true;
                    if (f.mTarget != null) {
                        i = f.mTarget.mIndex;
                    } else {
                        i = -1;
                    }
                    f.mTargetIndex = i;
                }
            }
        }
        return fragments;
    }

    /* access modifiers changed from: 0000 */
    public void saveFragmentViewState(Fragment f) {
        if (f.mInnerView != null) {
            if (this.mStateArray == null) {
                this.mStateArray = new SparseArray<>();
            } else {
                this.mStateArray.clear();
            }
            f.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                f.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Bundle saveFragmentBasicState(Fragment f) {
        Bundle result = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        f.onSaveInstanceState(this.mStateBundle);
        if (!this.mStateBundle.isEmpty()) {
            result = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (f.mView != null) {
            saveFragmentViewState(f);
        }
        if (f.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public Parcelable saveAllState() {
        execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int N = this.mActive.size();
        FragmentState[] active = new FragmentState[N];
        boolean haveFragments = HONEYCOMB;
        for (int i = 0; i < N; i++) {
            Fragment f = (Fragment) this.mActive.get(i);
            if (f != null) {
                haveFragments = true;
                FragmentState fs = new FragmentState(f);
                active[i] = fs;
                if (f.mState <= 0 || fs.mSavedFragmentState != null) {
                    fs.mSavedFragmentState = f.mSavedFragmentState;
                } else {
                    fs.mSavedFragmentState = saveFragmentBasicState(f);
                    if (f.mTarget != null) {
                        if (f.mTarget.mIndex < 0) {
                            String msg = "Failure saving state: " + f + " has target not in fragment manager: " + f.mTarget;
                            Log.e(TAG, msg);
                            dump("  ", null, new PrintWriter(new LogWriter(TAG)), new String[0]);
                            throw new IllegalStateException(msg);
                        }
                        if (fs.mSavedFragmentState == null) {
                            fs.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
                        if (f.mTargetRequestCode != 0) {
                            fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + f + ": " + fs.mSavedFragmentState);
                }
            }
        }
        if (haveFragments) {
            int[] added = null;
            BackStackState[] backStack = null;
            if (this.mAdded != null) {
                int N2 = this.mAdded.size();
                if (N2 > 0) {
                    added = new int[N2];
                    for (int i2 = 0; i2 < N2; i2++) {
                        added[i2] = ((Fragment) this.mAdded.get(i2)).mIndex;
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                        }
                    }
                }
            }
            if (this.mBackStack != null) {
                int N3 = this.mBackStack.size();
                if (N3 > 0) {
                    backStack = new BackStackState[N3];
                    for (int i3 = 0; i3 < N3; i3++) {
                        backStack[i3] = new BackStackState(this, (BackStackRecord) this.mBackStack.get(i3));
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                        }
                    }
                }
            }
            FragmentManagerState fms = new FragmentManagerState();
            fms.mActive = active;
            fms.mAdded = added;
            fms.mBackStack = backStack;
            return fms;
        } else if (!DEBUG) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void restoreAllState(Parcelable state, ArrayList<Fragment> nonConfig) {
        if (state != null) {
            FragmentManagerState fms = (FragmentManagerState) state;
            if (fms.mActive != null) {
                if (nonConfig != null) {
                    for (int i = 0; i < nonConfig.size(); i++) {
                        Fragment f = (Fragment) nonConfig.get(i);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: re-attaching retained " + f);
                        }
                        FragmentState fs = fms.mActive[f.mIndex];
                        fs.mInstance = f;
                        f.mSavedViewState = null;
                        f.mBackStackNesting = 0;
                        f.mInLayout = HONEYCOMB;
                        f.mAdded = HONEYCOMB;
                        f.mTarget = null;
                        if (fs.mSavedFragmentState != null) {
                            fs.mSavedFragmentState.setClassLoader(this.mActivity.getClassLoader());
                            f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                        }
                    }
                }
                this.mActive = new ArrayList<>(fms.mActive.length);
                if (this.mAvailIndices != null) {
                    this.mAvailIndices.clear();
                }
                for (int i2 = 0; i2 < fms.mActive.length; i2++) {
                    FragmentState fs2 = fms.mActive[i2];
                    if (fs2 != null) {
                        Fragment f2 = fs2.instantiate(this.mActivity);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: adding #" + i2 + ": " + f2);
                        }
                        this.mActive.add(f2);
                        fs2.mInstance = null;
                    } else {
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: adding #" + i2 + ": (null)");
                        }
                        this.mActive.add(null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList<>();
                        }
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: adding avail #" + i2);
                        }
                        this.mAvailIndices.add(Integer.valueOf(i2));
                    }
                }
                if (nonConfig != null) {
                    for (int i3 = 0; i3 < nonConfig.size(); i3++) {
                        Fragment f3 = (Fragment) nonConfig.get(i3);
                        if (f3.mTargetIndex >= 0) {
                            if (f3.mTargetIndex < this.mActive.size()) {
                                f3.mTarget = (Fragment) this.mActive.get(f3.mTargetIndex);
                            } else {
                                Log.w(TAG, "Re-attaching retained fragment " + f3 + " target no longer exists: " + f3.mTargetIndex);
                                f3.mTarget = null;
                            }
                        }
                    }
                }
                if (fms.mAdded != null) {
                    this.mAdded = new ArrayList<>(fms.mAdded.length);
                    for (int i4 = 0; i4 < fms.mAdded.length; i4++) {
                        Fragment f4 = (Fragment) this.mActive.get(fms.mAdded[i4]);
                        if (f4 == null) {
                            throw new IllegalStateException("No instantiated fragment for index #" + fms.mAdded[i4]);
                        }
                        f4.mAdded = true;
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: making added #" + i4 + ": " + f4);
                        }
                        this.mAdded.add(f4);
                    }
                } else {
                    this.mAdded = null;
                }
                if (fms.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fms.mBackStack.length);
                    for (int i5 = 0; i5 < fms.mBackStack.length; i5++) {
                        BackStackRecord bse = fms.mBackStack[i5].instantiate(this);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: adding bse #" + i5 + " (index " + bse.mIndex + "): " + bse);
                        }
                        this.mBackStack.add(bse);
                        if (bse.mIndex >= 0) {
                            setBackStackIndex(bse.mIndex, bse);
                        }
                    }
                    return;
                }
                this.mBackStack = null;
            }
        }
    }

    public void attachActivity(FragmentActivity activity) {
        if (this.mActivity != null) {
            throw new IllegalStateException();
        }
        this.mActivity = activity;
    }

    public void noteStateNotSaved() {
        this.mStateSaved = HONEYCOMB;
    }

    public void dispatchCreate() {
        this.mStateSaved = HONEYCOMB;
        moveToState(1, HONEYCOMB);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = HONEYCOMB;
        moveToState(2, HONEYCOMB);
    }

    public void dispatchStart() {
        this.mStateSaved = HONEYCOMB;
        moveToState(4, HONEYCOMB);
    }

    public void dispatchResume() {
        this.mStateSaved = HONEYCOMB;
        moveToState(5, HONEYCOMB);
    }

    public void dispatchPause() {
        moveToState(4, HONEYCOMB);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        moveToState(3, HONEYCOMB);
    }

    public void dispatchReallyStop() {
        moveToState(2, HONEYCOMB);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        moveToState(0, HONEYCOMB);
        this.mActivity = null;
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.onConfigurationChanged(newConfig);
                }
            }
        }
    }

    public void dispatchLowMemory() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.onLowMemory();
                }
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        boolean show = HONEYCOMB;
        ArrayList<Fragment> newMenus = null;
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible) {
                    show = true;
                    f.onCreateOptionsMenu(menu, inflater);
                    if (newMenus == null) {
                        newMenus = new ArrayList<>();
                    }
                    newMenus.add(f);
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment f2 = (Fragment) this.mCreatedMenus.get(i2);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean show = HONEYCOMB;
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible) {
                    show = true;
                    f.onPrepareOptionsMenu(menu);
                }
            }
        }
        return show;
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f.onOptionsItemSelected(item)) {
                    return true;
                }
            }
        }
        return HONEYCOMB;
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && !f.mHidden && f.onContextItemSelected(item)) {
                    return true;
                }
            }
        }
        return HONEYCOMB;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mActive != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible) {
                    f.onOptionsMenuClosed(menu);
                }
            }
        }
    }

    public static int reverseTransit(int transit) {
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE /*8194*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            default:
                return 0;
        }
    }

    public static int transitToStyleIndex(int transit, boolean enter) {
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return enter ? 1 : 2;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return enter ? 5 : 6;
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE /*8194*/:
                return enter ? 3 : 4;
            default:
                return -1;
        }
    }
}
