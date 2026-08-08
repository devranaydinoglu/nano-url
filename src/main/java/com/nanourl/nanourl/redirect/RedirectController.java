package com.nanourl.nanourl.redirect;

import com.nanourl.nanourl.url.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlService.findOriginalUrl(shortCode);

        if (originalUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        urlService.incrementUsageCounter(shortCode);

        return ResponseEntity
            .status(HttpStatus.MOVED_PERMANENTLY)
            .location(URI.create(originalUrl.get()))
            .build();
    }

}
