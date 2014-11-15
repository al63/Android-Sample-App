package com.sharethrough.sample;

import android.view.View;
import android.view.ViewGroup;
import com.sharethrough.sdk.BasicAdView;
import org.fest.assertions.api.ANDROID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.fest.assertions.api.Assertions.assertThat;

@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../res/debug/")
@RunWith(RobolectricTestRunner.class)
public class BasicActivityTest {

    private BasicActivity subject;

    @Test
    public void whenThereAreAdsAvailable_showsAnAdFromSDK() throws Exception {
        ActivityController<BasicActivity> activityController = Robolectric.buildActivity(BasicActivity.class).create().start().visible().resume();
        subject = activityController.get();

        subject.getSharethrough().getOnStatusChangeListener().newAdsToShow();

        ViewGroup rootView = (ViewGroup) subject.getWindow().getDecorView().getRootView();
        assertThat(hasBasicAdView(rootView)).isTrue();

        ANDROID.assertThat(subject.findViewById(R.id.sharethrough_ad_with_description)).isVisible();
        ANDROID.assertThat(subject.findViewById(R.id.sharethrough_ad_without_description)).isVisible();
    }

    @Test
    public void whenThereAreNoAdsAvailable_doesNotShowAdFromSDK() throws Exception {
        ActivityController<BasicActivity> activityController = Robolectric.buildActivity(BasicActivity.class).create().start().visible().resume();
        subject = activityController.get();

        subject.getSharethrough().getOnStatusChangeListener().noAdsToShow();

        ANDROID.assertThat(subject.findViewById(R.id.sharethrough_ad_with_description)).isNotVisible();
        ANDROID.assertThat(subject.findViewById(R.id.sharethrough_ad_without_description)).isNotVisible();
    }

    boolean hasBasicAdView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof BasicAdView) return true;
            if (child instanceof ViewGroup && hasBasicAdView((ViewGroup) child)) return true;
        }
        return false;
    }
}