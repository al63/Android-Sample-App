package com.sharethrough.sample;

/**
 * Represents one publisher feed content item with getters to content item variables
 */
public class ContentItem {

    private final String title;
    private final String description;
    private final String thumbnailUrl;
    private final String source;
    private final String link;
    private final String publishedDate;

    public ContentItem(FeedItemAdapter feedItemAdapter) throws Exception {
        try {
            title = feedItemAdapter.getTitle();
            description = feedItemAdapter.getDescription();
            thumbnailUrl = feedItemAdapter.getThumbnailUrl();
            source = feedItemAdapter.getSource();
            link = feedItemAdapter.getLink();
            publishedDate = feedItemAdapter.getPublishedDate();
        } catch (Exception e) {
            System.out.println("#ContentItem: " + e);
            throw e;
        }

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSource() {
        return source;
    }

    public String getLink() {
        return link;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
}
