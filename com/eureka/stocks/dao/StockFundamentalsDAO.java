package com.eureka.stocks.dao;

import com.eureka.stocks.vo.StockFundamentalsVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockFundamentalsDAO extends BaseDAO {
    public StockFundamentalsDAO() {
        super();
    }

    public List<StockFundamentalsVO> getStockFundamentals() {
        List<StockFundamentalsVO> stockFundamentalsVOList = new ArrayList<>();
        String stockFundamentalsQuery = """
                select
                	sf.ticker_symbol ,
                	sf.sector_id ,
                	sf.market_cap ,
                	sf.current_ratio
                from
                	endeavour.stock_fundamentals sf
                """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stockFundamentalsQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                StockFundamentalsVO stockFundamentalsVO = new StockFundamentalsVO(resultSet.getString("ticker_symbol"));
                stockFundamentalsVO.setSector_id(resultSet.getInt("sector_id"));
                stockFundamentalsVO.setMarket_cap(resultSet.getFloat("market_cap"));
                stockFundamentalsVO.setCurrentRatio(resultSet.getFloat("current_ratio"));

                stockFundamentalsVOList.add(stockFundamentalsVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockFundamentalsVOList;

    }

}
