package com.eureka.stocks.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class StocksPriceHistoryVO implements Comparable<StocksPriceHistoryVO>{

    private String tickerSymbol;
    private LocalDate tradingDate;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private StocksPriceHistoryVO() {
    }

    public StocksPriceHistoryVO(String tickerSymbol, LocalDate tradingDate) {
        this.tickerSymbol = tickerSymbol;
        this.tradingDate = tradingDate;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "StocksPriceHistoryVO{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tradingDate=" + tradingDate +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StocksPriceHistoryVO that = (StocksPriceHistoryVO) o;
        return Objects.equals(tickerSymbol, that.tickerSymbol) && Objects.equals(tradingDate, that.tradingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tickerSymbol, tradingDate);
    }

    /***
     * Natural Order to sort the Stock History List by TickerSymbol asc and then Trading Date desc
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(StocksPriceHistoryVO other) {
        return Comparator.comparing(StocksPriceHistoryVO::getTickerSymbol)
                .thenComparing(StocksPriceHistoryVO::getTradingDate).reversed()
                .compare(this, other);
    }
}
