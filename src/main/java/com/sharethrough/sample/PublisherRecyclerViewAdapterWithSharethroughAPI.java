package com.sharethrough.sample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sharethrough.sdk.BasicAdView;
import com.sharethrough.sdk.Sharethrough;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PublisherRecyclerViewAdapterWithSharethroughAPI extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<ContentItem> contentList;
    private Sharethrough mSharethrough;
    private int AD_POSITION = 1;
    private static final int AD = 1;
    private static final int ARTICLE = 0;

    public PublisherRecyclerViewAdapterWithSharethroughAPI(Context context, ArrayList<ContentItem> contentList, Sharethrough mSharethrough) {
        this.context = context;
        this.contentList = contentList;
        this.mSharethrough = mSharethrough;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case AD: return getAdViewHolder(parent);
            case ARTICLE: return getArticleViewHolder(parent);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        boolean hasAdsToShow = (mSharethrough.isAdAtPosition(position) || mSharethrough.getNumberOfAdsReadyToShow() > 0);
        if (shouldReturnAd(position) && hasAdsToShow) {
            return AD;
        } else {
            return ARTICLE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdViewHolder) {
            bindAdViewHolder((AdViewHolder)holder, position);
        } else if (holder instanceof ArticleViewHolder){
            bindArticleViewHolder((ArticleViewHolder)holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = contentList.size();
        return  mSharethrough.getNumberOfPlacedAds() + count;
    }

    private AdViewHolder getAdViewHolder(ViewGroup parent) {
        BasicAdView adView = new BasicAdView(parent.getContext());
        adView.prepareWithResourceIds(R.layout.mt_ad_view, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo, R.id.slug);
        AdViewHolder adViewHolder = new AdViewHolder(adView);
        return adViewHolder;
    }

    private ArticleViewHolder getArticleViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.mt_list_view, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(context, view);
        return articleViewHolder;
    }

    private void bindAdViewHolder(AdViewHolder holder, int position) {
        BasicAdView basicAdView = (BasicAdView)holder.itemView;
        mSharethrough.putCreativeIntoAdView(basicAdView, position);
    }

    private void bindArticleViewHolder(ArticleViewHolder holder, int position) {
        ContentItem contentItem = contentList.get(adjustedPosition(position));

        TextView titleView = holder.articleTitle;
        titleView.setText(contentItem.getTitle());
        ImageView imageView = holder.icon;
        if (contentItem.getThumbnailUrl() != null && !contentItem.getThumbnailUrl().isEmpty()) {
            Picasso.with(context).load(contentItem.getThumbnailUrl()).fit().centerCrop().into(imageView);
        }
        TextView source = holder.source;
        source.setText(contentItem.getSource());
        TextView publishedDate = holder.publishedDate;
        publishedDate.setText(contentItem.getPublishedDate() + "     " + position);
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        public AdViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        public TextView articleTitle;
        public ImageView icon;
        public TextView source;
        public TextView publishedDate;
        public ArticleViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.source = (TextView) itemView.findViewById(R.id.source);
            this.publishedDate = (TextView) itemView.findViewById(R.id.published_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("link", ((ContentItem) getItem(position)).getLink());
            context.startActivity(intent);
        }
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

    /**
     * Sets content list for list adapter
     * @param contentList
     */
    public void setContentList(ArrayList<ContentItem> contentList) {
        this.contentList = contentList;
    }

    public ContentItem getItem(int position) {
        if (shouldReturnAd(position)) {
            return null;
        } else {
            return contentList.get(adjustedPosition(position));
        }
    }

    private boolean shouldReturnAd(int position) {
        return position == AD_POSITION;
    }
}
