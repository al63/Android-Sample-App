package com.sharethrough.sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Represents a feed of publisher content items with a getter to the feed list, uses the FeedAdapter to do the parsing
 */
public class ContentItemParser {

    private final ArrayList<ContentItem> contentItemList = new ArrayList<ContentItem>();
    public static String feed = "{\"articles\":[{\"title\":\"Sneaker Politics x Saucony Courageous \\\"The Jackson\\\" Teaser\",\"desc\":\"A touch of gold is all you need. \",\"image_url\":\"http://hypebeast.com/image/2015/11/sneaker-politics-saucony-courageous-the-jackson-teaser-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115296\",\"published_date\":\"2015-11-11T01:09:35.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"adidas Originals ZX Flux Racer Asym\",\"desc\":\"The ZX Flux gets a sleek makeover.\",\"image_url\":\"http://hypebeast.com/image/2015/11/adidas-originals-zx-flux-racer-asym-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115156\",\"published_date\":\"2015-11-11T00:50:04.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"A Definitive History of Black Friday Jordan Releases\",\"desc\":\"Spanning 13 years of Jordanbeast heaven.\",\"image_url\":\"http://hypebeast.com/image/2015/11/jordan-black-friday-editorial-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1113106\",\"published_date\":\"2015-11-11T00:39:26.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"No I.D. \\u0026 Vince Staples Bring Together Generations of Musical Potential\",\"desc\":\"From the Westside to Chicago. \",\"image_url\":\"http://hypebeast.com/image/2015/11/no-id-vince-staples-generations-musical-potential-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115215\",\"published_date\":\"2015-11-11T00:30:57.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"Alife x Footpatrol 2015 Fall/Winter Collection\",\"desc\":\"London and the Big Apple unite.\",\"image_url\":\"http://hypebeast.com/image/2015/11/alife-footpatrol-2015-fall-winter-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115161\",\"published_date\":\"2015-11-11T00:26:55.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"'Finding Dory' Official Trailer\",\"desc\":\"\\\"She just kept swimming...\\\"\",\"image_url\":\"http://hypebeast.com/image/2015/11/pixar-finding-dory-trailer-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115154\",\"published_date\":\"2015-11-11T00:13:00.000Z\",\"published_date_human_readable\":\"November 11, 2015\"},{\"title\":\"NikeLab WMNS Roshe One Diamondback \\\"Triple White\\\"\",\"desc\":\"Inspired by the diamondback rattlesnake.\",\"image_url\":\"http://hypebeast.com/image/2015/11/nike-womens-roshe-one-diamondback-triple-white-0.jpg\",\"source\":\"Hypebeast\",\"link_to_original\":\"http://hypebeast.com/?post=1115209\",\"published_date\":\"2015-11-10T23:52:14.000Z\",\"published_date_human_readable\":\"November 10, 2015\"}]}";

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
