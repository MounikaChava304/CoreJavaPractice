package com.eureka.stocks.sorting;

import com.eureka.stocks.vo.StockFundamentalsVO;

import java.util.Comparator;

/***
 * Sorts Stock Fundamentals based on Market Cap Ascending
 */
public class SFMarketCapAscComparator implements Comparator<StockFundamentalsVO> {
    @Override
    public int compare(StockFundamentalsVO o1, StockFundamentalsVO o2) {
        return o1.getMarket_cap().compareTo(o2.getMarket_cap());
    }

}
