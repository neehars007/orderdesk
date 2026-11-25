package com.tradingapp.orderdesk.order;

public class OrderAnalyticsDemo {
    public static void main(String[] args) {
        var orders = OrderSamples.orders();
        var a = new OrderAnalytics();

        System.out.println("Buys: " + a.buys(orders).size());
        System.out.println("Symbols: " + a.symbols(orders));
        System.out.println("Total notional: " + a.totalNotional(orders));
        System.out.println("Qty by symbol: " + a.quantityBySymbol(orders));
        System.out.println("Largest: " + a.largestOrder(orders).map(Order::id).orElse(-1L));
        a.sortedByNotionalDesc(orders)
                .forEach(o -> System.out.println(o.id() + " " + o.notional()));
    }
}