package com.byteentropy.fx_core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record Rate(
    String pair, // e.g., EURUSD
    BigDecimal midRate,
    Instant timestamp
) {}