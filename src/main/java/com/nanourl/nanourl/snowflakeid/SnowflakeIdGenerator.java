package com.nanourl.nanourl.snowflakeid;


/**
 * Generates 64-bit Snowflake IDs
 *
 * 1 bit: unused (sign bit)
 * 41 bits: milliseconds since fixed epoch
 * 10 bits: machine ID
 * 12 bits: sequence number
 */
public class SnowflakeIdGenerator {

    private static final long EPOCH = 1767225600000L; // 2026-01-01, 12:00:00 AM
    private static final long MACHINE_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_MACHINE_ID = (1L << MACHINE_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private final long machineId;
    private long lastMs;
    private long sequence;

    public SnowflakeIdGenerator(long machineId) {
        if (machineId < 0 || machineId > MAX_MACHINE_ID)
            throw new IllegalArgumentException("Invalid machine ID");
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currentMs = System.currentTimeMillis();

        if (currentMs < lastMs)
            throw new IllegalStateException("Clock moved backwards. Can't generate id.");

        if (currentMs == lastMs) {
            sequence++;
            if (sequence > MAX_SEQUENCE) {
                sequence = 0;
                currentMs = waitUntilNextMillis();
            }
        } else {
            sequence = 0;
        }

        lastMs = currentMs;

        return ((currentMs - EPOCH) << (MACHINE_ID_BITS + SEQUENCE_BITS))
                | (machineId << SEQUENCE_BITS)
                | sequence;
    }

    private long waitUntilNextMillis() {
        long ms = System.currentTimeMillis();
        while (ms <= lastMs)
            ms = System.currentTimeMillis();
        return ms;
    }

}
