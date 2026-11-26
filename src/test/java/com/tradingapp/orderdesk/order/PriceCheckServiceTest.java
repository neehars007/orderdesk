package com.tradingapp.orderdesk.order;

import com.tradingapp.orderdesk.marketdata.MarketDataClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCheckServiceTest {

    @Mock
    private MarketDataClient marketData;

    @InjectMocks
    private PriceCheckService service;

    private Order aaplOrderAt(String price) {
        return new Order(1, "AAPL", Side.BUY, 100, new BigDecimal(price), "alice");
    }

    @Test
    void isPriceReasonable_within5Percent_returnsTrue() {
        // Arrange
        when(marketData.lastPrice("AAPL")).thenReturn(new BigDecimal("190.00"));

        // Act
        boolean result = service.isPriceReasonable(aaplOrderAt("195.00"));

        // Assert
        assertThat(result).isTrue();
        verify(marketData).lastPrice("AAPL");
    }

    @Test
    void isPriceReasonable_moreThan5PercentAway_returnsFalse() {
        when(marketData.lastPrice("AAPL")).thenReturn(new BigDecimal("190.00"));

        assertThat(service.isPriceReasonable(aaplOrderAt("210.00"))).isFalse();
    }

    @Test
    void isPriceReasonable_noMarketPrice_throws() {
        when(marketData.lastPrice("AAPL")).thenReturn(null);

        assertThatThrownBy(() -> service.isPriceReasonable(aaplOrderAt("190.00")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("AAPL");
    }

    @Test
    void isPriceReasonable_exactly5Percent_returnsTrue() {
        // Arrange
        when(marketData.lastPrice("AAPL")).thenReturn(new BigDecimal("190.00"));

        // Act
        boolean result = service.isPriceReasonable(aaplOrderAt("199.50"));

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isPriceReasonable_priceTooLow_returnsFalse() {
        // Arrange
        when(marketData.lastPrice("AAPL")).thenReturn(new BigDecimal("190.00"));

        // Act
        boolean result = service.isPriceReasonable(aaplOrderAt("170.00"));

        // Assert
        assertThat(result).isFalse();
    }
}