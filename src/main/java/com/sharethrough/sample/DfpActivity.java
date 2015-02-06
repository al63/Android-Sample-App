package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;

public class DfpActivity extends Activity {
    public static final String PLACEMENT_KEY = "155c3656";
    private Sharethrough sharethrough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dfp);

        final BasicAdView dfpAdView = (BasicAdView) findViewById(R.id.dfp_ad);

        dfpAdView.setVisibility(View.GONE);

        sharethrough = new Sharethrough(this, PLACEMENT_KEY, 1000, true);
        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                dfpAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void noAdsToShow() {
                dfpAdView.setVisibility(View.GONE);
            }
        });

        dfpAdView.prepareWithResourceIds(R.layout.basic_ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
        sharethrough.putCreativeIntoAdView(dfpAdView, 0);
    }

    //Only for testing
    Sharethrough getSharethrough() {
        return sharethrough;
    }

}
