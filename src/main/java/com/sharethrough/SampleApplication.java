package com.sharethrough;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class SampleApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        // Issue wiht Robolectric and Multi Dex support: https://github.com/robolectric/robolectric/issues/1328
        try {
            super.attachBaseContext(base);
            MultiDex.install(this);
        } catch (RuntimeException ignored) {

        }
    }
}
