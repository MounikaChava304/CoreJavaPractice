package com.eureka.stocks.vo;

import java.util.Objects;

public class TickerByYearVO {
    private Integer year;
    private String tickerSymbol;

    public TickerByYearVO(Integer year, String tickerSymbol) {
        this.year = year;
        this.tickerSymbol = tickerSymbol;
    }

    private TickerByYearVO() {
    }

    public Integer getYear() {
        return year;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TickerByYearVO that = (TickerByYearVO) o;
        return Objects.equals(getYear(), that.getYear()) && Objects.equals(getTickerSymbol(), that.getTickerSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYear(), getTickerSymbol());
    }

    @Override
    public String toString() {
        return "TickerByYearVO{" +
                "year=" + year +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                '}';
    }
}
