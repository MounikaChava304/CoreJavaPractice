package com.eureka.stocks;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.dao.StockFundamentalsDAO;
import com.eureka.stocks.exception.StockException;
import com.eureka.stocks.service.MarketAnalyticsService;
import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.StockFundamentalsVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.util.List;

public class StocksMain {
    public static void main(String[] args) {
        try (LookUpDAO lookUpDAO = new LookUpDAO();
             StockFundamentalsDAO stockFundamentalsDAO = new StockFundamentalsDAO()) { //try-with resources construct

            //Injecting an instance of lookUpDAO to the constructor of MarketAnalyticsService,
            // as it is a dependency that is needed for the MarketAnalyticsService instance to function.
            MarketAnalyticsService marketAnalyticsService = new MarketAnalyticsService(lookUpDAO, stockFundamentalsDAO);

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

        } catch (StockException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
