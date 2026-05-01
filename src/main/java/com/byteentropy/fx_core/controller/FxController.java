package com.byteentropy.fx_core.controller;

import com.byteentropy.fx_core.domain.Quote;
import com.byteentropy.fx_core.service.QuoteManager;
import com.byteentropy.fx_core.service.RateProviderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/fx")
public class FxController {

    private final RateProviderService rateProvider;
    private final QuoteManager quoteManager;

    public FxController(RateProviderService rateProvider, QuoteManager quoteManager) {
        this.rateProvider = rateProvider;
        this.quoteManager = quoteManager;
    }

    @GetMapping("/quote")
    public CompletableFuture<ResponseEntity<Quote>> requestQuote(
            @RequestParam String from,
            @RequestParam String to) {
        
        // Executes on a Virtual Thread
        return CompletableFuture.supplyAsync(() -> {
            var marketRate = rateProvider.getLiveRate(from, to);
            var quote = quoteManager.createQuote(from, to, marketRate);
            return ResponseEntity.ok(quote);
        });
    }

    @GetMapping("/verify/{quoteId}")
    public ResponseEntity<Quote> verifyQuote(@PathVariable UUID quoteId) {
        return ResponseEntity.ok(quoteManager.getValidQuote(quoteId));
    }
}