package com.sharethrough.sample;

public class FeedItem {
    public final String title;
    public final String description;
    public final int imageResourceId;

    public FeedItem(int imageResourceId, String title, String description) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
}
