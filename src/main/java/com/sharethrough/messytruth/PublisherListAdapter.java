package com.sharethrough.messytruth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sharethrough.sample.R;
import com.sharethrough.sdk.IAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newuser on 6/12/15.
 */
public class PublisherListAdapter extends ArrayAdapter<ContentItem> {

    private final Context context;
    private List<ContentItem> contentList;

    public PublisherListAdapter(Context context, int textViewResourceId, ArrayList<ContentItem> contentList) {
        super(context, textViewResourceId, contentList);
        this.context = context;
        this.contentList = contentList;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public ContentItem getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = getPublisherContentItemView(position, parent);
        return rowView;
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
        ContentItem contentItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mt_list_view, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.article_title);
        titleView.setText(contentItem.getTitle());
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Picasso.with(context).load(contentItem.getThumbnailUrl()).fit().centerCrop().into(imageView);
        TextView source = (TextView) rowView.findViewById(R.id.source);
        source.setText(contentItem.getSource());
        TextView publishedDate = (TextView) rowView.findViewById(R.id.published_date);
        publishedDate.setText(contentItem.getPublishedDate() + "     " + position);

        return rowView;
    }

}
