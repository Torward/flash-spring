package ru.lomov.flashbackend.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.ImperativeSpanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Distributed Tracing configuration for Flash-Spring microservices.
 * Uses Micrometer Tracing with Zipkin backend for observability.
 */
@Configuration
public class TracingConfig {

    /**
     * Custom span customizer to add application-specific tags
     */
    @Bean
    public ImperativeSpanCustomizer imperativeSpanCustomizer() {
        return new ImperativeSpanCustomizer() {
            @Override
            public Span name(String name) {
                return tracer().nextSpan().name(name);
            }

            @Override
            public Span tag(String key, String value) {
                tracer().currentSpan().tag(key, value);
                return tracer().currentSpan();
            }

            @Override
            public void annotate(String value) {
                tracer().currentSpan().annotate(value);
            }
        };
    }

    /**
     * Example method to demonstrate manual tracing
     * Use this pattern in service methods that need custom spans
     */
    public void traceableMethod(Tracer tracer, String operationName) {
        Span span = tracer.nextSpan().name(operationName).start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            // Add custom tags
            span.tag("operation", operationName);
            span.tag("thread", Thread.currentThread().getName());
            
            // Your business logic here
            // ...
            
            // Annotate events
            span.annotate("operation.completed");
        } finally {
            span.end();
        }
    }
}
