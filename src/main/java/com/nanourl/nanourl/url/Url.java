package com.nanourl.nanourl.url;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("Url")
public class Url {

    @PrimaryKey("short_code")
    private String shortCode;

    @Column("original_url")
    private String originalUrl;

    @Column("snowflake_id")
    private Long snowflakeId;

    @Column("usage_counter")
    private int usageCounter;

    @Column("expires_at")
    private Long expiresAt;

    @Column("created_at")
    private Long createdAt;

    public Url(String shortCode, String originalUrl, Long snowflakeId, int usageCounter, Long expiresAt, Long createdAt) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.snowflakeId = snowflakeId;
        this.usageCounter = usageCounter;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Long getSnowflakeId() {
        return snowflakeId;
    }

    public int getUsageCounter() {
        return usageCounter;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setSnowflakeId(Long snowflakeId) {
        this.snowflakeId = snowflakeId;
    }

    public void setUsageCounter(int usageCounter) {
        this.usageCounter = usageCounter;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Url{" +
            "shortCode='" + shortCode + '\'' +
            ", originalUrl='" + originalUrl + '\'' +
            ", snowflakeId=" + snowflakeId +
            ", usageCounter=" + usageCounter +
            ", expiresAt=" + expiresAt +
            ", createdAt=" + createdAt +
            '}';
    }

}
