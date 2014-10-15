package com.sharethrough.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@Config(manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void testSomething() throws Exception {
        MyActivity activity = Robolectric.buildActivity(MyActivity.class).create().get();
        assertTrue(activity != null);
    }
}
