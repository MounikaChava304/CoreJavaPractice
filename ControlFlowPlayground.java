import java.math.BigDecimal;

public class ControlFlowPlayground {
    public static void main(String[] args) {
        boolean someBoolean = true;

        int i = 38; //= refers to assignment of a value to the variable
        int j = 38;

        if (i == j) {// Boolean expression comparing primitive variables
            System.out.println("i and j are equal");
        }

        BigDecimal bd1 = new BigDecimal("35.15");
        BigDecimal bd2 = new BigDecimal("35.15");
        if (bd1 == bd2) {//Boolean expression comparing objects
            System.out.println("bd1 and bd2 are equal");
        } else {
            System.out.println("bd1 and bd2 are not the same");
        }
        if (bd1.equals(bd2)) { //equals method to compare objects
            System.out.println("Using equals() method, bd1 and bd2 are equal");
        } else {
            System.out.println("Using equals() method, bd1 and bd2 are not equal");
        }

        boolean k = (i == 38); //== represents evaluating expression for equality
        System.out.println("Value of boolean k is " + k);

        //Branching Code
        if (i == 38) {//Expression in the if-clause should resolve to a boolean value
            //Code that executes when "i" is equal to 38
            System.out.println("i is 38");
        } else {
            //Code that executes when "i" is not 38
            System.out.println("i is not 38");
        }
        // | or ||
        if (i == 38 || k && i > 26) {//Chain your boolean expression
            System.out.println("i is either 38 or k is true and i is greater than 26");
        } else {
            System.out.println("Whatever");
        }
        String str1 = "Eureka";
        if (str1 != null && !str1.isBlank()) {
//        if(!str1.isBlank()){ //Results in a nullPointerException
            System.out.println("Str1 length is " + str1.length());
        } else {
            System.out.println("str1 is either null or blank");
        }

        //Ternary Operator - Example
        double randomDouble = Math.random();
        String someString = null;
        if (randomDouble > 0.5) {
            someString = "Greater than 0.5";
        } else {
            someString = "Less than 0.5";
        }
        System.out.println("value of randomDouble is " + randomDouble + " , someString is " + someString);

        //Using Ternary Operator, assigning a value to someOtherString based on what the boolean expression evaluates to
        String someOtherString = (randomDouble > 0.5) ? "Greater than 0.5" : "Less than 0.5";
        System.out.println("Using ternary operator, value of randomDouble is " + randomDouble + " , someOtherString is " + someOtherString);

        //While Loop
        int x = 8;
        while (x > 0) { //While loop runs as long as the boolean expression keeps evaluating to true
            System.out.println("In while loop, value of x is " + x);
            x--; //Value of x is decremented
        }
        Stock appleStock = new Stock("AAPL", "Apple Inc", 35, 165);
//        Stock msftStock = new Stock("MSFT", "Microsoft Corp", 31, 265);
//        Stock googleStock = new Stock("GOOGL", "Alphabet Inc", 37, 187);
//        Stock teslaStock = new Stock("TSLA", "Tesla Inc", 38, 175);
        Stock anotherAppleStock = new Stock("AAPL", "Apple Inc", 35, 165);

        if (appleStock == anotherAppleStock) {
            System.out.println("Both Apple Stocks are the same");
        } else {
            System.out.println("Both Apple Stocks are not the same");
        }

        if (appleStock.equals(anotherAppleStock)) {
            System.out.println("Using equals(), Both Apple Stocks are the same");
        } else {
            System.out.println("Using equals(), Both Apple Stocks are not the same");
        }

        if (appleStock.equals(bd1)) {
            System.out.println("Using equals(), Both Apple and BD are the same");
        } else {
            System.out.println("Using equals(), Both Apple and BD are not the same");
        }

        dumbInvestmentAdvice(appleStock);
    }

    private static void dumbInvestmentAdvice(Stock stock) {
        //Branching code
        if (stock.getTickerSymbol().equalsIgnoreCase("AAPL")) {
            System.out.println("Sell !!!!!!!");
        } else if (stock.getTickerSymbol().equalsIgnoreCase("MSFT")) {
            System.out.println("Hold !!!!!!!!");
        } else if (stock.getTickerSymbol().equalsIgnoreCase("GOOGL")) {
            System.out.println("Buy !!!!!!!");
        } else {
            System.out.println("Go to Hell !!!!");
        }
        //Switch Statement
        switch (stock.getTickerSymbol()) {
            case "AAPL" -> System.out.println("Sell from Switch!!!");
            case "MSFT" -> System.out.println("Hold from Switch!!!");
            case "GOOGL" -> System.out.println("Buy from Switch!!!");
            default -> System.out.println("Go to Hell from Switch !!!");
        }
    }
}
