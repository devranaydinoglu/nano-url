package com.nanourl.nanourl.url;

import jakarta.validation.constraints.NotNull;

public record CreateUrlResponse(@NotNull String shortUrl,
                                @NotNull String originalUrl,
                                @NotNull long expiresAt) {
}
