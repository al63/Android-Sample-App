package com.sharethrough.sample;

import android.content.Intent;
import android.widget.ListView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.robolectric.Robolectric.shadowOf;


@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../res/debug/")
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    private MyActivity subject;
    private ActivityController<MyActivity> activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(MyActivity.class).create().start().visible().resume();
        subject = activityController.get();
    }

    @Test
    public void showsItemForBasicView() throws Exception {
        ListView menu = (ListView) subject.findViewById(R.id.menu);
        shadowOf(menu).populateItems();
        shadowOf(menu).clickFirstItemContainingText("Basic");
        Intent nextStartedActivity = shadowOf(subject).getNextStartedActivity();

        assertThat(nextStartedActivity).isEqualTo(new Intent(subject, BasicActivity.class));
    }

    @Test
    public void showsItemForListWithBasicView() throws Exception {
        ListView menu = (ListView) subject.findViewById(R.id.menu);
        shadowOf(menu).populateItems();
        shadowOf(menu).clickFirstItemContainingText("News Feed");
        Intent nextStartedActivity = shadowOf(subject).getNextStartedActivity();

        assertThat(nextStartedActivity).isEqualTo(new Intent(subject, ListAdapterWithBasicViewActivity.Feed.class));
    }

    @Test
    public void showsItemForListWithCardView() throws Exception {
        ListView menu = (ListView) subject.findViewById(R.id.menu);
        shadowOf(menu).populateItems();
        shadowOf(menu).clickFirstItemContainingText("Card");
        Intent nextStartedActivity = shadowOf(subject).getNextStartedActivity();

        assertThat(nextStartedActivity).isEqualTo(new Intent(subject, ListAdapterWithBasicViewActivity.Card.class));
    }
}
