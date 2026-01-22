package com.eureka.stocks.dao;

import com.eureka.stocks.exception.StockException;
import com.eureka.stocks.vo.StocksPriceHistoryVO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockPriceHistoryDAO extends BaseDAO{

    public List<StocksPriceHistoryVO> getPriceHistoryForStock(String ticker, LocalDate fromDate, LocalDate toDate){
        String priceHistoryQuery = """
                select
                	sph.ticker_symbol ,
                	sph.trading_date ,
                	sph.open_price ,
                	sph.close_price ,
                	sph.high_price ,
                	sph.low_price
                from
                	endeavour.stocks_price_history sph
                where
                	sph.ticker_symbol = ?
                	and sph.trading_date between ? and ?
                """;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(priceHistoryQuery);
            preparedStatement.setString(1, ticker);
            preparedStatement.setDate(2, Date.valueOf(fromDate)); //Convert java.time.LocalDate to java.sql.Date
            preparedStatement.setDate(3, Date.valueOf(toDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<StocksPriceHistoryVO> priceHistoryVOList = new ArrayList<>();

            while(resultSet.next()){
                StocksPriceHistoryVO stocksPriceHistoryVO = new StocksPriceHistoryVO(
                        resultSet.getString("ticker_symbol"),
                        resultSet.getDate("trading_date").toLocalDate() //Converting returned java.sql.Date to java.time.Local Date
                );
                stocksPriceHistoryVO.setOpenPrice(resultSet.getBigDecimal("open_price"));
                stocksPriceHistoryVO.setClosePrice(resultSet.getBigDecimal("close_price"));
                stocksPriceHistoryVO.setHighPrice(resultSet.getBigDecimal("high_price"));
                stocksPriceHistoryVO.setLowPrice(resultSet.getBigDecimal("low_price"));
                priceHistoryVOList.add(stocksPriceHistoryVO);
            }
            return priceHistoryVOList;
        }catch(SQLException e){
            throw new StockException("Unable to get Stock Price History data for given ticker "+ticker, e);
        }


    }
}
