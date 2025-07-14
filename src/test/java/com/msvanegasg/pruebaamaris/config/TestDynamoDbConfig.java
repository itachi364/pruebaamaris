package com.msvanegasg.pruebaamaris.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import static org.mockito.Mockito.mock;

@TestConfiguration
@Profile("test")
public class TestDynamoDbConfig {

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return mock(DynamoDbEnhancedClient.class);
    }
}
