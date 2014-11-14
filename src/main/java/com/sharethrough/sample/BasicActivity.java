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

        Sharethrough sharethrough = new Sharethrough(this, PLACEMENT_KEY, 1000);

        BasicAdView adViewWithDescription = (BasicAdView) findViewById(R.id.sharethrough_ad_with_description);
        adViewWithDescription.showAd(sharethrough, R.layout.basic_ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail);

        BasicAdView adViewWithoutDescription = (BasicAdView) findViewById(R.id.sharethrough_ad_without_description);
        adViewWithoutDescription.showAd(sharethrough, R.layout.basic_ad, R.id.title, R.id.advertiser, R.id.thumbnail);
    }
}