package com.sharethrough.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.sharethrough.sdk.Sharethrough;
import com.sharethrough.sdk.SharethroughListAdapter;

import java.util.Arrays;
import java.util.List;

public class SharethroughListAdapterActivity extends Activity {
    private static final String STR_KEY = "eeea9e65";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharethrough_list_adapter);

        ListView listView = (ListView) findViewById(R.id.list);

        final Sharethrough sharethrough = new Sharethrough(this, STR_KEY, 1000);
        final ExampleAdapter myAdapter = new ExampleAdapter(getBaseContext(), Arrays.asList(FEED));

        SharethroughListAdapter sharethroughAdapter = new SharethroughListAdapter(this, myAdapter, sharethrough, R.layout.basic_ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
        listView.setAdapter(sharethroughAdapter);
        listView.setOnItemClickListener(sharethroughAdapter.createOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        }));

        listView.setOnItemLongClickListener(sharethroughAdapter.createOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Long Clicked: " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        }));

        listView.setOnItemSelectedListener(sharethroughAdapter.createOnItemSelectListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Selected: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public class ExampleAdapter extends BaseAdapter {

        private final Context mContext;
        private List<FeedItem> mList;

        public ExampleAdapter(final Context context, List<FeedItem> list) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE / 2;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position % mList.size());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.feed_item, parent, false);

                ViewHolder vh = new ViewHolder();
                vh.title = ((TextView) convertView.findViewById(R.id.title));
                vh.description = ((TextView) convertView.findViewById(R.id.description));
                vh.image = ((ImageView) convertView.findViewById(R.id.image));

                convertView.setTag(vh);
            }

            ViewHolder vh = (ViewHolder) convertView.getTag();

            FeedItem item = (FeedItem) getItem(position);

            vh.title.setText(item.title);
            vh.description.setText(item.description);
            vh.image.setImageDrawable(getResources().getDrawable(item.imageResourceId));

            return convertView;
        }

        private class ViewHolder {
            TextView title;
            TextView description;
            ImageView image;
        }
    }

}
