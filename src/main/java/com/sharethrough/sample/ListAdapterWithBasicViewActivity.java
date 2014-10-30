package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;

public abstract class ListAdapterWithBasicViewActivity extends Activity {

    public static final String STR_KEY = "eeea9e65";
    private static final FeedItem[] FEED = {
            new FeedItem(R.drawable.olympics_flag, "Are you ready for the Olympics?", "Two of the most anticipated events for the upcoming games."),
            new FeedItem(R.drawable.polar_bears, "The polar bear cubs are born!","The city zoo is proud to announce the birth of bebo and bobo.  Both cubs and momma bear are doing well."),
            new FeedItem(R.drawable.farming, "The Elders of Organic Farming", "For nearly a week, two dozen pioneers of sustainable agriculture from the United States and Canada shared decades’ worth of stories, secrets and anxieties…"),
            new FeedItem(R.drawable.lorem, "Curabitur pulvinar est arcu quis pretium lectus", "Aenean vel magna feugiat eros luctus convallis. Curabitur est est, convallis id leo eu, imperdiet pretium purus…"),
            new FeedItem(R.drawable.education, "Screen Time Study Finds Education Drop-Off", "According to a study, less than half the time that children age 2 to 10 spend watching or interacting with…"),
            new FeedItem(R.drawable.sabbatical, "Sabbaticals give workers time to pursue passions", "Companies in Nashville and around the U.S. have been adding perks over the years to compete for talent…"),
            new FeedItem(R.drawable.barber, "The 10 Best Barbers in San Francisco", "In need of a killer haircut?  We'll tell where to go and the barber to ask for."),
            new FeedItem(R.drawable.hamburger, "Hamburger Heaven", "How to guarantee your next cookout is the talk of the town. Let's cook some burgers!"),
            new FeedItem(R.drawable.big_data, "Big Data: The New Frontier", "More and more companies are turning to big data"),
            new FeedItem(R.drawable.broncos_uniforms, "Buckin' Broncos", "Colorado rebrands after their embarassing Super Bowl failure")
    };

    protected abstract int getItemLayoutResourceId();

    protected abstract int getAdLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adapter_with_basic_view);

        final Sharethrough sharethrough = new Sharethrough(this, STR_KEY);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return FEED.length + 1;
            }

            @Override
            public Object getItem(int i) {
                if ( i < 3) {
                    return FEED[i];
                }
                else if ( i == 3) {
                    return sharethrough;
                }
                else {
                    return FEED[i-1];
                }
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (position != 3) {
                    return getView((FeedItem) getItem(position), parent);
                } else {
                    BasicAdView result = new BasicAdView(ListAdapterWithBasicViewActivity.this);
                    result.showAd(sharethrough, ListAdapterWithBasicViewActivity.this, getAdLayoutResourceId(), R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail);
                    return result;
                }
            }

            private View getView(FeedItem item, ViewGroup parent) {
                View result = ListAdapterWithBasicViewActivity.this.getLayoutInflater().inflate(getItemLayoutResourceId(), parent, false);
                ((TextView)result.findViewById(R.id.title)).setText(item.title);
                ((TextView)result.findViewById(R.id.description)).setText(item.description);
                ((ImageView)result.findViewById(R.id.image)).setImageResource(item.imageResourceId);
                return result;
            }
        });
    }

    private static class FeedItem {
        public final String title;
        public final String description;
        public final int imageResourceId;

        private FeedItem(int imageResourceId, String title, String description) {
            this.title = title;
            this.description = description;
            this.imageResourceId = imageResourceId;
        }
    }

    public static class Feed extends ListAdapterWithBasicViewActivity {
        @Override
        protected int getItemLayoutResourceId() {
            return R.layout.feed_item;
        }

        @Override
        protected int getAdLayoutResourceId() {
            return R.layout.basic_ad;
        }

    }

    public static class Card extends ListAdapterWithBasicViewActivity {
        @Override
        protected int getItemLayoutResourceId() {
            return R.layout.card_item;
        }

        @Override
        protected int getAdLayoutResourceId() {
            return R.layout.card_ad;
        }
    }
}
