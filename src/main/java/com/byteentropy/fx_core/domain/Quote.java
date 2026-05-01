package com.byteentropy.fx_core.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Quote(
    UUID id,
    String baseCurrency,
    String targetCurrency,
    BigDecimal rate,
    Instant expiresAt
) {}