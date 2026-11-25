package com.tradingapp.orderdesk.order;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderAnalytics {

    // 1. Only BUY orders
    public List<Order> buys(List<Order> orders) {
        return orders.stream().filter(o -> o.side() == Side.BUY).toList();
    }

    // 2. Unique symbols, alphabetical
    public List<String> symbols(List<Order> orders) {
        return orders.stream().map(Order::symbol).distinct().sorted().toList();
    }

    // 3. Sum of notional for all orders
    public BigDecimal totalNotional(List<Order> orders) {
        return orders.stream().map(Order::notional).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 4. Total quantity per symbol
    public Map<String, Integer> quantityBySymbol(List<Order> orders) {
        return orders.stream().collect(Collectors.groupingBy(Order::symbol, Collectors.summingInt(Order::quantity)));
    }

    // 5. Order with the highest notional
    public Optional<Order> largestOrder(List<Order> orders) {
        return orders.stream().max(Comparator.comparing(Order::notional));
    }

    // 6. Orders sorted by notional, highest first   
    public List<Order> sortedByNotionalDesc(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::notional).reversed()).toList();
    }
}