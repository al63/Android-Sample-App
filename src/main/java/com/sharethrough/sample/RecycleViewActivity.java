package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import com.sharethrough.sdk.Sharethrough;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

public class RecycleViewActivity extends Activity {
    //private List<FeedItem> feedItemList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    public static final String STR_KEY = "155c3656";
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
            new FeedItem(R.drawable.broncos_uniforms, "Buckin' Broncos", "Colorado rebrands after their embarrassing Super Bowl failure"),
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

    private List<FeedItem> feedItemList = Arrays.asList(FEED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        /*initialize recycler view */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Sharethrough sharethrough = new Sharethrough(this, STR_KEY, 1000);
        final MyRecyclerAdapter recycleAdapter = new MyRecyclerAdapter(feedItemList, sharethrough );
        mRecyclerView.setAdapter( recycleAdapter );



        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                //adsToShow = true;
                recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void noAdsToShow() {
                //adsToShow = false;
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycle_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
