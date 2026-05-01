package com.byteentropy.fx_core.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;

class PrecisionMathTest {
    private final TriangulationEngine engine = new TriangulationEngine();

    @Test
    void testRoundingConsistency() {
        BigDecimal rawRate = new BigDecimal("1.123456789");
        BigDecimal testAmount = new BigDecimal("100.00");
        BigDecimal spread = new BigDecimal("0.0005");

        // FIX: Passing all 3 required arguments
        BigDecimal rateWithSpread = engine.applySpread(rawRate, testAmount, spread);
        
        BigDecimal result = testAmount.multiply(rateWithSpread).setScale(2, RoundingMode.HALF_EVEN);
        
        assertNotNull(result);
        assertEquals(new BigDecimal("112.40"), result);
    }
}