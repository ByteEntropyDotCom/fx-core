package com.byteentropy.fx_core.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairTest {

    @Test
    void shouldNormalizeToUpperCase() {
        CurrencyPair pair = new CurrencyPair("eur", "usd");
        assertEquals("EUR", pair.base());
        assertEquals("USD", pair.target());
    }

    @Test
    void shouldThrowExceptionForSameCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new CurrencyPair("USD", "USD"));
    }

    @Test
    void shouldThrowExceptionForNullCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new CurrencyPair(null, "USD"));
    }
}