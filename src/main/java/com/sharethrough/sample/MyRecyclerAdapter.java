package com.sharethrough.sample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.view.LayoutInflater;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedItem> feedItemList;
    private Sharethrough sharethrough;
    private enum ViewType
    {
        FEED,
        BASIC_AD
    }

    public MyRecyclerAdapter( List<FeedItem> feedItemList, Sharethrough sharethrough) {
        this.feedItemList = feedItemList;
        this.sharethrough = sharethrough;
    }

    @Override
    public int getItemViewType(int position){
        if( position == 0 || position == 6 )
        {
            return ViewType.BASIC_AD.ordinal();
        }
        return ViewType.FEED.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if ( viewType == ViewType.BASIC_AD.ordinal() ){

            BasicAdView adView = new BasicAdView(viewGroup.getContext());
            adView.prepareWithResourceIds(R.layout.basic_ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);

            BasicAdListRowHolder adHolder = new BasicAdListRowHolder((View)adView);
            return adHolder;
        }
        else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, null);
            FeedListRowHolder feedHolder = new FeedListRowHolder(v);
            return feedHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position+1-sharethrough.getNumberOfAdsBeforePosition(position));

        if( holder instanceof BasicAdListRowHolder)
        {
            BasicAdView basicAdView = (BasicAdView)holder.itemView;
            sharethrough.putCreativeIntoAdView(basicAdView, position);
        }
        else {
            FeedListRowHolder feedHolder = (FeedListRowHolder) holder;
            feedHolder.thumbnail.setImageResource(feedItem.imageResourceId);
            feedHolder.title.setText(feedItem.title);
            feedHolder.description.setText(feedItem.description);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
