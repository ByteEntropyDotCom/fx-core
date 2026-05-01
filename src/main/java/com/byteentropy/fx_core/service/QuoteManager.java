package com.byteentropy.fx_core.service;

import com.byteentropy.fx_core.domain.Quote;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class QuoteManager {

    private final TriangulationEngine engine;
    private final BigDecimal defaultSpread;
    
    private final Cache<UUID, Quote> activeQuotes = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();

    public QuoteManager(TriangulationEngine engine, 
                        @Value("${fx.default-spread}") BigDecimal defaultSpread) {
        this.engine = engine;
        this.defaultSpread = defaultSpread;
    }

    public Quote createQuote(String base, String target, BigDecimal marketRate) {
        BigDecimal finalRate = engine.applySpread(marketRate, BigDecimal.ONE, defaultSpread);
        
        Quote quote = new Quote(
            UUID.randomUUID(),
            base,
            target,
            finalRate,
            Instant.now().plusSeconds(30)
        );
        activeQuotes.put(quote.id(), quote);
        return quote;
    }

    public Quote getValidQuote(UUID id) {
        Quote quote = activeQuotes.getIfPresent(id);
        if (quote == null) {
            throw new RuntimeException("Quote expired or not found");
        }
        return quote;
    }
}