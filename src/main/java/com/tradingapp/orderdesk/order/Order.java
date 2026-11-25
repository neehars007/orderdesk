package com.tradingapp.orderdesk.order;

import java.math.BigDecimal;

public record Order(long id, String symbol, Side side, int quantity,
                    BigDecimal price, String trader) {

    public BigDecimal notional() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}