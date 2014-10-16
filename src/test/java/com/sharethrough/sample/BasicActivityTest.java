package com.sharethrough.sample;

import android.widget.TextView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class BasicActivityTest {

    private BasicActivity subject;

    @Test
    public void showsAnAddFromSDK() throws Exception {
        ActivityController<BasicActivity> activityController = Robolectric.buildActivity(BasicActivity.class).create().start().visible().resume();
        subject = activityController.get();

        assertThat((TextView) subject.findViewById(R.id.title)).hasText("Title");
        assertThat((TextView) subject.findViewById(R.id.description)).hasText("Description");
        assertThat((TextView) subject.findViewById(R.id.advertiser)).hasText("Advertiser");
    }
}