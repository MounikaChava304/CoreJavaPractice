import java.math.BigInteger;

/**
 * Class that represents real world stock being traded in the US Stock Exchanges
 * Encapsulation is a concept where in Instance variables of a class are made private and
 * access is restricted via getter and setter methods
 */
public class Stock {

    //Default Constructor
    private Stock() {
    }

    /**
     * Constructor that forces creating a stock with preset parameters
     * @param tickerSymbol
     * @param tickerName
     * @param sectorID
     * @param subSectorID
     */

    public Stock(String tickerSymbol, String tickerName, int sectorID, int subSectorID) {
        this.tickerSymbol = tickerSymbol;
        this.tickerName = tickerName;
        this.sectorID = sectorID;
        this.subSectorID = subSectorID;
    }

    private String tickerSymbol;
    private String tickerName;
    private BigInteger marketCap;
    private int sectorID;
    private int subSectorID;
    private double currentRatio;

    //Getter and Setter Methods

    public String getTickerSymbol() {
        return tickerSymbol;
    }

//    public void setTickerSymbol(String tickerSymbol) {
//        this.tickerSymbol = tickerSymbol; //this keyword refers to the current object
//    }

    public String getTickerName() {
        return tickerName;
    }

//    public void setTickerName(String tickerName) {
//        this.tickerName = tickerName;
//    }

    public BigInteger getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigInteger marketCap) {
        this.marketCap = marketCap;//this.marketCap is an instance variable of class, marketCap is a method parameter
    }

    public int getSectorID() {
        return sectorID;
    }

//    public void setSectorID(int sectorID) {
//        this.sectorID = sectorID;
//    }

    public int getSubSectorID() {
        return subSectorID;
    }

//    public void setSubSectorID(int subSectorID) {
//        this.subSectorID = subSectorID;
//    }

    public double getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(double currentRatio) {
        this.currentRatio = currentRatio;
    }
}
