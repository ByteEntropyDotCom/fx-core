package com.byteentropy.fx_core.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RateProviderService {

    // Simulating a list of supported currencies in the provider
    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP", "JPY", "CAD");

    private final Cache<String, BigDecimal> rateCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    public BigDecimal getLiveRate(String base, String target) {
        // Validate that both currencies are supported
        if (!SUPPORTED_CURRENCIES.contains(base.toUpperCase()) || 
            !SUPPORTED_CURRENCIES.contains(target.toUpperCase())) {
            throw new RuntimeException("Currency pair " + base + "/" + target + " not supported by provider.");
        }

        String pair = (base + target).toUpperCase();
        
        return rateCache.get(pair, k -> {
            // Mock mid-market rates
            return switch (k) {
                case "EURUSD" -> BigDecimal.valueOf(1.0850);
                case "GBPUSD" -> BigDecimal.valueOf(1.2640);
                case "USDJPY" -> BigDecimal.valueOf(151.20);
                default -> BigDecimal.valueOf(1.1025);
            };
        });
    }
}