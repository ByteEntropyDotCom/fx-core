package com.byteentropy.fx_core.service;

import com.byteentropy.fx_core.domain.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuoteManagerTest {

    private QuoteManager quoteManager;
    private TriangulationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new TriangulationEngine();
        // Passing 0.0005 (5 bps) as default spread
        quoteManager = new QuoteManager(engine, new BigDecimal("0.0005"));
    }

    @Test
    void createAndVerifyQuoteTest() {
        BigDecimal marketRate = new BigDecimal("1.1025");
        Quote quote = quoteManager.createQuote("EUR", "USD", marketRate);

        assertNotNull(quote.id());
        assertEquals("EUR", quote.baseCurrency());
        
        Quote verified = quoteManager.getValidQuote(quote.id());
        assertEquals(quote.rate(), verified.rate());
    }

    @Test
    void shouldThrowExceptionWhenQuoteNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> quoteManager.getValidQuote(randomId));
    }
}
