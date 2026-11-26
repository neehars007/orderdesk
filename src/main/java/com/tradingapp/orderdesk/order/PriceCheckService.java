package com.tradingapp.orderdesk.order;

import com.tradingapp.orderdesk.marketdata.MarketDataClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCheckService {

    private static final BigDecimal MAX_DEVIATION = new BigDecimal("0.05");

    private final MarketDataClient marketData;

    public PriceCheckService(MarketDataClient marketData) {
        this.marketData = marketData;
    }

    public boolean isPriceReasonable(Order order) {
        BigDecimal market = marketData.lastPrice(order.symbol());
        if (market == null) {
            throw new IllegalStateException("No market price for " + order.symbol());
        }
        BigDecimal deviation = order.price().subtract(market).abs()
                .divide(market, 4, RoundingMode.HALF_UP);
        return deviation.compareTo(MAX_DEVIATION) <= 0;
    }
}