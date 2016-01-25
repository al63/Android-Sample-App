package com.sharethrough.messytruth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sharethrough.sample.R;
import com.sharethrough.sdk.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class PublisherListAdapterActivity extends Activity {
    private static final String PLACEMENT_KEY = "16592761";
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;
    private PublisherListAdapter publisherListAdapter;
    private SharethroughListAdapter sharethroughListAdapter;
    private Sharethrough sharethrough = null;
    private String savedSharethrough = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mt_activity_sharethrough_list_adapter);
        setSwipeRefreshLayout();
    }

    private void setupListAdapter() {
        publisherListAdapter = new PublisherListAdapter(context.getApplicationContext(), R.layout.mt_list_view, new ArrayList<ContentItem>());

        if (savedSharethrough != null) {
            // Initialize Sharethrough with serializedSharethrough and reset serializedSharethrough
            sharethrough = new Sharethrough(this, PLACEMENT_KEY, false, savedSharethrough);
            savedSharethrough = null;
        } else{
            sharethrough = new Sharethrough(this, PLACEMENT_KEY, false);
        }

        sharethroughListAdapter = new SharethroughListAdapter(context, publisherListAdapter, sharethrough, R.layout.mt_ad_view, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                sharethroughListAdapter.notifyDataSetChanged();
            }

            @Override
            public void noAdsToShow() {
            }
        });

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

    @Override
    protected void onResume() {
        setupListAdapter();
        retrievePublisherContentList();
        super.onResume();
    }

    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        savedSharethrough = (String) savedInstanceState.get("sharethrough");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("sharethrough", sharethrough.serialize());
        super.onSaveInstanceState(outState);
    }


}


