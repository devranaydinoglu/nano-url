package com.nanourl.nanourl.url;

import com.nanourl.nanourl.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ApiResponse<UrlResponse>> findByShortCode(@PathVariable String shortCode) {
        UrlResponse foundUrl = urlService.findByShortCode(shortCode);

        if (foundUrl == null)
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("URL not found"));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(foundUrl, "URL retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateUrlResponse>> create(@RequestBody @Valid CreateUrlRequest req) {
        CreateUrlResponse response = urlService.create(req);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "Short URL created successfully"));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String shortCode) {
        urlService.delete(shortCode);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null, "Short URL deleted successfully"));
    }

}
