package com.byteentropy.fx_core.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TriangulationEngine {

    private static final int PRECISION = 6;
    private static final BigDecimal MAX_LIQUIDITY_LIMIT = new BigDecimal("1000000.00");

    public BigDecimal calculateCrossRate(BigDecimal baseToAnchor, BigDecimal targetToAnchor) {
        if (targetToAnchor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Target anchor rate must be positive");
        }
        return baseToAnchor.divide(targetToAnchor, PRECISION, RoundingMode.HALF_EVEN);
    }

    /**
     * Updated method signature with 3 parameters to support Liquidity Checks.
     */
    public BigDecimal applySpread(BigDecimal rate, BigDecimal amount, BigDecimal spreadPercent) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }

        if (amount.compareTo(MAX_LIQUIDITY_LIMIT) > 0) {
            throw new RuntimeException("Amount exceeds automated liquidity limit.");
        }

        return rate.multiply(BigDecimal.ONE.add(spreadPercent))
                   .setScale(PRECISION, RoundingMode.HALF_EVEN);
    }
}