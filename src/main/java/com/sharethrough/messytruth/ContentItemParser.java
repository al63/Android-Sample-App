package com.sharethrough.messytruth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Represents a feed of publisher content items with a getter to the feed list, uses the FeedAdapter to do the parsing
 */
public class ContentItemParser {

    private final ArrayList<ContentItem> contentItemList = new ArrayList<ContentItem>();

    public ContentItemParser(String response) throws Exception {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        FeedAdapter feedAdapter = new FeedAdapter(jsonArray);
        FeedItemAdapter feedItemAdapter;
        while ((feedItemAdapter = feedAdapter.getNextItem()) != null) {
            ContentItem contentItem = new ContentItem(feedItemAdapter);
            contentItemList.add(contentItem);
        }
    }

    public ArrayList<ContentItem> getContentItemList() {
        return contentItemList;
    }
}
