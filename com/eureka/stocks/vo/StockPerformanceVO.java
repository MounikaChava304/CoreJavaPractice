package com.eureka.stocks.vo;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/***
 * This VO holds the performance of a Ticker for a particular year
 */

public class StockPerformanceVO implements Comparable<StockPerformanceVO>{
    private Integer year;
    private String tickerSymbol;
    private BigDecimal performance;
    private String state;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StockPerformanceVO{" +
                "year=" + year +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", performance=" + performance +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockPerformanceVO that = (StockPerformanceVO) o;
        return Objects.equals(getYear(), that.getYear()) && Objects.equals(getTickerSymbol(), that.getTickerSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYear(), getTickerSymbol());
    }

    /***
     * Establishing natural Order
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(StockPerformanceVO o) {
        return Comparator.comparing(StockPerformanceVO::getYear)
                .thenComparing(StockPerformanceVO::getTickerSymbol)
                        .compare(this,o);
    }
}
