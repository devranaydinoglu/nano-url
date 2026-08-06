package com.nanourl.nanourl.configuration;

import com.nanourl.nanourl.snowflakeid.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeIdGeneratorConfiguration {

    @Bean
    SnowflakeIdGenerator snowflakeIdGenerator(@Value("${machine.id}") long machineId) {
        return new SnowflakeIdGenerator(machineId);
    }

}
