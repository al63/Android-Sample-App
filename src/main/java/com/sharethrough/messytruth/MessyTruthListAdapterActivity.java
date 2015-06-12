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


public class MessyTruthListAdapterActivity extends Activity {
    private static final String STR_KEY = "16592761";
    private Sharethrough sharethrough;
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;
    private MessyTruthListAdapter messyTruthListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mt_activity_sharethrough_list_adapter);
        setSwipeRefreshLayout();

        setupSharethroughAds();
        setupListAdapter();

        retrievePublisherContentList();
    }

    /**
     * Set up sharethrough: (1) create sharethrough object, (2) set onstatuschangelistener
     */
    private void setupSharethroughAds() {
        Logger.enabled = false;
        sharethrough = new Sharethrough(context, STR_KEY, 1000);
        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                if (messyTruthListAdapter != null) {
                    messyTruthListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void noAdsToShow() {
            }
        });
    }

    private void setupListAdapter() {
        messyTruthListAdapter = new MessyTruthListAdapter(context, R.layout.mt_list_view, new ArrayList<ContentItem>());
        messyTruthListAdapter.setSharethrough(sharethrough);
        messyTruthListAdapter.setAdVariables(R.layout.mt_ad_view,
                R.id.title,
                R.id.description,
                R.id.advertiser,
                R.id.thumbnail,
                R.id.optout_icon,
                R.id.brand_logo);

        // create listview
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(messyTruthListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", messyTruthListAdapter.getItem(messyTruthListAdapter.adjustPositionDueToAdInsertion(position)).getLink());
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
     * Populates list messyTruthListAdapter with content items from response
     */
    private final ContentRequest.OnCompleteListener requestContentListOnCompleteListener = new ContentRequest.OnCompleteListener() {
        @Override
        public void onComplete(String response) {
            try {
                // parses Messy Truth content items response
                ContentItemParser contentItemParser = new ContentItemParser(response);
                ArrayList<ContentItem> contentList = new ArrayList<ContentItem>();
                contentList.addAll(contentItemParser.getContentItemList());

                // creates list messyTruthListAdapter on initial app load, or refreshes content list in messyTruthListAdapter
                // when user drags to refresh
                if (messyTruthListAdapter == null) {
                    setupListAdapter();
                } else {
                    messyTruthListAdapter.setContentList(contentList);
                    messyTruthListAdapter.notifyDataSetChanged();
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


