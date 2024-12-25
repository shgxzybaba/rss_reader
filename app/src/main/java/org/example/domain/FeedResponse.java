package org.example.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record FeedResponse(
        String status,
        Integer totalResults,
        List<Feed> results
) {

}
