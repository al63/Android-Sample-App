package com.sharethrough.sample;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.sharethrough.test.Fixtures;
import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.RequestMatcher;
import org.robolectric.tester.org.apache.http.TestHttpResponse;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ListAdapterWithBasicViewActivityTest {

    private ListAdapterWithBasicViewActivity subject;

    @Before
    public void setUp() throws Exception {
        Robolectric.addHttpResponseRule(new RequestMatcher() {
            @Override
            public boolean matches(HttpRequest httpRequest) {
                return httpRequest.getRequestLine().getUri().contains(ListAdapterWithBasicViewActivity.STR_KEY);
            }
        }, new TestHttpResponse(200, Fixtures.getFile("assets/str_ad_youtube.json")));
        Robolectric.addHttpResponseRule("GET", "http://th.umb.na/il/URL", new TestHttpResponse());

        subject = Robolectric.buildActivity(ListAdapterWithBasicViewActivity.class).create().start().visible().resume().get();
    }

    @Test
    public void showsAdInTheMix() throws Exception {
        ListView listView = (ListView) subject.findViewById(R.id.list);
        shadowOf(listView).populateItems();

        ViewGroup adView = (ViewGroup) listView.getChildAt(3);
        assertThat((TextView)adView.findViewById(R.id.title)).hasText("Title");
        assertThat((TextView)adView.findViewById(R.id.description)).hasText("Description.");
        assertThat((TextView)adView.findViewById(R.id.advertiser)).hasText("Advertiser");
    }
}