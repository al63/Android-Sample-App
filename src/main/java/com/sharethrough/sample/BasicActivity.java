package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;

public class BasicActivity extends Activity {

    public static final String PLACEMENT_KEY = "eeea9e65";
    private Sharethrough sharethrough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basic_activity);

        final BasicAdView adViewWithDescription = (BasicAdView) findViewById(R.id.sharethrough_ad_with_description);
        final BasicAdView adViewWithoutDescription = (BasicAdView) findViewById(R.id.sharethrough_ad_without_description);

        adViewWithDescription.setVisibility(View.GONE);
        adViewWithoutDescription.setVisibility(View.GONE);

        sharethrough = new Sharethrough(this, PLACEMENT_KEY, 1000);
        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                adViewWithDescription.setVisibility(View.VISIBLE);
                adViewWithoutDescription.setVisibility(View.VISIBLE);
            }

            @Override
            public void noAdsToShow() {
                adViewWithDescription.setVisibility(View.GONE);
                adViewWithoutDescription.setVisibility(View.GONE);
            }
        });

        adViewWithDescription.prepareWithResourceIds(R.layout.basic_ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
        sharethrough.putCreativeIntoAdView(adViewWithDescription);

        adViewWithoutDescription.prepareWithResourceIds(R.layout.basic_ad, R.id.title, -1, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, -1);
        sharethrough.putCreativeIntoAdView(adViewWithoutDescription);
    }

    // Only for testing
    Sharethrough getSharethrough() {
        return sharethrough;
    }
}