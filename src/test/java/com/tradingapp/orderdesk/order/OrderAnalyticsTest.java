package com.tradingapp.orderdesk.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderAnalyticsTest {

    private OrderAnalytics analytics;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        analytics = new OrderAnalytics();
        orders = OrderSamples.orders();
    }

    @Test
    void buys_returnsOnlyBuyOrders() {
        // Act
        List<Order> result = analytics.buys(orders);

        // Assert
        assertThat(result)
                .hasSize(5)
                .allMatch(o -> o.side() == Side.BUY);
    }

    @Test
    void totalNotional_sumsAllOrders() {
        assertThat(analytics.totalNotional(orders))
                .isEqualByComparingTo("99328.00");
    }

    @Test
    void largestOrder_emptyList_returnsEmpty() {
        assertThat(analytics.largestOrder(List.of())).isEmpty();
    }

    @Test
    void symbols_returnsUniqueSortedSymbols() {
        assertThat(analytics.symbols(orders))
                .containsExactly("AAPL", "GOOG", "MSFT", "TSLA");
    }

    @Test
    void quantityBySymbol_sumsPerSymbol() {
        assertThat(analytics.quantityBySymbol(orders)).hasSize(4)
                .containsEntry("AAPL", 190)
                .containsEntry("GOOG", 60)
                .containsEntry("MSFT", 120)
                .containsEntry("TSLA", 15);
    }

    @Test
    void sortedByNotionalDesc_highestFirst() {
        assertThat(analytics.sortedByNotionalDesc(orders))
                .extracting(Order::id)
                .containsExactly(5L, 2L, 1L, 8L, 7L, 3L, 6L, 4L);
    }


    @Test
    void totalNotional_emptyList_returnsZero() {
        assertThat(analytics.totalNotional(List.of()))
                .isEqualByComparingTo("0.00");
    }

    @Test
    void buys_noBuyOrders_returnsEmpty() {
        List<Order> sellOrders = List.of(
                new Order(1, "AAPL", Side.SELL, 10, new BigDecimal("190.00"), "alice"),
                new Order(2, "MSFT", Side.SELL, 5, new BigDecimal("410.00"), "bob"));
        assertThat(analytics.buys(sellOrders)).isEmpty();
    }
}