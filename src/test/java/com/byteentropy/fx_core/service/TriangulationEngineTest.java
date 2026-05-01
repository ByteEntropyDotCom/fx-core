package com.byteentropy.fx_core.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TriangulationEngineTest {
    private final TriangulationEngine engine = new TriangulationEngine();

    @Test
    void applySpreadTest() {
        BigDecimal midRate = new BigDecimal("1.0000");
        BigDecimal amount = new BigDecimal("10.00");
        BigDecimal spread = new BigDecimal("0.01");

        // FIX: Added 'amount' as second parameter
        BigDecimal result = engine.applySpread(midRate, amount, spread);
        
        assertEquals(new BigDecimal("1.010000"), result);
    }
}