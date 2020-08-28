package com.google.ads.searchads;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import com.google.ads.AdRequest;
import java.util.Map;

public class SearchAdRequest extends AdRequest {

    /* renamed from: a */
    private String f120a;

    /* renamed from: b */
    private int f121b;

    /* renamed from: c */
    private Pair<Integer, Integer> f122c;

    /* renamed from: d */
    private int f123d;

    /* renamed from: e */
    private int f124e;

    /* renamed from: f */
    private int f125f;

    /* renamed from: g */
    private String f126g;

    /* renamed from: h */
    private int f127h;

    /* renamed from: i */
    private int f128i;

    /* renamed from: j */
    private BorderType f129j;

    /* renamed from: k */
    private int f130k;

    public enum BorderType {
        NONE("none"),
        DASHED("dashed"),
        DOTTED("dotted"),
        SOLID("solid");
        

        /* renamed from: a */
        private String f132a;

        private BorderType(String param) {
            this.f132a = param;
        }

        public final String toString() {
            return this.f132a;
        }
    }

    public void setQuery(String query) {
        this.f120a = query;
    }

    public void setBackgroundColor(int backgroundColor) {
        if (Color.alpha(backgroundColor) == 255) {
            this.f121b = backgroundColor;
            this.f122c = null;
        }
    }

    public void setBackgroundGradient(int from, int to) {
        if (Color.alpha(from) == 255 && Color.alpha(to) == 255) {
            this.f121b = Color.argb(0, 0, 0, 0);
            this.f122c = Pair.create(new Integer(from), new Integer(to));
        }
    }

    public void setHeaderTextColor(int headerTextColor) {
        this.f123d = headerTextColor;
    }

    public void setDescriptionTextColor(int descriptionTextColor) {
        this.f124e = descriptionTextColor;
    }

    public void setAnchorTextColor(int anchorTextColor) {
        this.f125f = anchorTextColor;
    }

    public void setFontFace(String fontFace) {
        this.f126g = fontFace;
    }

    public void setHeaderTextSize(int headerTextSize) {
        this.f127h = headerTextSize;
    }

    public void setBorderColor(int borderColor) {
        this.f128i = borderColor;
    }

    public void setBorderType(BorderType borderType) {
        this.f129j = borderType;
    }

    public void setBorderThickness(int borderThickness) {
        this.f130k = borderThickness;
    }

    public Map<String, Object> getRequestMap(Context context) {
        if (this.f120a != null) {
            addExtra("q", this.f120a);
        }
        if (Color.alpha(this.f121b) != 0) {
            addExtra("bgcolor", m125a(this.f121b));
        }
        if (!(this.f122c == null || this.f122c.first == null || this.f122c.second == null)) {
            addExtra("gradientfrom", m125a(((Integer) this.f122c.first).intValue()));
            addExtra("gradientto", m125a(((Integer) this.f122c.second).intValue()));
        }
        if (Color.alpha(this.f123d) != 0) {
            addExtra("hcolor", m125a(this.f123d));
        }
        if (Color.alpha(this.f124e) != 0) {
            addExtra("dcolor", m125a(this.f124e));
        }
        if (Color.alpha(this.f125f) != 0) {
            addExtra("acolor", m125a(this.f125f));
        }
        if (this.f126g != null) {
            addExtra("font", this.f126g);
        }
        addExtra("headersize", Integer.toString(this.f127h));
        if (Color.alpha(this.f128i) != 0) {
            addExtra("bcolor", m125a(this.f128i));
        }
        if (this.f129j != null) {
            addExtra("btype", this.f129j.toString());
        }
        addExtra("bthick", Integer.toString(this.f130k));
        return super.getRequestMap(context);
    }

    /* renamed from: a */
    private static String m125a(int i) {
        return String.format("#%06x", new Object[]{Integer.valueOf(16777215 & i)});
    }
}
