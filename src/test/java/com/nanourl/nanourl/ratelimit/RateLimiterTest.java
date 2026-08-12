package com.nanourl.nanourl.ratelimit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimiterTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void firstRequestShouldBeAllowedAndExpirationShouldBeSet() {
        String remoteAddr = "192.168.1.1";

        when(valueOperations.increment(anyString())).thenReturn(1L);

        boolean result = rateLimiter.isUrlCreateLimitExceeded(remoteAddr);

        assertFalse(result);

        verify(valueOperations).increment("rate-limit:url-create:192.168.1.1:" + YearMonth.now());
        verify(redisTemplate).expire(
            "rate-limit:url-create:192.168.1.1:" + YearMonth.now(),
            Duration.ofDays(31)
        );
    }

    @Test
    void fiveRequestsShouldBeAllowed() {
        String remoteAddr = "192.168.1.1";

        when(valueOperations.increment(anyString())).thenReturn(5L);

        boolean result = rateLimiter.isUrlCreateLimitExceeded(remoteAddr);

        assertFalse(result);
    }

    @Test
    void sixthRequestShouldBeRejected() {
        String remoteAddr = "192.168.1.1";

        when(valueOperations.increment(anyString())).thenReturn(6L);

        boolean result = rateLimiter.isUrlCreateLimitExceeded(remoteAddr);

        assertTrue(result);
    }

    @Test
    void differentIpsShouldUseSeparateCounters() {
        String firstIp = "192.168.1.1";
        String secondIp = "192.168.1.2";

        when(valueOperations.increment(anyString())).thenReturn(1L);

        assertFalse(rateLimiter.isUrlCreateLimitExceeded(firstIp));
        assertFalse(rateLimiter.isUrlCreateLimitExceeded(secondIp));

        verify(valueOperations).increment(
            "rate-limit:url-create:" + firstIp + ":" + YearMonth.now()
        );

        verify(valueOperations).increment(
            "rate-limit:url-create:" + secondIp + ":" + YearMonth.now()
        );
    }

    @Test
    void redisFailureShouldAllowRequest() {
        String remoteAddr = "192.168.1.1";

        when(valueOperations.increment(anyString())).thenReturn(null);

        boolean result = rateLimiter.isUrlCreateLimitExceeded(remoteAddr);

        assertFalse(result);

        verify(redisTemplate, never()).expire(anyString(), any(Duration.class));
    }
}
