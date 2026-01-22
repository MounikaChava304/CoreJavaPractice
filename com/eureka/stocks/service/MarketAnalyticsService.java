package com.eureka.stocks.service;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.dao.StockFundamentalsDAO;
import com.eureka.stocks.dao.StockPriceHistoryDAO;
import com.eureka.stocks.sorting.SFMarketCapAscComparator;
import com.eureka.stocks.sorting.SubSectorNameComparator;
import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.StockFundamentalsVO;
import com.eureka.stocks.vo.StocksPriceHistoryVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MarketAnalyticsService {
    private LookUpDAO lookUpDAO; //instance variable, it is also a dependency
    private StockFundamentalsDAO stockFundamentalsDAO;
    private StockPriceHistoryDAO stockPriceHistoryDAO;

    private MarketAnalyticsService() {
    }

    /***
     * Parameterized Constructor that forces an instance of lookUpDao to be provided for the
     * MarketAnalyticsService to the function
     * @param lookUpDAO
     */
    public MarketAnalyticsService(LookUpDAO lookUpDAO) {
        this.lookUpDAO = lookUpDAO;
    }

    public MarketAnalyticsService(StockFundamentalsDAO stockFundamentalsDAO) {
        this.stockFundamentalsDAO = stockFundamentalsDAO;
    }

    public MarketAnalyticsService(StockPriceHistoryDAO stockPriceHistoryDAO) {
        this.stockPriceHistoryDAO = stockPriceHistoryDAO;
    }

    public MarketAnalyticsService(LookUpDAO lookUpDAO, StockFundamentalsDAO stockFundamentalsDAO, StockPriceHistoryDAO stockPriceHistoryDAO) {
        this.lookUpDAO = lookUpDAO;
        this.stockFundamentalsDAO = stockFundamentalsDAO;
        this.stockPriceHistoryDAO = stockPriceHistoryDAO;
    }

    /***
     * Business method that fetches all sectors from the database
     * @return
     */
    public List<SectorVO> getAllSectors() {
        List<SectorVO> allSectorsList = lookUpDAO.getAllSectors();
        Collections.sort(allSectorsList);
        return allSectorsList;
    }

    /***
     * Business method that fetches single sub sector from the database
     * @return
     */
    public SubSectorVO getSingleSubSector(int subSectorID) {
        return lookUpDAO.getSubSector(subSectorID);
    }

    /***
     * Business method that fetches all stock details from the database
     * @return
     */
    public List<StockFundamentalsVO> getStockFundamentals() {
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        Collections.sort(allStockFundamentalsList);//Natural Order by Ticker Symbol Ascending

//        allStockFundamentalsList.sort(new SFMarketCapAscComparator()); //Other Order by MarketCap Ascending
//        allStockFundamentalsList.sort(new SFMarketCapAscComparator().reversed()); //Other order by MarketCap Descending

//        Sort by Market Cap descending using anonymous inner class
//        allStockFundamentalsList.sort(new Comparator<StockFundamentalsVO>() {// Anonymous Inner Class Way
//            @Override
//            public int compare(StockFundamentalsVO o1, StockFundamentalsVO o2) {
//                return o1.getMarket_cap().compareTo(o2.getMarket_cap());
//            }
//        });

        //Same Comparator using Lambda Expression
        allStockFundamentalsList.sort((o1,o2) -> o2.getMarket_cap().compareTo(o1.getMarket_cap()));

        allStockFundamentalsList.sort(Comparator.comparing(stockFundamentalsVO -> stockFundamentalsVO.getMarket_cap())); //Using Lambda
        allStockFundamentalsList.sort(Comparator.comparing(StockFundamentalsVO::getMarket_cap).reversed()); //Using Method Reference

        StockFundamentalsVO snappleVO = new StockFundamentalsVO("SAAPL");
        snappleVO.setMarket_cap(new BigDecimal("3436888195072"));
        allStockFundamentalsList.add(snappleVO);

        //Sort by MarketCap desc and then by ticker_symbol desc
        allStockFundamentalsList.sort(Comparator.comparing(StockFundamentalsVO::getMarket_cap).reversed()
                .thenComparing(Comparator.comparing(StockFundamentalsVO::getTicker_symbol).reversed()));
        return allStockFundamentalsList;
    }

    public List<SubSectorVO> getAllSubSectors() {
        List<SubSectorVO> allSubSectorsList = lookUpDAO.getAllSubSectors();
        Collections.sort(allSubSectorsList);//Natural Order
//        Collections.sort(allSubSectorsList, new SubSectorNameComparator()); //OtherOrder sort by subsector name ascending
        allSubSectorsList.sort(new SubSectorNameComparator()); //Other way of writing above line

        //Sort by sector ID first and then SubSector Name - ( SQL Equivalent : order by sector_id, subSectorName desc)
        allSubSectorsList.sort(Comparator.comparing(SubSectorVO::getSectorID)
                .thenComparing(Comparator.comparing(SubSectorVO::getSubSectorName).reversed())
        );
        return allSubSectorsList;
    }

    /***
     * Business method that retrieves price history for a give stock in the given date range
     * @param tickerSymbol
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<StocksPriceHistoryVO> getPriceHistoryForSingleStock(String tickerSymbol, LocalDate fromDate, LocalDate toDate){
        List<StocksPriceHistoryVO> priceHistoryList = stockPriceHistoryDAO.getPriceHistoryForStock(tickerSymbol, fromDate, toDate);
        //Collections.sort(priceHistoryList);

        //SQL equivalent is order by close_price desc, high_price, trading_date desc
        priceHistoryList.sort(Comparator.comparing(StocksPriceHistoryVO::getClosePrice).reversed() //Closing Price desc
                .thenComparing(StocksPriceHistoryVO::getHighPrice) //High Price asc
                .thenComparing(Comparator.comparing(StocksPriceHistoryVO::getTradingDate).reversed())); //trading Date desc
        return priceHistoryList;
    }
}

