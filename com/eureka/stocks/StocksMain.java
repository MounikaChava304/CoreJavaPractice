package com.eureka.stocks;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.dao.StockFundamentalsDAO;
import com.eureka.stocks.dao.StockPriceHistoryDAO;
import com.eureka.stocks.exception.StockException;
import com.eureka.stocks.service.MarketAnalyticsService;
import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.StockFundamentalsVO;
import com.eureka.stocks.vo.StocksPriceHistoryVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StocksMain {
    public static void main(String[] args) {
        try (LookUpDAO lookUpDAO = new LookUpDAO();
             StockFundamentalsDAO stockFundamentalsDAO = new StockFundamentalsDAO();
             StockPriceHistoryDAO stockPriceHistoryDAO = new StockPriceHistoryDAO()) { //try-with resources construct

            //Injecting an instance of lookUpDAO to the constructor of MarketAnalyticsService,
            // as it is a dependency that is needed for the MarketAnalyticsService instance to function.
            MarketAnalyticsService marketAnalyticsService = new MarketAnalyticsService(lookUpDAO, stockFundamentalsDAO, stockPriceHistoryDAO);

            //Get all sector information from the database
            List<SectorVO> allSectorsList = marketAnalyticsService.getAllSectors();
            System.out.println("Number of sectors returned from db is " + allSectorsList.size());

            int subSectorID = 283;
            //Get subsector details from the database for the subsector id listed above
            SubSectorVO subSector = marketAnalyticsService.getSingleSubSector(subSectorID);
            System.out.println("SubSector retrieved is : " + subSector);

            //Get Stocks fundamentals details from the database
            List<StockFundamentalsVO> allStockFundamentals = marketAnalyticsService.getStockFundamentals();
            System.out.println("Number of stocks returned from db is " + allStockFundamentals.size());
//            for (StockFundamentalsVO eachStock : allStockFundamentals) {
//                System.out.println("Stock Symbol : " + eachStock.getTicker_symbol() + " , Sector ID : " + eachStock.getSector_id() +
//                        " , Market Cap : " + eachStock.getMarket_cap() + " , Current Ratio : " + eachStock.getCurrentRatio());
//            }

            //Get all subsector information from the database
            List<SubSectorVO> subSectorList = marketAnalyticsService.getAllSubSectors();
            System.out.println("Number of subsectors returned from db is " + subSectorList.size());

            //Get PriceHistory for AMD between 2 dates fromDate and toDate
            LocalDate fromDate = LocalDate.parse("2025-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<StocksPriceHistoryVO> priceHistoryVOList = marketAnalyticsService.getPriceHistoryForSingleStock("AMD", fromDate, fromDate.plusMonths(1));
            System.out.println(priceHistoryVOList);

            //Deal with HeathCare stocks
            marketAnalyticsService.dealingWithHealthCareStocks();

            //per Sector method
            marketAnalyticsService.splitStocksBySector();
            marketAnalyticsService.identifyBlueChipStocks();
            marketAnalyticsService.identifySmallCapStocks();
            marketAnalyticsService.subSectorAvgMktCap();

        } catch (StockException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
