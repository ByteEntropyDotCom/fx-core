package com.byteentropy.fx_core.service;

import com.byteentropy.fx_core.domain.Quote;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyStressTest {

    @Test
    void testHighVolumeQuoteGeneration() throws InterruptedException {
        var engine = new TriangulationEngine();
        var manager = new QuoteManager(engine, new BigDecimal("0.0005"));
        var results = new ConcurrentLinkedQueue<Quote>();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 5000).forEach(i -> executor.submit(() -> {
                results.add(manager.createQuote("EUR", "USD", new BigDecimal("1.10")));
            }));
        }

        assertEquals(5000, results.size(), "All 5000 virtual threads should have generated a quote");
    }
}