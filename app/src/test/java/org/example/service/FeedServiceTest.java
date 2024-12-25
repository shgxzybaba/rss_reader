package org.example.service;

import junit.framework.TestCase;

import java.util.HashMap;

public class FeedServiceTest extends TestCase {

    public void testGetFeeds() {
        var feedService = new FeedService();
        var feeds = feedService.getFeeds(new HashMap<>());
        assertFalse(feeds.isEmpty());
    }

    public void testRequestForFeeds() {
        var feedService = new FeedService();
        var feeds = feedService.requestForFeeds(new HashMap<>());
        assertFalse(feeds.isEmpty());
        assertFalse(feeds.isBlank());
    }
}