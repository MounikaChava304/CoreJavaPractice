package com.eureka.stocks.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class StockFundamentalsVO implements  Comparable<StockFundamentalsVO>{
    private String ticker_symbol;
    private int sector_id;
    private BigDecimal market_cap;
    private BigDecimal currentRatio;
    private int subSector_id;

    public int getSubSector_id() {
        return subSector_id;
    }

    public void setSubSector_id(int subSector_id) {
        this.subSector_id = subSector_id;
    }

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

    public BigDecimal getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(BigDecimal market_cap) {
        this.market_cap = market_cap;
    }

    public BigDecimal getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(BigDecimal currentRatio) {
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
                ", sector_id=" + sector_id +
                ", market_cap=" + market_cap +
                ", currentRatio=" + currentRatio +
                ", subSector_id=" + subSector_id +
                '}';
    }

    /***
     * Natural order is by ticker_symbol ascending
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(StockFundamentalsVO o) {
        return this.getTicker_symbol().compareTo(o.getTicker_symbol());
    }
}
