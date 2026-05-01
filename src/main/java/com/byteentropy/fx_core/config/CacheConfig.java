package com.byteentropy.fx_core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures the CacheManager using Caffeine.
     * We define a primary cache for "live-rates" with a very short TTL
     * to ensure we are always trading on fresh data.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("live-rates");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    /**
     * Cache Builder Settings:
     * - Initial Capacity: 100 currency pairs
     * - Max Size: 500 currency pairs (to prevent memory leaks)
     * - Expire After Write: 5 seconds (Standard for high-volatility FX)
     */
    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .recordStats(); // Useful for observability-core metrics
    }
}
