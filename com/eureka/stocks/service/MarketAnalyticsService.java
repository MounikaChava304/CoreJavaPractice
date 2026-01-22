package com.eureka.stocks.service;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.dao.StockFundamentalsDAO;
import com.eureka.stocks.sorting.SFMarketCapAscComparator;
import com.eureka.stocks.sorting.SubSectorNameComparator;
import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.StockFundamentalsVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MarketAnalyticsService {
    private LookUpDAO lookUpDAO; //instance variable, it is also a dependency
    private StockFundamentalsDAO stockFundamentalsDAO;

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

    public MarketAnalyticsService(LookUpDAO lookUpDAO, StockFundamentalsDAO stockFundamentalsDAO) {
        this.lookUpDAO = lookUpDAO;
        this.stockFundamentalsDAO = stockFundamentalsDAO;
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
}

