package com.sharethrough.messytruth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.sharethrough.sample.R;
import com.sharethrough.sdk.IAdView;
import com.sharethrough.sdk.Logger;
import com.sharethrough.sdk.Sharethrough;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SharethroughListAdapter extends BaseAdapter {

    private final Context context;
    private Sharethrough sharethrough;
    private Adapter originalAdapter;
    private String placementKey;

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

    public SharethroughListAdapter(Context context, Adapter originalAdapter, String placementKey) {
        this.context = context;
        this.originalAdapter = originalAdapter;
        this.placementKey = placementKey;

        Logger.enabled = true;
        sharethrough = new Sharethrough(context, placementKey, 1000);
        sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
            @Override
            public void newAdsToShow() {
                handleNewAds();
            }
            @Override
            public void noAdsToShow() {
            }
        });
    }

    void handleNewAds() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  sharethrough.getNumberOfPlacedAds() + originalAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        if (isAd(position)) {
            return null;
        }
        return originalAdapter.getItem(adjustPositionDueToAdInsertion(position));
    }

    @Override
    public long getItemId(int position) {
        if (isAd(position)) {
            return 0;
        }
        return originalAdapter.getItemId(adjustPositionDueToAdInsertion(position));

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
            return originalAdapter.getView(adjustPositionDueToAdInsertion(position), convertView, parent);

        }
    }

    /**
     * Checks if given position should be an ad
     * @param position index of list
     * @return true if position should be an ad, false otherwise
     */
    private boolean isAd(int position) {
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
     * Get ad view from Sharethrough
     * @param slotNumber position
     * @param convertView convertView
     * @return ad view
     */
    private View getAd(int slotNumber, IAdView convertView) {
        return sharethrough.getAdView(context, slotNumber, adLayoutResourceId, titleViewId, descriptionViewId,
                advertiserViewId, thumbnailViewId, optoutId, brandLogoId, convertView).getAdView();
    }

    /**
     * Converts Sharethrough adapter position TO publisher's adapter position
     * @param position index
     * @return adjusted position
     */
    public int adjustPositionDueToAdInsertion(int position) {
        if (position <= articlesBeforeFirstAd) {
            return position;
        } else {
            int numberOfAdsBeforePosition = sharethrough.getNumberOfAdsBeforePosition(position);
            return position - numberOfAdsBeforePosition;
        }
    }

    /**
     * Set up Sharethrough view bindings
     * @param adLayoutResourceId resourceId
     * @param titleViewId titleId
     * @param descriptionViewId descId
     * @param advertiserViewId adViewId
     * @param thumbnailViewId thumbId
     * @param optoutId optoutId
     * @param brandLogoId brandLogoId
     */
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

}
