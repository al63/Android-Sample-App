package com.sharethrough.messytruth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sharethrough.sample.R;
import com.sharethrough.sdk.IAdView;
import com.sharethrough.sdk.Sharethrough;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessyTruthListAdapter extends ArrayAdapter<ContentItem> {

    private final Context context;
    private List<ContentItem> contentList;
    private Sharethrough sharethrough;

    //Ad variables
    private int adLayoutResourceId;
    private int titleViewId;
    private int descriptionViewId;
    private int advertiserViewId;
    private int thumbnailViewId;
    private int optoutId;
    private int brandLogoId;

    private int articlesBeforeFirstAd = 1;
    private int articlesBetweenAds = 3;

    public MessyTruthListAdapter(Context context, int textViewResourceId, ArrayList<ContentItem> contentList) {
        super(context, textViewResourceId, contentList);
        this.context = context;
        this.contentList = contentList;
    }

    public void setSharethrough(Sharethrough sharethrough) {
        this.sharethrough = sharethrough;
    }

    @Override
    public int getCount() {
        int count = contentList.size();
        return  sharethrough.getNumberOfPlacedAds() + count;
    }

    @Override
    public ContentItem getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isAd(position)) {
            // we must check to make sure convertView is correct type, views may change type depending on ads availability
            if (convertView != null && !(convertView instanceof IAdView)) {
                convertView = null;
            }
            return getAd(position, (IAdView) convertView);
        } else {
            sharethrough.fetchAdsIfReadyForMore();
            View rowView = getPublisherContentItemView(adjustPositionDueToAdInsertion(position), parent);
            return rowView;
        }
    }

    /**
     * Sets content list for list adapter
     * @param contentList
     */
    public void setContentList(ArrayList<ContentItem> contentList) {
        this.contentList = contentList;
    }

    /**
     * Checks if given position should be an ad
     * @param position index of list
     * @return true if position should be an ad, false otherwise
     */
    private boolean isAd(int position) {
        /*if (position == 5) {
            return true;
        }
        return false;*/

        if (position < articlesBeforeFirstAd) {
            return false;
        }
        else if (position == articlesBeforeFirstAd) {
            return true;
        }
        else if ( ((position - (articlesBeforeFirstAd)) >= articlesBetweenAds) && ((position - (articlesBeforeFirstAd)) % (articlesBetweenAds+1)) == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets the publisher content item for this feed position
     * @param adjustedPosition index of list, already adjusted from sharethrough list adapter
     * @param parent parent ViewGroup
     * @return View of the content item article
     */
    private View getPublisherContentItemView(int adjustedPosition, ViewGroup parent) {
        ContentItem contentItem = getItem(adjustedPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mt_list_view, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.article_title);
        titleView.setText(contentItem.getTitle());
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Picasso.with(context).load(contentItem.getThumbnailUrl()).into(imageView);
        TextView source = (TextView) rowView.findViewById(R.id.source);
        source.setText(contentItem.getSource());
        TextView publishedDate = (TextView) rowView.findViewById(R.id.published_date);
        publishedDate.setText(contentItem.getPublishedDate() + adjustedPosition);

        return rowView;
    }

    private View getAd(int slotNumber, IAdView convertView) {
        return sharethrough.getAdView(context, slotNumber, adLayoutResourceId, titleViewId, descriptionViewId,
                advertiserViewId, thumbnailViewId, optoutId, brandLogoId, convertView).getAdView();
    }

    public void setAdVariables(int adLayoutResourceId,
                               int titleViewId,
                               int descriptionViewId,
                               int advertiserViewId,
                               int thumbnailViewId,
                               int optoutId,
                               int brandLogoId) {
        this.adLayoutResourceId = adLayoutResourceId;
        this.titleViewId = titleViewId;
        this.descriptionViewId = descriptionViewId;
        this.advertiserViewId = advertiserViewId;
        this.thumbnailViewId = thumbnailViewId;
        this.optoutId = optoutId;
        this.brandLogoId = brandLogoId;
    }

    //TODO figure out logic for this
    public int adjustPositionDueToAdInsertion(int position) {
        /*if( position >= 6 )
        {
            return position - 1;
        }

        return position;*/

        if (position <= articlesBeforeFirstAd) {
            return position;
        } else {
            int numberOfAdsBeforePosition = sharethrough.getNumberOfAdsBeforePosition(position);
            int adjustedPosition = position - numberOfAdsBeforePosition;

            return adjustedPosition;
        }
    }

}
