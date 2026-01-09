import java.math.BigInteger;

/**
 * Class that represents real world stock being traded in the US Stock Exchanges
 * Encapsulation is a concept where in Instance variables of a class are made private and
 * access is restricted via getter and setter methods
 */
public class Stock {
     /**
     * code in the static block runs only once , the first time class is engaged either by calling a static method on it or by creating objects out of its
     */
    static {
        System.out.println("Inside the static block in the stock class");
    }

    private String tickerSymbol;
    private String tickerName;
    private BigInteger marketCap;
    private int sectorID;
    private int subSectorID;
    private double currentRatio;
    //Default Constructor
    private Stock() {
    }
    /**
     * Parameterized Constructor that forces creating a stock with preset parameters
     *
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

    //Static variables are generally used to define constants
    //final values are not to be changed
    private static final String junkVar = "Whatever";
    /**
     * Some documentation for this junk static method
     *
     * @param someInput
     * @return
     */
    //Static methods are used to implement functionality purely based on inputs or to provide utility functionality
    public static String someJunkMethod(String someInput) {
        return junkVar + someInput;
    }

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
        System.out.println(someJunkMethod(junkVar)); //Static variable/method can be used in Instance Method
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

    /**
     * This Method prints all the details of the stock.
     */
    public void printStockDetails() {
        System.out.println("Ticker Symbol : " + tickerSymbol);
        System.out.println("Ticker Name : " + tickerName);
        System.out.println("SectorID : " + sectorID);
        System.out.println("Sub Sector ID : " + subSectorID);
        System.out.println("Market Cap : " + marketCap);
        System.out.println("Current Ratio : " + currentRatio);
    }
}
