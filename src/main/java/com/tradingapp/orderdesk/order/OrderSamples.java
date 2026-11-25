package com.tradingapp.orderdesk.order;


import java.math.BigDecimal;
import java.util.List;

public final class OrderSamples {
    private OrderSamples() {}

    public static List<Order> orders() {
        return List.of(
                new Order(1, "AAPL", Side.BUY, 100, new BigDecimal("190.50"), "alice"),
                new Order(2, "MSFT", Side.SELL, 50, new BigDecimal("410.00"), "bob"),
                new Order(3, "AAPL", Side.SELL, 30, new BigDecimal("191.00"), "alice"),
                new Order(4, "GOOG", Side.BUY, 20, new BigDecimal("172.25"), "carol"),
                new Order(5, "MSFT", Side.BUY, 70, new BigDecimal("408.75"), "alice"),
                new Order(6, "TSLA", Side.BUY, 15, new BigDecimal("245.10"), "bob"),
                new Order(7, "GOOG", Side.SELL, 40, new BigDecimal("173.00"), "carol"),
                new Order(8, "AAPL", Side.BUY, 60, new BigDecimal("189.90"), "bob")
        );
    }
}