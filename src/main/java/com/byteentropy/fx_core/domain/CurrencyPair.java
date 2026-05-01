package com.byteentropy.fx_core.domain;

/**
 * Value Object representing a Currency Pair (e.g., EUR/USD).
 */
public record CurrencyPair(String base, String target) {
    
    public CurrencyPair {
        if (base == null || target == null) {
            throw new IllegalArgumentException("Currencies cannot be null");
        }
        base = base.toUpperCase().trim();
        target = target.toUpperCase().trim();
        
        if (base.equals(target)) {
            throw new IllegalArgumentException("Base and target currencies must be different");
        }
    }

    public String toStringPair() {
        return base + target;
    }

    @Override
    public String toString() {
        return base + "/" + target;
    }
}