package com.nanourl.nanourl.url;

import com.nanourl.nanourl.snowflakeid.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final SnowflakeIdGenerator idGenerator;
    private final String baseUrl;

    public UrlService(UrlRepository urlRepository,
                      SnowflakeIdGenerator idGenerator,
                      @Value("${app.base-url}") String baseUrl) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
        this.baseUrl = baseUrl;
    }

    public CreateUrlResponse create(CreateUrlRequest req) {
        Long snowflakeId = idGenerator.nextId();
        String shortCode = generateShortCode(snowflakeId);
        long expiresAt = calculateExpirationDate(req.validityDuration());
        long createdAt = System.currentTimeMillis();

        Url url = new Url(shortCode, req.originalUrl(), snowflakeId, 0, expiresAt, createdAt);
        urlRepository.save(url);

        return new CreateUrlResponse(
                baseUrl + "/" + shortCode,
                req.originalUrl(),
                expiresAt
        );
    }

    protected String generateShortCode(Long snowflakeId) {
        char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        int BASE = BASE62.length;

        if (snowflakeId < 0) {
            throw new IllegalArgumentException("Value must be non-negative.");
        }

        if (snowflakeId == 0) {
            return String.valueOf(BASE62[0]);
        }

        StringBuilder encoded = new StringBuilder();

        while (snowflakeId > 0) {
            int remainder = (int) (snowflakeId % BASE);
            encoded.append(BASE62[remainder]);
            snowflakeId /= BASE;
        }

        return encoded.reverse().toString();
    }

    protected long calculateExpirationDate(Duration validityDuration) {
        return Instant.now().plus(validityDuration).toEpochMilli();
    }

}
