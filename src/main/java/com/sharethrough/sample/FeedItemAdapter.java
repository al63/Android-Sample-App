package com.sharethrough.sample;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Feed Item Adapter is used to parse specific data for a given content item
 */
public class FeedItemAdapter {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "desc";
    private static final String THUMBNAILURL = "image_url";
    private static final String SOURCE = "source";
    private static final String LINK = "link_to_original";
    private static final String PUBLISHED_DATE = "published_date_human_readable";

    private JSONObject jsonObject;


    public FeedItemAdapter(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getTitle() {
        return getString(jsonObject, TITLE);
    }

    public String getDescription() {
        return getString(jsonObject, DESCRIPTION);
    }

    public String getThumbnailUrl() {
        return getString(jsonObject, THUMBNAILURL);
    }

    public String getSource() {
        return getString(jsonObject, SOURCE);
    }

    public String getLink() {
        return getString(jsonObject, LINK);
    }

    public String getPublishedDate() {
        return getString(jsonObject, PUBLISHED_DATE);
    }
    public String getString(JSONObject jsonObject, String fieldName) {
        try {
            if (jsonObject.has(fieldName) && !jsonObject.isNull(fieldName)) {
                return jsonObject.getString(fieldName);
            }
        } catch (JSONException e) {

        }
        return null;
    }
}
