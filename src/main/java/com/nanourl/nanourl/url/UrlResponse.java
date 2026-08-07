package com.nanourl.nanourl.url;

public record UrlResponse(String shortCode,
                          String originalUrl,
                          Long expiresAt,
                          Long createdAt) {
}
