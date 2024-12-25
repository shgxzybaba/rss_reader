package org.example.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Feed(String title, @JsonAlias("source_name") String sourceName, String description) {
    @Override
    public String toString() {
        return String.format("""
                Title: %s
                Source: %s
                Description: %s
                """, title, sourceName, description);
    }
}
