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
            new FeedItem(R.drawable.polar_bears, "The polar bear cubs are born!", "The city zoo is proud to announce the birth of bebo and bobo.  Both cubs and momma bear are doing well."),
            new FeedItem(R.drawable.farming, "The Elders of Organic Farming", "For nearly a week, two dozen pioneers of sustainable agriculture from the United States and Canada shared decades’ worth of stories, secrets and anxieties…"),
            new FeedItem(R.drawable.lorem, "Curabitur pulvinar est arcu quis pretium lectus", "Aenean vel magna feugiat eros luctus convallis. Curabitur est est, convallis id leo eu, imperdiet pretium purus…"),
            new FeedItem(R.drawable.education, "Screen Time Study Finds Education Drop-Off", "According to a study, less than half the time that children age 2 to 10 spend watching or interacting with…"),
            new FeedItem(R.drawable.sabbatical, "Sabbaticals give workers time to pursue passions", "Companies in Nashville and around the U.S. have been adding perks over the years to compete for talent…"),
            new FeedItem(R.drawable.barber, "The 10 Best Barbers in San Francisco", "In need of a killer haircut?  We'll tell where to go and the barber to ask for."),
            new FeedItem(R.drawable.hamburger, "Hamburger Heaven", "How to guarantee your next cookout is the talk of the town. Let's cook some burgers!"),
            new FeedItem(R.drawable.big_data, "Big Data: The New Frontier", "More and more companies are turning to big data"),
            new FeedItem(R.drawable.broncos_uniforms, "Buckin' Broncos", "Colorado rebrands after their embarrassing Super Bowl failure")
    };
    private boolean adsToShow;

    protected abstract int getItemLayoutResourceId();

    protected abstract int getAdLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adapter_with_basic_view);

        final Sharethrough sharethrough = new Sharethrough(this, STR_KEY, 1000);

        ListView list = (ListView) findViewById(R.id.list);
        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                if (adsToShow) {
                    return FEED.length + 1;
                } else {
                    return FEED.length;
                }

            }

            @Override
            public Object getItem(int i) {
                if (adsToShow) {
                    if (i < 3) {
                        return FEED[i];
                    } else if (i == 3) {
                        return sharethrough;
                    } else {
                        return FEED[i - 1];
                    }
                } else {
                    return FEED[i];
                }
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public int getItemViewType(int i) {
                if (adsToShow) {
                    if (i == 3) return IGNORE_ITEM_VIEW_TYPE;
                    return 0;
                } else {
                    return 0;
                }

            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (adsToShow) {
                    if (position != 3) {
                        return getListItemView(convertView, (FeedItem) getItem(position), parent);
                    } else {
                        return getAdView();
                    }
                } else {
                    return getListItemView(convertView, (FeedItem) getItem(position), parent);
                }

            }

            private View getAdView() {
                BasicAdView result = new BasicAdView(ListAdapterWithBasicViewActivity.this);
                //TODO: change r.id.title to something more unique like R.id.sharethrough_title
                result.showAd(sharethrough, getAdLayoutResourceId(), R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail);
                return result;
            }

            private View getListItemView(View convertView, FeedItem item, ViewGroup parent) {
                if (convertView == null) {
                    convertView = ListAdapterWithBasicViewActivity.this.getLayoutInflater().inflate(getItemLayoutResourceId(), parent, false);
                }
                ((TextView) convertView.findViewById(R.id.title)).setText(item.title);
                ((TextView) convertView.findViewById(R.id.description)).setText(item.description);
                ((ImageView) convertView.findViewById(R.id.image)).setImageResource(item.imageResourceId);
                return convertView;
            }
        };
        list.setAdapter(adapter);

        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                adsToShow = true;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void noAdsToShow() {
                adsToShow = false;
                adapter.notifyDataSetChanged();
            }
        });

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
