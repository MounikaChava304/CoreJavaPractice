import java.math.BigInteger;

public class AssignmentDay02 {
    public static void main(String[] args) {
        Stock microsoftStock = new Stock("MSFT", "Microsoft Corporation", 44, 278);
        microsoftStock.setMarketCap(new BigInteger("390000000000"));
        microsoftStock.setCurrentRatio(1.89);
        microsoftStock.printStockDetails();
    }
}
