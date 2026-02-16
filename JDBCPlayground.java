import java.io.IOException;
import java.sql.*;
import java.util.List;

public class JDBCPlayground {

    private static final String JDBC_URL = "jdbc:postgresql://endeavourtech.ddns.net:50271/StocksDB";
    private static final String USERNAME = "evr_sql_app";
    private static final String PASSWORD = "5LViU5pLkSjRHECec9NF4wRxxV";

    public static void main(String[] args) {
//      DriverManager.registerDriver(new OracleDriver()); //This Step is needed for Oracle and not needed for postgreSQL
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            int neededSubsectorID = 283;

            List<Integer> sectorIDList = List.of(35, 38, 41, 44);

            getAllSectorRecords(dbConnection);
            getSubSectorRecords(dbConnection, neededSubsectorID);
            getSectorsForGivenIDS(dbConnection, sectorIDList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getSectorsForGivenIDS(Connection dbConnection, List<Integer> sectorIDList) {
        String sectorQuery = """
                            select
                               	sl.sector_id ,
                               	sl.sector_name
                            from
                               	endeavour.sector_lookup sl
                            where
                               	sl.sector_id in (
                """;
        String dynamicQuestionMarks = "?,".repeat(sectorIDList.size());
        String finalQuery = sectorQuery + dynamicQuestionMarks.substring(0, dynamicQuestionMarks.length() - 1) + ")";
        System.out.println("Final generated Query is : " + finalQuery);
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(finalQuery);
            for (int i = 0; i < sectorIDList.size(); i++) {
                preparedStatement.setInt(i + 1, sectorIDList.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int sectorID = resultSet.getInt("sector_id");
                String sectorName = resultSet.getString("sector_name");
                System.out.println("Sector ID is : " + sectorID + " , Sector Name is : " + sectorName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Code in pointless finally block");
        }
    }

    private static void getSubSectorRecords(Connection dbConnection, int neededSubsectorID) {
        String subSectorQuery = """ 
                select
                	sl.sector_id ,
                	sl.subsector_id ,
                	sl.subsector_name
                from
                	endeavour.subsector_lookup sl
                where
                	sl.subsector_id = ?
                """;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(subSectorQuery);
            preparedStatement.setInt(1, neededSubsectorID); //Injecting value as input into the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Each iteration of this while loop is reading a row
                int subSectorID = resultSet.getInt("subsector_id");//Reading a single column from a row
                int sectorID = resultSet.getInt("sector_id");
                String subSectorName = resultSet.getString("subsector_name");
                System.out.println("Sector ID is : " + sectorID + " , Sub Sector ID is : " + subSectorID + " , Sub Sector Name is : " + subSectorName);
//                throw new IOException("Throwing exception for time pass");
            }
//        } catch (SQLException | IOException | NullPointerException e) {
        } catch (SQLException e) {
            System.out.println("In the first Line of SQLException Catch block");
            e.printStackTrace(); //prints the exception details into the console
        } catch (Exception e) {
            System.out.println("In the first Line of Exception Catch block");
            e.printStackTrace();
        } finally {
            System.out.println("This Code in finally always executes no matter what");
        }
    }

    private static void getAllSectorRecords(Connection dbConnection) throws SQLException {
        //Multi Line String in Java -- Just give """
        String sectorQuery = """ 
                select
                	sl.sector_id ,
                	sl.sector_name
                from
                	endeavour.sector_lookup sl ;
                """;
        PreparedStatement preparedStatement = dbConnection.prepareStatement(sectorQuery); //generate a prepared statement from connection
        ResultSet resultSet = preparedStatement.executeQuery(); //Generate the result set by executing the query on the prepared statement

        //Iterate the Result Set, each iteration of the below while loop represents reading a row from the query result set
        while (resultSet.next()) {
            int sectorID = resultSet.getInt("sector_id");
            String sectorName = resultSet.getString("sector_name");
            System.out.println("Sector ID is : " + sectorID +
                    " , Sector Name is : " + sectorName);
        }
    }
}
