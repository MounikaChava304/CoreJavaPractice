package com.eureka.stocks.service;

import com.eureka.stocks.dao.LookUpDAO;
import com.eureka.stocks.dao.StockFundamentalsDAO;
import com.eureka.stocks.dao.StockPriceHistoryDAO;
import com.eureka.stocks.sorting.SFMarketCapAscComparator;
import com.eureka.stocks.sorting.SubSectorNameComparator;
import com.eureka.stocks.vo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
//    Get the average market cap for each subsector of the economy. Show subsectorName along with its average Market Cap
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

        Map<String, Integer> finalOutputMap = new TreeMap<>();

//        For Each sector of the economy, count the number of stocks
        stocksPerSectorList.forEach((sectorID, stocksList) -> {
            int stocksCount = stocksList.stream()
                    .map(StockFundamentalsVO::getTicker_symbol)
                    .collect(Collectors.toList()).size();
            finalOutputMap.put(sectorNameMapByID.get(sectorID), stocksCount);
        });
       finalOutputMap.forEach((sector, count) -> {
           System.out.println(sector+" : "+count);
       });
    }
//    Other way of Assignment

    public void getSectorStocksCountOtherWay(){
        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<SectorVO> allSectorsList = lookUpDAO.getAllSectors();

        Map<Integer, String> sectorNameByIDMap = allSectorsList.stream()
                .collect(Collectors.toMap(
                        SectorVO::getSectorID,
                        SectorVO::getSectorName
                ));
        Map<String, Integer> finalOutputMap = new TreeMap<>();

        //Using compute if absent
        allStockFundamentalsList
                .forEach(stockFundamentalsVO -> {
                    int count = finalOutputMap.computeIfAbsent(sectorNameByIDMap.get(stockFundamentalsVO.getSector_id()), (key) -> 0) + 1;
                    finalOutputMap.put(sectorNameByIDMap.get(stockFundamentalsVO.getSector_id()),count);
                });
    }

    /***
     * For each year, Calculate the lowest close price for a given stock
     * @param tickerSymbol
     * @param fromDate
     * @param toDate
     */
    public void getLowestPriceForGivenStock(String tickerSymbol, LocalDate fromDate, LocalDate toDate){
        List<StocksPriceHistoryVO> priceHistoryList = stockPriceHistoryDAO.getPriceHistoryForStock(tickerSymbol, fromDate, toDate);

        Map<Integer, List<StocksPriceHistoryVO>> priceHistoryListByYearMap = priceHistoryList.stream()
                .collect(Collectors.groupingBy(stocksPriceHistoryVO -> stocksPriceHistoryVO.getTradingDate().getYear()));

        Map<Integer, StocksPriceHistoryVO> finalOutputMap = new TreeMap<>();

        priceHistoryListByYearMap.forEach((year, stockList) -> {
            Optional<StocksPriceHistoryVO> lowestCPOptional = stockList.stream()
                    .min(Comparator.comparing(StocksPriceHistoryVO::getClosePrice)); //Instead of using intermediate and terminal function like below, this is a terminal function that does the same
//                    .sorted(Comparator.comparing(StocksPriceHistoryVO::getClosePrice))
//                    .findFirst();

            lowestCPOptional.ifPresent(lowestCP -> finalOutputMap.put(year, lowestCP));
        });
        System.out.println(finalOutputMap);
    }

    public void streamsRecap(){

        List<StockFundamentalsVO> allStockFundamentalsList = stockFundamentalsDAO.getStockFundamentals();
        List<SectorVO> allSectorsList = lookUpDAO.getAllSectors();

        //Generating a sub List from given List using filter
        List<StockFundamentalsVO> healthCareList = allStockFundamentalsList.stream()
                .filter(StockFundamentalsVO -> StockFundamentalsVO.getSector_id() == 34)
                .collect(Collectors.toList());

        //Sort HealthCare Stocks by current ratio desc -- also handle nulls
        List<StockFundamentalsVO> anotherHealthCareList = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .sorted(Comparator.comparing(StockFundamentalsVO::getCurrentRatio, Comparator.nullsFirst(BigDecimal::compareTo)).reversed()) //Use Comparator.nullsFirst(datatype::compareTo) when there could be null values
                .collect(Collectors.toList());

//        Find to 5 HealthCare Stocks by current ratio desc
        List<String> top5HealthCareTickers = allStockFundamentalsList.stream()
                .filter(stockFundamentalsVO -> stockFundamentalsVO.getSector_id() == 34)
                .sorted(Comparator.comparing(StockFundamentalsVO::getCurrentRatio, Comparator.nullsFirst(BigDecimal::compareTo)).reversed())
                .limit(5) //Like SQL Limit
                .map(StockFundamentalsVO::getTicker_symbol)// Transform the stream from stream<StockFundamentalsVO> to stream<Strings>
                .collect(Collectors.toList());
        System.out.println(top5HealthCareTickers);

//        Convert a 1-1 List to a Map
        Map<Integer, String> sectorNameByIDMap = allSectorsList.stream()
                .collect(Collectors.toMap(
                        SectorVO::getSectorID,
                        SectorVO::getSectorName
                ));
//        Convert a 1-many List into a Map
        SectorVO mafiaSector = new SectorVO(45);
        mafiaSector.setSectorName("Mafia");
        allSectorsList.add(mafiaSector);

        SectorVO junkSector = new SectorVO(45);
        junkSector.setSectorName("SomeJunk");
        allSectorsList.add(junkSector);

        Map<Integer, String> anotherSectorNameByIDMap = allSectorsList.stream()
                .collect(Collectors.toMap(
                        SectorVO::getSectorID,
                        SectorVO::getSectorName,
                        (x, y) -> x.concat(y) //Binary Operator Merge Function that resolves the values that go into the Map Entry
                ));

        //    Get a single String with all sector Names comma separated
        String allSectorsNamesString = allSectorsList.stream()
                .map(SectorVO::getSectorName)
                .sorted()
                .collect(Collectors.joining(" , "));

        //Calculate totalMarketCap for HealthCare Stocks
        Optional<BigDecimal> healthCareTotalOptional = healthCareList.stream()
                .map(StockFundamentalsVO::getMarket_cap)
//                .reduce((a,b)->a.add(b)) //Using Lambda Expression
                .reduce(BigDecimal::add);//Method Reference
        healthCareTotalOptional.ifPresent(System.out::println);

        //Split all Stock Price History by Month for AMD
        LocalDate fromDate = LocalDate.parse("2024-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<StocksPriceHistoryVO> amdPriceHistoryList = stockPriceHistoryDAO.getPriceHistoryForStock("AMD", fromDate, fromDate.plusYears(1));
        Map<Month, List<StocksPriceHistoryVO>> priceHistoryByMonthMap = new TreeMap(amdPriceHistoryList.stream()
                .collect(Collectors.groupingBy(stocksPriceHistoryVO -> stocksPriceHistoryVO.getTradingDate().getMonth())));
    }

    /***
     * --  During the period of 2022 to 2024, identify the top performing stock for each state in each year and its performance
     * --  Best performing stock is % of growth between Closing Price EOY and Opening Price SOY
     */
    public void calculateStockPerformanceByState(LocalDate fromDate, LocalDate toDate){
        long startTime =System.nanoTime();
        Instant start = Instant.now();
        List<StocksPriceHistoryVO> priceHistoryList = stockPriceHistoryDAO.getStockPriceHistoryAllStocks(fromDate, toDate);
        priceHistoryList.sort(Comparator.comparing(StocksPriceHistoryVO::getTickerSymbol)
                .thenComparing(Comparator.comparing(StocksPriceHistoryVO::getTradingDate).reversed()));
        //Group the list by trading year
        Map<Integer, List<StocksPriceHistoryVO>> priceHistoryListByYearMap = priceHistoryList.stream()
                .collect(Collectors.groupingBy(stocksPriceHistoryVO ->
                        stocksPriceHistoryVO.getTradingDate().getYear()));

        //The key of our map is a custom object that we created
        Map<TickerByYearVO,List<StocksPriceHistoryVO>> priceHistoryListByTckYrMap=new HashMap<>();

        //For every year, get the Price History of that year, group by Ticker Symbol, and then put it into the priceHistoryListByTckYrMap
        priceHistoryListByYearMap.forEach((year,phList)->{
            // This map contains price history for a given Ticker symbol for a given year
            Map<String, List<StocksPriceHistoryVO>> phListByTickerMap = phList.stream()
                    .collect(Collectors.groupingBy(StocksPriceHistoryVO::getTickerSymbol));

            phListByTickerMap.forEach((ticker,phTickerList)->{
                TickerByYearVO tickerByYearVO= new TickerByYearVO(year, ticker);
                priceHistoryListByTckYrMap.put(tickerByYearVO,phTickerList);
            });

        });
        List<StockPerformanceVO> performanceList = new ArrayList<>();
        //Map<newVo, List<PriceHistory>-> get list->Identify pho
        //with lowest date and highest date and get prices from them
        //and into a  performanceVo object
        priceHistoryListByTckYrMap.forEach(((tickerByYearVO, phByYearTkrList)->
        {
            //Optional contains a StockPrice History object for the max Trading date for that year and ticker
            Optional<StocksPriceHistoryVO> maxTradingDateOptional = phByYearTkrList.stream()
                    .max(Comparator.comparing(StocksPriceHistoryVO::getTradingDate));
            //Optional contains a StockPrice History object for the min Trading date for that year and ticker
            Optional<StocksPriceHistoryVO> minTradingDateOptional = phByYearTkrList.stream()
                    .min(Comparator.comparing(StocksPriceHistoryVO::getTradingDate));
            if(maxTradingDateOptional.isPresent() && minTradingDateOptional.isPresent()){
                StockPerformanceVO stockPerformanceVO= new StockPerformanceVO();
                stockPerformanceVO.setYear(tickerByYearVO.getYear());
                stockPerformanceVO.setTickerSymbol(tickerByYearVO.getTickerSymbol());
                stockPerformanceVO.setState(maxTradingDateOptional.get().getState());

                //Performance calculation 100*(CPYE-OPSOY)/OPSOY
                BigDecimal performance =new BigDecimal(100).multiply((maxTradingDateOptional.get().getClosePrice()
                                .subtract(minTradingDateOptional.get().getOpenPrice())))
                        .divide(minTradingDateOptional.get().getOpenPrice(),2,RoundingMode.HALF_UP);
                stockPerformanceVO.setPerformance(performance);
                performanceList.add(stockPerformanceVO);
            }
            Collections.sort(performanceList);
        }));
        List<StockPerformanceVO> finalOutputList = new ArrayList<>();
        //Split the performance List by year
        Map<Integer, List<StockPerformanceVO>> performanceByYearMap = performanceList.stream()
                .collect(Collectors.groupingBy(StockPerformanceVO::getYear));
        //performanceByYearMap contains List of performance metrics for a  given year
        performanceByYearMap.forEach((year, perfList)->{
            //PerfByStateMap which is temporary that contains List of performance Metrics for each year and state
            Map<String, List<StockPerformanceVO>> perfByStateMap = perfList.stream()
                    .collect(Collectors.groupingBy(StockPerformanceVO::getState));

            perfByStateMap.forEach((stateCode, perfStateList)->
            {
                Optional<StockPerformanceVO> byStateTopPerformingStockOptional = perfStateList.stream()
                        .max(Comparator.comparing(StockPerformanceVO::getPerformance));
                byStateTopPerformingStockOptional.ifPresent(stockPerformanceVO -> finalOutputList.add(stockPerformanceVO));
            });
        });
        Collections.sort(finalOutputList);
        long endTime=System.nanoTime();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start,finish).toMillis();
        System.out.println("\n Time take to complete \n"+timeElapsed);
        System.out.println(finalOutputList);
    }
}

