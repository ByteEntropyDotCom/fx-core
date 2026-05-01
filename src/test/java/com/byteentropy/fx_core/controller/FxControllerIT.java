package com.byteentropy.fx_core.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FxControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNewQuote() throws Exception {
        mockMvc.perform(get("/v1/fx/quote")
                .param("from", "EUR")
                .param("to", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseCurrency").value("EUR"))
                .andExpect(jsonPath("$.targetCurrency").value("USD"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rate").isNumber());
    }

    @Test
    void shouldReturnErrorForSameCurrency() throws Exception {
        // Tests the validation in CurrencyPair record or logic
        mockMvc.perform(get("/v1/fx/quote")
                .param("from", "USD")
                .param("to", "USD"))
                .andExpect(status().isBadRequest());
    }
}