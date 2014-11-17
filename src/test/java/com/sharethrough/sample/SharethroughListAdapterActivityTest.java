package com.sharethrough.sample;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.sharethrough.test.Fixtures;
import org.apache.http.HttpRequest;
import org.fest.assertions.api.ANDROID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.org.apache.http.RequestMatcher;
import org.robolectric.tester.org.apache.http.TestHttpResponse;

import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class SharethroughListAdapterActivityTest {

    private SharethroughListAdapterActivity subject;

    @Before
    public void setUp() throws Exception {
        Robolectric.addHttpResponseRule(new RequestMatcher() {
            @Override
            public boolean matches(HttpRequest httpRequest) {
                String uri = httpRequest.getRequestLine().getUri();
                return uri.contains("placement_key=" + ListAdapterWithBasicViewActivity.STR_KEY);
            }
        }, new TestHttpResponse(200, Fixtures.getFile("assets/str_ad_youtube.json")));
        Robolectric.addHttpResponseRule("GET", "http://th.umb.na/il/URL", new TestHttpResponse());

        Robolectric.addHttpResponseRule(new RequestMatcher() {
            @Override
            public boolean matches(HttpRequest httpRequest) {
                String uri = httpRequest.getRequestLine().getUri();
                return uri.contains("native.sharethrough.com") && uri.contains(ListAdapterWithBasicViewActivity.STR_KEY);
            }
        }, new TestHttpResponse(200, "{\"articlesBeforeFirstAd\": 3, \"articlesBetweenAds\": 2}"));
        Robolectric.addHttpResponseRule(new RequestMatcher() {
            @Override
            public boolean matches(HttpRequest httpRequest) {
                return httpRequest.getRequestLine().getUri().startsWith("http://b.sharethrough.com/butler");
            }
        }, new TestHttpResponse(200, ""));

        subject = Robolectric.buildActivity(SharethroughListAdapterActivity.class).create().start().visible().resume().get();
    }

    @Test
    public void showsAdsInTheMix() throws Exception {
        ListView listView = (ListView) subject.findViewById(R.id.list);
        shadowOf(listView).populateItems();

        verifyAdAtPosition(listView, 3);
        verifyAdAtPosition(listView, 6);
        verifyAdAtPosition(listView, 9);
        verifyAdAtPosition(listView, 12);
        verifyAdAtPosition(listView, 15);
        verifyAdAtPosition(listView, 18);
        verifyAdAtPosition(listView, 21);
    }

    private void verifyAdAtPosition(ListView listView, int position) {
        ViewGroup adView = (ViewGroup) listView.getChildAt(position);
        ANDROID.assertThat((TextView) adView.findViewById(R.id.title)).hasText("Title");
        ANDROID.assertThat((TextView) adView.findViewById(R.id.description)).hasText("Description.");
        ANDROID.assertThat((TextView) adView.findViewById(R.id.advertiser)).hasText("Advertiser");
    }
}