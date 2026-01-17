package com.eureka.stocks.vo;

import java.util.Objects;

public class StockFundamentalsVO {
    private String ticker_symbol;
    private int sector_id;
    private float market_cap;
    private float currentRatio;

    private StockFundamentalsVO() {
    }

    public StockFundamentalsVO(String ticker_symbol) {
        this.ticker_symbol = ticker_symbol;
    }

    public String getTicker_symbol() {
        return ticker_symbol;
    }

    public int getSector_id() {
        return sector_id;
    }

    public void setSector_id(int sector_id) {
        this.sector_id = sector_id;
    }

    public float getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(float market_cap) {
        this.market_cap = market_cap;
    }

    public float getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(float currentRatio) {
        this.currentRatio = currentRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockFundamentalsVO that = (StockFundamentalsVO) o;
        return Objects.equals(ticker_symbol, that.ticker_symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticker_symbol);
    }

    @Override
    public String toString() {
        return "StockFundamentalsVO{" +
                "ticker_symbol='" + ticker_symbol + '\'' +
                '}';
    }
}
