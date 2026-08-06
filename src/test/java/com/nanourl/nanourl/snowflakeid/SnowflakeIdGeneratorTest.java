package com.nanourl.nanourl.snowflakeid;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


public class SnowflakeIdGeneratorTest {

    @Test
    void invalidMachineIdUpperShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SnowflakeIdGenerator(1024)
        );
    }

    @Test
    void invalidMachineIdLowerShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SnowflakeIdGenerator(-1)
        );
    }

    @Test
    void generatedIdsShouldBeUnique() {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);

        Set<Long> ids = new HashSet<>();

        for (int i = 0; i < 100_000; i++) {
            assertTrue(ids.add(generator.nextId()));
        }
    }

    @Test
    void idsShouldBeIncreasing() {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);

        long previous = generator.nextId();

        for (int i = 0; i < 100_000; i++) {
            long current = generator.nextId();
            assertTrue(current > previous);
            previous = current;
        }
    }

    @Test
    void differentMachinesShouldGenerateDifferentIds() {
        SnowflakeIdGenerator g1 = new SnowflakeIdGenerator(1);
        SnowflakeIdGenerator g2 = new SnowflakeIdGenerator(2);

        assertNotEquals(g1.nextId(), g2.nextId());
    }

    @Test
    void concurrentGenerationShouldProduceUniqueIds() throws Exception {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Set<Long> ids = ConcurrentHashMap.newKeySet();

        List<Callable<Void>> tasks = IntStream.range(0, 100000)
                .mapToObj(i -> (Callable<Void>) () -> {
                    ids.add(generator.nextId());
                    return null;
                })
                .toList();

        executor.invokeAll(tasks);

        assertEquals(100000, ids.size());
    }

}
