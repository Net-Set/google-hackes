package com.hacks.googhackslite;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class GoogHacksMainActivity extends Activity {
    private Dialog InfoDialog;
    private String MY_AD_UNIT_ID = "a14edfa1b022f93";
    private AdView adView;
    protected AppPreferences appPrefs;
    private Spinner search_spinner;
    private EditText search_string;
    private String[] search_values;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(3);
        setContentView(C0074R.layout.main);
        setFeatureDrawableResource(3, C0074R.drawable.googhack_logo);
        this.search_string = (EditText) findViewById(C0074R.C0075id.tv_googhacks_search_string);
        this.search_values = getResources().getStringArray(C0074R.array.sa_search_values);
        this.search_spinner = (Spinner) findViewById(C0074R.C0075id.sp_search_options);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, C0074R.array.sa_search_options, 17367048);
        adapter1.setDropDownViewResource(17367049);
        this.search_spinner.setAdapter(adapter1);
        this.adView = new AdView((Activity) this, AdSize.IAB_MRECT, this.MY_AD_UNIT_ID);
        ((LinearLayout) findViewById(C0074R.C0075id.main)).addView(this.adView);
        this.adView.loadAd(new AdRequest());
        this.appPrefs = new AppPreferences(this);
        String AppVersion = "";
        try {
            AppVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!this.appPrefs.getPrefs().equals("info_v" + AppVersion)) {
            this.InfoDialog = new Dialog(this);
            this.InfoDialog.requestWindowFeature(1);
            this.InfoDialog.setCancelable(true);
            this.InfoDialog.setCanceledOnTouchOutside(true);
            this.InfoDialog.setContentView(C0074R.layout.first_time_info);
            this.InfoDialog.show();
            this.appPrefs.setPrefs("info_v" + AppVersion);
        }
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case C0074R.C0075id.btn_googhack_search /*2131165188*/:
                HackGoogle();
                return;
            default:
                return;
        }
    }

    private void HackGoogle() {
        if (this.search_string.getText().length() > 0) {
            String srcString = this.search_string.getText().toString();
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.com/search?q=" + srcString + "%20" + getSpinnerString())));
            return;
        }
        Toast.makeText(this, "Please enter text to search", 1).show();
    }

    private String getSpinnerString() {
        String SpinnerString = null;
        Toast.makeText(this, "Finding: " + this.search_spinner.getSelectedItem().toString(), 1).show();
        if (this.search_spinner.getSelectedItem().toString().contains("Music")) {
            SpinnerString = this.search_values[0].toString();
        }
        if (this.search_spinner.getSelectedItem().toString().contains("Images")) {
            SpinnerString = this.search_values[1].toString();
        }
        if (this.search_spinner.getSelectedItem().toString().contains("eBooks and PDFs")) {
            SpinnerString = this.search_values[2].toString();
        }
        if (this.search_spinner.getSelectedItem().toString().contains("Text Files")) {
            SpinnerString = this.search_values[3].toString();
        }
        if (this.search_spinner.getSelectedItem().toString().contains("Compressed Files")) {
            return this.search_values[4].toString();
        }
        if (this.search_spinner.getSelectedItem().toString().contains("Other..")) {
            return this.search_values[5].toString();
        }


        return SpinnerString;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0074R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0074R.C0075id.info /*2131165189*/:
                this.InfoDialog = new Dialog(this);
                this.InfoDialog.requestWindowFeature(1);
                this.InfoDialog.setCancelable(true);
                this.InfoDialog.setCanceledOnTouchOutside(true);
                this.InfoDialog.setContentView(C0074R.layout.first_time_info);
                this.InfoDialog.show();
                break;
            case C0074R.C0075id.blog /*2131165190*/:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://tndevteam.blogspot.com")));
                break;
            case C0074R.C0075id.rate /*2131165191*/:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.hacks.googhackslite")));
                break;
        }
        return true;
    }

    public void onDestroy() {
        this.adView.destroy();
        super.onDestroy();
    }
}
