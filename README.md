
# 💹 FX-Core | High-Precision Pricing Engine

[![FX-Core CI/CD](https://github.com/your-username/fx-core/actions/workflows/pipeline.yml/badge.svg)](https://github.com/your-username/fx-core/actions)
![Java Version](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)

**FX-Core** is a high-performance, cloud-native pricing service designed to serve as the "Price Oracle" for a multi-currency banking ecosystem. It handles the complex math of currency conversion, risk-adjusted spreads, and quote management using modern Java 21 features.

---

## 🏗️ Architecture

1.  **Request:** User asks for a rate.
2.  **Triangulate:** If the pair isn't direct, the engine finds a path through USD.
3.  **Spread:** The engine adds a 0.5% buffer to the mid-market rate.
4.  **Lock:** A unique UUID is generated and stored in the cache for 30 seconds.
5.  **Response:** The user receives a "Price Tag" they can act on.

---

## 🚀 Key Features

*   **Java 21 Virtual Threads:** Optimized for high-concurrency, allowing thousands of simultaneous quote requests without thread exhaustion.
*   **Triangulation Engine:** Automatically calculates rates for non-standard currency pairs (e.g., EUR/SGD) by triangulating through anchor currencies (USD).
*   **Automated Risk Spread:** Dynamically applies configurable buy/sell spreads to ensure bank profitability on every transaction.
*   **Smart Caching:** Uses **Caffeine L1 Cache** to provide 30-second quote "locks," protecting both the user from volatility and the bank from stale data.
*   **Fintech Precision:** Uses `BigDecimal` with `HALF_EVEN` (Banker's Rounding) for all calculations to prevent decimal drift.
*   **Standardized Error Handling:** Implements **RFC 9457 (Problem Details)** for consistent API error reporting.

## 🛠️ Technical Stack

*   **Language:** Java 21 (Project Loom)
*   **Framework:** Spring Boot 3.x
*   **Caching:** Caffeine
*   **Containerization:** Docker (Multi-stage builds)
*   **CI/CD:** GitHub Actions
*   **Testing:** JUnit 5 & AssertJ

## Docker 
```
# Build the image
docker build -t fx-core .

# Run the container
docker run -p 8085:8085 fx-core
```

## License 
MIT License
