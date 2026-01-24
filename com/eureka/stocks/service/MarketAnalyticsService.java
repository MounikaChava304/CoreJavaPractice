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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        //Use on List with 1-1 relational mapping between instance variables
        Map<Integer, String> sectorMap = allSectorsList.stream()
                .collect(Collectors.toMap( //Generating a map from the list
                        SectorVO::getSectorID, //Key Mapping Function
                        SectorVO::getSectorName //Value Mapping Function
                ));
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

    /***
     * Method to use Streams to play with stock Fundamentals
     */
    public void dealingWithHealthCareStocks(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<StockFundamentalsVO> healthCareStocksList = new ArrayList<>();

        //Getting only Healthcare Stocks - Old way
        allStockFundamentalsList.forEach(stockFundamentalsVO -> {
            if(stockFundamentalsVO.getSector_id() == 34){
                healthCareStocksList.add(stockFundamentalsVO);
            }
        });
        System.out.println("Health Care List Size is "+healthCareStocksList.size());

        //Getting only HealthCare Stocks - Using Streams
        List<StockFundamentalsVO> coolHealthCareStocksList = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() != 0)
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .sorted(Comparator.comparing(StockFundamentalsVO::getMarket_cap).reversed())
                .collect(Collectors.toList());
        System.out.println("Cool HealthCare List is : "+coolHealthCareStocksList.size());

        //Count the number of healthcare stocks
        long countHealthCareStocks = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .count();
        System.out.println("Number of HealthCare Stocks is : "+countHealthCareStocks);

        //Get all ticker symbols for HealthCare Stocks
        List<String> healthCareTickersList = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .map(StockFundamentalsVO::getTicker_symbol) //Using Method Reference
//                .map(stockFundamentalsVO -> stockFundamentalsVO.getTicker_symbol()) //Using Lambda Expression
                .sorted(Comparator.reverseOrder()) //Reverse the natural Order
//                .sorted(Comparator.comparing(String::toString).reversed()) //Another way of sorting by ticker symbol desc
                .collect(Collectors.toList());
        System.out.println(healthCareTickersList);

        //Get a comma separated list of health care ticker symbols
        String allTickersString = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .map(StockFundamentalsVO::getTicker_symbol)
                .collect(Collectors.joining(","));
        System.out.println("All Tickers String is : "+allTickersString);

        //Calculate the total market cap for all HealthCare Stocks
        Optional<BigDecimal> totalMktCapOptional = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .map(stockFundamentalsVO -> stockFundamentalsVO.getMarket_cap())
//                .reduce((a, b) -> a.add(b)); //reduce terminal function using Lambda Expression
                .reduce(BigDecimal::add); //reduce terminal function using Method Reference
        totalMktCapOptional.ifPresent((x) -> System.out.println("Total Market Cap of Health Care Stocks is : "+x));
    }

    public void splitStocksBySector(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        Map<Integer, List<StockFundamentalsVO>> perSectorMap = allStockFundamentalsList.stream()
                .collect(Collectors.groupingBy(StockFundamentalsVO::getSector_id)); //Split the original list into sublists based on sector id
    }

//    Method to identify Blue Chip Stocks. Blue Chips have a market cap > 10 Billion USD.
//    Get total market cap of Blue Chips
    public void identifyBlueChipStocks(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<StockFundamentalsVO> blueChipsStocksList = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getMarket_cap().compareTo(new BigDecimal("10000000000")) > 0)
                .sorted(Comparator.comparing(StockFundamentalsVO::getMarket_cap))
                .collect(Collectors.toList());
        System.out.println("# of Blue Chip Stocks are : "+blueChipsStocksList.size());

        Optional<BigDecimal> blueChipsMKTotalOptional = blueChipsStocksList.stream()
                .map(StockFundamentalsVO::getMarket_cap)
                .reduce((BigDecimal::add));
        blueChipsMKTotalOptional.ifPresent(x -> System.out.println("Total Market Cap of Blue Chip Stocks is : "+x));
    }
//  Method to identify Small Cap Stocks. Small Caps have a market cap between 250 Million and 2 Billion.
//  Find market cap total for Small caps
    public void identifySmallCapStocks(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        Optional<BigDecimal> smallCapTotalMktCapOptional = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getMarket_cap()
                        .compareTo(new BigDecimal("250000000")) > 0)
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getMarket_cap()
                        .compareTo(new BigDecimal("2000000000")) < 0)
                .map(StockFundamentalsVO::getMarket_cap)
                .reduce(BigDecimal::add);
        smallCapTotalMktCapOptional.ifPresent(x -> System.out.println("Total Market Cap of all small Cap Stocks is : "+x));
    }
//    Get the average market cap for each subsector of the economy. Show subsectorName along with its avergae Market Cap
    public void subSectorAvgMktCap(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<SubSectorVO> allSubSectorsList = lookUpDAO.getAllSubSectors();
//        Generate a map with each entry representing a Unique subsector, with key being subSectorID and value SubSector Name
        Map<Integer, String> subSectorNameByIDMap = allSubSectorsList.stream()
                .collect(Collectors.toMap(
                        SubSectorVO::getSubSectorID,
                        SubSectorVO::getSubSectorName
                ));

        Map<String, BigDecimal> finalOutputMap = new HashMap<>();

//        Map which contains subList of stocks objects belonging to each SubSector ID
        Map<Integer, List<StockFundamentalsVO>> subSectorMap = allStockFundamentalsList.stream()
                .collect(Collectors.groupingBy(StockFundamentalsVO::getSubSector_id));

//        ForEach subSector of the economy, stream the stockLists abd calculate total market cap
        subSectorMap.forEach((subSectorID, stocksList) -> {
            Optional<BigDecimal> subSectorTotalOptional = stocksList.stream()
                    .map(StockFundamentalsVO::getMarket_cap)
                    .reduce(BigDecimal::add);
            subSectorTotalOptional.ifPresent(subSectorTotal -> {
                finalOutputMap.put(subSectorNameByIDMap.get(subSectorID), subSectorTotal.divide(new BigDecimal(stocksList.size()),2 , RoundingMode.HALF_UP)); //divide method parameters, number, scale and rounding method
            });
        });
        System.out.println(finalOutputMap);
    }

    /***
     * This method gives the number of stocks for each sector. Assignment Day 11
     */
    public void eachSectorStocksCount(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<SectorVO> allSectorsList = lookUpDAO.getAllSectors();

        //Map for unique sector with sector_id as key and sector_name as value
        Map<Integer, String> sectorNameMapByID = allSectorsList.stream()
                .collect(Collectors.toMap(
                        SectorVO::getSectorID,
                        SectorVO::getSectorName
                ));
//        Map which contains stocks per sector
        Map<Integer, List<StockFundamentalsVO>> stocksPerSectorList = allStockFundamentalsList.stream()
                .collect(Collectors.groupingBy(StockFundamentalsVO::getSector_id));

        Map<String, Integer> finalOutputMap = new HashMap<>();

//        For Each sector of the economy, count the number of stocks
        stocksPerSectorList.forEach((sectorName, stocksList) -> {
            int stocksCount = stocksList.stream()
                    .map(StockFundamentalsVO::getTicker_symbol)
                    .collect(Collectors.toList()).size();
            finalOutputMap.put(sectorNameMapByID.get(sectorName), stocksCount);
        });
       finalOutputMap.forEach((sector, count) -> {
           System.out.println(sector+" : "+count);
       });
    }
}

