package com.eureka.stocks;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.service.MarketAnalyticsService;
import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.util.List;

public class StocksMain {
    public static void main(String[] args) {
        try (LookUpDAO lookUpDAO = new LookUpDAO()) { //try-with resources construct

            //Injecting an instance of lookUpDAO to the constructor of MarketAnalyticsService,
            // as it is a dependency that is needed for the MarketAnalyticsService instance to function.
            MarketAnalyticsService marketAnalyticsService = new MarketAnalyticsService(lookUpDAO);

            //Get all sector information from the database
            List<SectorVO> allSectorsList = marketAnalyticsService.getAllSectors();
            System.out.println("Number of sectors returned from db is " + allSectorsList.size());

            int subSectorID = 283;
            //Get subsector details from the database for the subsector id listed above
            SubSectorVO subSector = marketAnalyticsService.getSingleSubSector(subSectorID);
            System.out.println("SubSector retrieved is : " + subSector);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
