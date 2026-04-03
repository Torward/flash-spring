package ru.lomov.flashbackend.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Resilience4j Circuit Breaker configuration for Flash-Spring microservices.
 * Provides fault tolerance and resilience patterns.
 */
@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .waitDurationInOpenState(Duration.ofSeconds(10))
            .failureRateThreshold(50)
            .recordExceptions(
                java.io.IOException.class,
                java.util.concurrent.TimeoutException.class,
                org.springframework.web.client.ResourceAccessException.class
            )
            .build();

        return CircuitBreakerRegistry.of(customConfig);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(3))
            .cancelRunningFuture(true)
            .build();

        return TimeLimiterRegistry.of(timeLimiterConfig);
    }

    @Bean
    public Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory(
        CircuitBreakerRegistry circuitBreakerRegistry,
        TimeLimiterRegistry timeLimiterRegistry
    ) {
        return new Resilience4JCircuitBreakerFactory(
            circuitBreakerRegistry,
            timeLimiterRegistry
        );
    }
}
