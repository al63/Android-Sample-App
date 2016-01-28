package com.sharethrough.messytruth;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Class for iterating the json feed response content items
 */
public class FeedAdapter {

    private final JSONArray entries;
    private int entryIndex = -1;

    public FeedAdapter(JSONArray entries) {
        this.entries = entries;
    }

    public FeedItemAdapter getNextItem() {
        this.entryIndex++;
        return getEntry();
    }

    public FeedItemAdapter getEntry() {
        try {
            if (entryIndex < entries.length()) {
                return new FeedItemAdapter(entries.getJSONObject(entryIndex));
            }
        } catch (JSONException e) {
            System.out.println("#FeedAdapter " + e);
        }

        return null;
    }


}
