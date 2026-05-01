package com.byteentropy.fx_core.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RateProviderResilienceTest {

    @Test
    void shouldHandleProviderFailure() {
        RateProviderService provider = new RateProviderService();
        
        // This should now correctly throw an exception because XYZ/ABC isn't in the supported list
        Exception exception = assertThrows(RuntimeException.class, () -> {
            provider.getLiveRate("XYZ", "ABC");
        });

        assertTrue(exception.getMessage().contains("not supported"));
    }
}