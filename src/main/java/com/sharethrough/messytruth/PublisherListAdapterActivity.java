package com.sharethrough.messytruth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.sharethrough.sample.R;
import com.sharethrough.sdk.Logger;
import com.sharethrough.sdk.Sharethrough;

import java.util.ArrayList;


public class PublisherListAdapterActivity extends Activity {
    private static final String PLACEMENT_KEY = "16592761";
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;
    private PublisherListAdapter publisherListAdapter;
    private SharethroughListAdapter sharethroughListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mt_activity_sharethrough_list_adapter);
        setSwipeRefreshLayout();

        setupListAdapter();

        retrievePublisherContentList();
    }

    private void setupListAdapter() {
        publisherListAdapter = new PublisherListAdapter(context.getApplicationContext(), R.layout.mt_list_view, new ArrayList<ContentItem>());

        sharethroughListAdapter = new SharethroughListAdapter(context, publisherListAdapter, PLACEMENT_KEY);
        sharethroughListAdapter.setAdVariables(R.layout.mt_ad_view,
                R.id.title,
                R.id.description,
                R.id.advertiser,
                R.id.thumbnail,
                R.id.optout_icon,
                R.id.brand_logo);

        // create listview
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(sharethroughListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", ((ContentItem)sharethroughListAdapter.getItem(position)).getLink());
                startActivity(intent);
            }
        });
    }

    /**
     * Makes request to retrieve Messy Truth Publisher content items
     */
    private void retrievePublisherContentList() {
        ContentRequest contentRequest = new ContentRequest();
        contentRequest.addOnCompleteListener(requestContentListOnCompleteListener);
        contentRequest.doRequest();
    }

    /**
     * Sets the drag down to refresh layout
     */
    private void setSwipeRefreshLayout() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(android.R.color.holo_green_light);
        swipeLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.transparent));
        swipeLayout.setOnRefreshListener(refreshListener);
    }

    // refreshListener will make an content request to refresh the Messy Truth content items
    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            retrievePublisherContentList();
            swipeLayout.setRefreshing(false);
        }
    };

    /**
     * Populates list sharethroughListAdapter with content items from response
     */
    private final ContentRequest.OnCompleteListener requestContentListOnCompleteListener = new ContentRequest.OnCompleteListener() {
        @Override
        public void onComplete(String response) {
            try {
                // parses Messy Truth content items response
                ContentItemParser contentItemParser = new ContentItemParser(response);
                ArrayList<ContentItem> contentList = new ArrayList<ContentItem>();
                contentList.addAll(contentItemParser.getContentItemList());

                // creates list sharethroughListAdapter on initial app load, or refreshes content list in sharethroughListAdapter
                // when user drags to refresh
                if (publisherListAdapter == null) {
                    setupListAdapter();
                } else {
                    publisherListAdapter.setContentList(contentList);
                    publisherListAdapter.notifyDataSetChanged();
                    sharethroughListAdapter.notifyDataSetChanged();
                }

            } catch (Exception e) {
                Logger.w(e.getMessage());
            }
        }
    };




    // Life cycle methods
//    @Override
//    protected void onDestroy() {
//        moPubAdAdapter.destroy();
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        // Set up request parameters
//        myRequestParameters = new RequestParameters.Builder()
//                .keywords("my targeting keywords")
//                .build();
//
//        // Request ads when the user returns to this activity
//        moPubAdAdapter.loadAds(MOPUB_AD_UNIT_ID, myRequestParameters);
//        super.onResume();
//    }
}


