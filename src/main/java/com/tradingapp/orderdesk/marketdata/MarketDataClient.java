package com.tradingapp.orderdesk.marketdata;

import java.math.BigDecimal;

public interface MarketDataClient {
    BigDecimal lastPrice(String symbol);
}
