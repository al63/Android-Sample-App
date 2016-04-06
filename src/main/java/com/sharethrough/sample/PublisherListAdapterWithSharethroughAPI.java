package com.sharethrough.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sharethrough.messytruth.ContentItem;
import com.sharethrough.messytruth.PublisherListAdapter;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.IAdView;
import com.sharethrough.sdk.Sharethrough;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PublisherListAdapterWithSharethroughAPI extends ArrayAdapter<ContentItem> {
    private final Context context;
    private List<ContentItem> contentList;
    private Sharethrough mSharethrough;
    private int AD_POSITION = 1;

    public PublisherListAdapterWithSharethroughAPI(Context context, int textViewResourceId, ArrayList<ContentItem> contentList, Sharethrough mSharethrough) {
        super(context, textViewResourceId, contentList);
        this.context = context;
        this.contentList = contentList;
        this.mSharethrough = mSharethrough;
    }

    @Override
    public int getCount() {
        int count = contentList.size();
        return  mSharethrough.getNumberOfPlacedAds() + count;
    }

    @Override
    public ContentItem getItem(int position) {
        if (shouldReturnAd(position)) {
            return null;
        } else {
            return contentList.get(adjustedPosition(position));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean hasAdsToShow = mSharethrough.isAdAtPosition(position) || mSharethrough.getNumberOfAdsReadyToShow() > 0;
        if(shouldReturnAd(position) && hasAdsToShow)
        {
            BasicAdView adView;
            if(convertView instanceof IAdView)
            {
                adView = (BasicAdView) convertView;
            }
            else
            {
                adView = getBasicAdView();
                mSharethrough.putCreativeIntoAdView(adView, position);
            }
            return adView;
        }

        mSharethrough.fetchAdsIfReadyForMore();
        View rowView = getPublisherContentItemView(adjustedPosition(position), parent);
        return rowView;
    }

    public BasicAdView getBasicAdView() {
        BasicAdView adView = new BasicAdView(getContext());
        adView.prepareWithResourceIds(R.layout.mt_ad_view, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
        return adView;
    }

    /**
     * Converts position with ads TO content list position
     * @param position index
     * @return adjusted position
     */
    private int adjustedPosition(int position) {
        if (position <= AD_POSITION) {
            return position;
        } else {
            int numberOfAdsBeforePosition = mSharethrough.getNumberOfAdsBeforePosition(position);
            return position - numberOfAdsBeforePosition;
        }
    }

    private boolean shouldReturnAd(int position) {
        return position == AD_POSITION;
    }

    /**
     * Sets content list for list adapter
     * @param contentList
     */
    public void setContentList(ArrayList<ContentItem> contentList) {
        this.contentList = contentList;
    }

    /**
     * Gets the publisher content item for this feed position
     * @param position index of list
     * @param parent parent ViewGroup
     * @return View of the content item article
     */
    private View getPublisherContentItemView(int position, ViewGroup parent) {
        ContentItem contentItem = contentList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mt_list_view, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.article_title);
        titleView.setText(contentItem.getTitle());
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        if (contentItem.getThumbnailUrl() != null && !contentItem.getThumbnailUrl().isEmpty()) {
            Picasso.with(context).load(contentItem.getThumbnailUrl()).fit().centerCrop().into(imageView);
        }
        TextView source = (TextView) rowView.findViewById(R.id.source);
        source.setText(contentItem.getSource());
        TextView publishedDate = (TextView) rowView.findViewById(R.id.published_date);
        publishedDate.setText(contentItem.getPublishedDate() + "     " + position);

        return rowView;
    }
}
