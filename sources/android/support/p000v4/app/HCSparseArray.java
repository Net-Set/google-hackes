package android.support.p000v4.app;

import android.util.Log;

/* renamed from: android.support.v4.app.HCSparseArray */
public class HCSparseArray<E> {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public HCSparseArray() {
        this(10);
    }

    public HCSparseArray(int initialCapacity) {
        this.mGarbage = false;
        int initialCapacity2 = idealIntArraySize(initialCapacity);
        this.mKeys = new int[initialCapacity2];
        this.mValues = new Object[initialCapacity2];
        this.mSize = 0;
    }

    public E get(int key) {
        return get(key, null);
    }

    public E get(int key, E valueIfKeyNotFound) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        return (i < 0 || this.mValues[i] == DELETED) ? valueIfKeyNotFound : this.mValues[i];
    }

    public void delete(int key) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0 && this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(int key) {
        delete(key);
    }

    public void removeAt(int index) {
        if (this.mValues[index] != DELETED) {
            this.mValues[index] = DELETED;
            this.mGarbage = true;
        }
    }

    /* renamed from: gc */
    private void m0gc() {
        int n = this.mSize;
        int o = 0;
        int[] keys = this.mKeys;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            Object val = values[i];
            if (val != DELETED) {
                if (i != o) {
                    keys[o] = keys[i];
                    values[o] = val;
                }
                o++;
            }
        }
        this.mGarbage = false;
        this.mSize = o;
    }

    public void put(int key, E value) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0) {
            this.mValues[i] = value;
            return;
        }
        int i2 = i ^ -1;
        if (i2 >= this.mSize || this.mValues[i2] != DELETED) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                m0gc();
                i2 = binarySearch(this.mKeys, 0, this.mSize, key) ^ -1;
            }
            if (this.mSize >= this.mKeys.length) {
                int n = idealIntArraySize(this.mSize + 1);
                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }
            if (this.mSize - i2 != 0) {
                System.arraycopy(this.mKeys, i2, this.mKeys, i2 + 1, this.mSize - i2);
                System.arraycopy(this.mValues, i2, this.mValues, i2 + 1, this.mSize - i2);
            }
            this.mKeys[i2] = key;
            this.mValues[i2] = value;
            this.mSize++;
            return;
        }
        this.mKeys[i2] = key;
        this.mValues[i2] = value;
    }

    public int size() {
        if (this.mGarbage) {
            m0gc();
        }
        return this.mSize;
    }

    public int keyAt(int index) {
        if (this.mGarbage) {
            m0gc();
        }
        return this.mKeys[index];
    }

    public E valueAt(int index) {
        if (this.mGarbage) {
            m0gc();
        }
        return this.mValues[index];
    }

    public void setValueAt(int index, E value) {
        if (this.mGarbage) {
            m0gc();
        }
        this.mValues[index] = value;
    }

    public int indexOfKey(int key) {
        if (this.mGarbage) {
            m0gc();
        }
        return binarySearch(this.mKeys, 0, this.mSize, key);
    }

    public int indexOfValue(E value) {
        if (this.mGarbage) {
            m0gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            values[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(int key, E value) {
        if (this.mSize == 0 || key > this.mKeys[this.mSize - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                m0gc();
            }
            int pos = this.mSize;
            if (pos >= this.mKeys.length) {
                int n = idealIntArraySize(pos + 1);
                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }
            this.mKeys[pos] = key;
            this.mValues[pos] = value;
            this.mSize = pos + 1;
            return;
        }
        put(key, value);
    }

    private static int binarySearch(int[] a, int start, int len, int key) {
        int high = start + len;
        int low = start - 1;
        while (high - low > 1) {
            int guess = (high + low) / 2;
            if (a[guess] < key) {
                low = guess;
            } else {
                high = guess;
            }
        }
        if (high == start + len) {
            return (start + len) ^ -1;
        }
        return a[high] != key ? high ^ -1 : high;
    }

    private void checkIntegrity() {
        for (int i = 1; i < this.mSize; i++) {
            if (this.mKeys[i] <= this.mKeys[i - 1]) {
                for (int j = 0; j < this.mSize; j++) {
                    Log.e("FAIL", j + ": " + this.mKeys[j] + " -> " + this.mValues[j]);
                }
                throw new RuntimeException();
            }
        }
    }

    static int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++) {
            if (need <= (1 << i) - 12) {
                return (1 << i) - 12;
            }
        }
        return need;
    }

    static int idealIntArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }
}
