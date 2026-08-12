package com.nanourl.nanourl.ratelimit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.YearMonth;

@Component
public class RateLimiter {

    private final static int MAX_REQUESTS = 5;
    private final StringRedisTemplate redisTemplate;

    public RateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isUrlCreateLimitExceeded(String remoteAddr) {
        String yearMonth = YearMonth.now().toString();
        String key = "rate-limit:url-create:" + remoteAddr + ":" + yearMonth;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == null)
            return false;

        if (count == 1)
            redisTemplate.expire(key, Duration.ofDays(31));

        return count > MAX_REQUESTS;
    }

}
