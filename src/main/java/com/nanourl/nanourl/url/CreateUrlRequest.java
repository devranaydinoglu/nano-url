package com.nanourl.nanourl.url;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record CreateUrlRequest(@NotNull String originalUrl, @NotNull Duration validityDuration) {
}
