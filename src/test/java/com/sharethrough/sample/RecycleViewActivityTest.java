package com.sharethrough.sample;

import android.support.v7.widget.RecyclerView;
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
public class RecycleViewActivityTest {

    private RecycleViewActivity subject;

    @Before
    public void setUp() throws Exception {
        Robolectric.addHttpResponseRule(new RequestMatcher() {
            @Override
            public boolean matches(HttpRequest httpRequest) {
                return httpRequest.getRequestLine().getUri().contains(RecycleViewActivity.STR_KEY);
            }
        }, new TestHttpResponse(200, Fixtures.getFile("assets/str_ad_youtube_single.json")));
        Robolectric.addHttpResponseRule("GET", "http://th.umb.na/il/URL", new TestHttpResponse());

        subject = Robolectric.buildActivity(RecycleViewActivity.class).create().start().visible().resume().get();
    }

    @Test
    public void showsAdInTheMix() throws Exception {
        RecyclerView recyclerView = (RecyclerView) subject.findViewById(R.id.recycler_view);
        ViewGroup adView = (ViewGroup) recyclerView.getChildAt(6);
        assertThat((TextView) adView.findViewById(R.id.title)).hasText("Title");
        assertThat((TextView) adView.findViewById(R.id.description)).hasText("Description.");
        assertThat((TextView) adView.findViewById(R.id.advertiser)).hasText("Advertiser");
    }
}