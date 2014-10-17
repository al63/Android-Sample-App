package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;

public class BasicActivity extends Activity {

    public static final String PLACEMENT_KEY = "eeea9e65";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basic_activity);

        Sharethrough sharethrough = new Sharethrough(PLACEMENT_KEY);
        BasicAdView adView = (BasicAdView)findViewById(R.id.sharethrough_ad);
        adView.showAd(sharethrough, this, R.layout.basic, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail);
    }
}
