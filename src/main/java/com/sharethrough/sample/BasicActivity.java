package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import com.sharethrough.sdk.BasicAd;

public class BasicActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BasicAd basicAd = new BasicAd(this);
        basicAd.showAd(R.layout.basic, R.id.title, R.id.description, R.id.advertiser);
        setContentView(basicAd);
    }
}
