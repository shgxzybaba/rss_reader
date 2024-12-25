package org.example.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Feed;
import org.example.domain.FeedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class FeedService {

    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String API_KEY = "pub_63270618bc3e406e4c60f62a65b1b926e5780";
    private static final String BASE_URL = "https://newsdata.io/api/1/latest";


    public List<Feed> getFeeds(Map<String, String> params) {
        logger.info("Getting feeds");
        var response = requestForFeeds(params);
        var feedResponse = parseResponse(response);
        return List.copyOf(feedResponse.isEmpty() ? List.of() : feedResponse.get().results());
    }

    public String requestForFeeds(Map<String, String> params) {
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getUri(params))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.debug("Status Code: {}" , response.statusCode());
            logger.debug("Response Body: {}" , response.body());
            return response.body();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private Optional<FeedResponse> parseResponse(String response) {
        try {
            FeedResponse feedResponse = mapper.readValue(new StringReader(response), FeedResponse.class);
            return Optional.of(feedResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private URI getUri( Map<String, String> params) {
        params.put("apikey", API_KEY);
        params.putIfAbsent("language", "en");
        params.putIfAbsent("size", "2");

        var uri = new StringBuilder(BASE_URL).append("?");
        for (var entry : params.entrySet()) {
            uri.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return URI.create(uri.toString());
    }
}
