package com.eureka.stocks.dao;

import com.eureka.stocks.vo.SectorVO;
import com.eureka.stocks.vo.SubSectorVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LookUpDAO extends BaseDAO {
    /***
     * Default Constructor will call the super class constructor that creates the database connection
     */
    public LookUpDAO() {
        super();
    }

    public List<SectorVO> getAllSectors() {
        String sectorQuery = """
                select
                	sl.sector_id ,
                	sl.sector_name
                from
                	endeavour.sector_lookup sl ;
                """;
        List<SectorVO> sectorList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sectorQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SectorVO sectorVO = new SectorVO(resultSet.getInt("sector_id")); //Get value of sector_id column from the db and use it to create a SectorVO object/instance
                sectorVO.setSectorName(resultSet.getString("sector_name")); //get value of sector_name column from the db and set it into the sectorVO instance
                sectorList.add(sectorVO);
            }
            return sectorList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        }
        return sectorList;
    }

    public SubSectorVO getSubSector(int subSectorID) {
        String subSectorQuery = """
                select
                	sl.sector_id ,
                	sl.subsector_id ,
                	sl.subsector_name
                from
                	endeavour.subsector_lookup sl
                where
                	sl.subsector_id = ?;
                """;
        SubSectorVO subSectorVO = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(subSectorQuery);
            preparedStatement.setInt(1, subSectorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subSectorVO = new SubSectorVO(resultSet.getInt("subsector_id"));
                subSectorVO.setSectorID(resultSet.getInt("sector_id"));
                subSectorVO.setSubSectorName(resultSet.getString("subsector_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subSectorVO;
    }
}
