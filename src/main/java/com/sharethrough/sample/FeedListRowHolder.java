package com.sharethrough.sample;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class FeedListRowHolder extends RecyclerView.ViewHolder{
    protected ImageView thumbnail;
    protected TextView title;
    protected TextView description;

    public FeedListRowHolder(View view){
        super(view);

        thumbnail = (ImageView) view.findViewById(R.id.image);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
    }

}
