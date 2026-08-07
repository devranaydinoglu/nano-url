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
