package com.nanourl.nanourl.url;

public record CreateUrlResponse(String shortUrl,
                                String originalUrl,
                                long expiresAt) {
}
