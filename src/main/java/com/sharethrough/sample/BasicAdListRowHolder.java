package com.sharethrough.sample;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class BasicAdListRowHolder extends RecyclerView.ViewHolder{
    protected TextView title;
    protected TextView description;

    public BasicAdListRowHolder(View view){
        super(view);

        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
    }

}
