import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ListPlayground {
    public static void main(String[] args) {
//        One way of Assignment
//        ArrayList<String> myFirstArrayList = new ArrayList<String>();
//        Using interface polymorphism
        List<String> myFirstArrayList = new ArrayList<>();

        List<String> removeList = new ArrayList<>();
        removeList.add("INTL");
        removeList.add("NVDA");
//        Lists maintain insertion order
        myFirstArrayList.add("GOOGL");
        myFirstArrayList.add("AMD");
        myFirstArrayList.add("INTL");
        myFirstArrayList.add("AAPL");

        System.out.println("Size of the list is :" + myFirstArrayList.size());
        System.out.println("Contents of the list is : " + myFirstArrayList);

        System.out.println("Stock at position 4 is " + myFirstArrayList.get(3));
        myFirstArrayList.add(2, "INTL");
        myFirstArrayList.add("AMD");
        //OverLoaded add method

        myFirstArrayList.remove("INTL");
        myFirstArrayList.removeAll(removeList);
        myFirstArrayList.remove(3);
        System.out.println(myFirstArrayList);

        System.out.println("Is my First List Empty ? " + myFirstArrayList.isEmpty());

        myFirstArrayList.addAll(removeList);
        System.out.println("First List after addition of another list is " + myFirstArrayList);

        System.out.println("Does my list contain AMD stock int it ? " + myFirstArrayList.contains("AMD"));

        Collections.sort(myFirstArrayList);//Collections is a util class
        System.out.println("My First List after the sort is " + myFirstArrayList);

        //List of our custom object stock
        List<Stock> stocksList = new ArrayList<>();

        //Immutable Stocks List
//        List<Stock> stocksList = List.of(
//                new Stock("AAPL", "Apple Inc. "),
//                new Stock("AMD", "Advanced Micro Devices Inc. "),
//                new Stock("AAPL", "Apple Inc. ")
//        );
        stocksList.add(new Stock("AAPL", "Apple Inc. "));
        stocksList.add(new Stock("AMD", "Advanced Micro Devices Inc."));
        stocksList.add(new Stock("GOOGL", "Alphabet"));
        stocksList.add(new Stock("TSLA", "Tesla Inc."));
        stocksList.add(new Stock("AMEX", "American Express"));
        stocksList.add(new Stock("AMD", "Advanced Micro Devices Inc.", 35, 147));

        System.out.println("Stocks List is " + stocksList);//toString overridden by ArrayList and Stock Class
        System.out.println("Does AMD exist in Stock List ? " + stocksList.contains(new Stock("AMD", "AdvancedMicro Devices Inc.")));
        System.out.println("AMD stock in the list is at position : " + stocksList.indexOf(new Stock("AMD", "Advanced Micro Devices Inc.")));

//        List<Stock> reverseStockList = stocksList.reversed(); //This generates a new list with reverse order
        Collections.reverse(stocksList); //This will change the original list order

//        System.out.println("Reversed List is : "+reverseStockList);
        List<Stock> stocksSubList = stocksList.subList(1, 4); //Start index is inclusive, endIndex is exclusive

        //Iteration of a list using for-i loop
        for (int i = 0; i < stocksSubList.size(); i++) {
            System.out.println(stocksSubList.get(i));
        }
        System.out.println("--------------------------------");
        //For-each loop on the list
        for (Stock eachStock : stocksList) {
            System.out.println(eachStock);
        }
        System.out.println("--------------------------------");
        stocksList.forEach(stock -> { //Lambda Expression
            System.out.println("Print from forEach using Functional Interface "+stock);
        });
        System.out.println("--------------------------------");
        stocksList.forEach(System.out::println); //Using Method Reference

        //ALERT ---- DO NOT DO THIS
        List<Object> someJunkList = new ArrayList();
        someJunkList.add(new Stock("V", "Visa Inc"));
        someJunkList.add("Whatever");
        someJunkList.add(new BigDecimal("100.0"));

        //Wrapper Class
        int i = 47;
        Integer obji = 47; //Implicit typecasting being done by Java from primitive int 47 to 47 being held in an object of type Integer

//        List<Integer> intList = new ArrayList<>();
//        intList.add(47);
//        intList.add(95);
//        intList.add(63);
//        intList.add(173);
        List<Integer> intList = List.of(47, 93, 63, 179); //Alternate way of generating a Immutable List using of static method in List Interface

        //intList.add(35); //Not possible on a immutable list created at line 99
        int total = 0;
        for (Integer eachInt : intList) {
            total = total + eachInt; //Another implicit typecasting in reverse
        }
        System.out.println("Total of intList values is " + total);
        System.out.println("Does intList contains 63 ? " + intList.contains(63));

        List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        //My First Stream
        List<String> streamStringList = integerList.stream()
                .filter(n -> n % 2 == 0) //Filter is and Intermediate Function, like WHERE in SQL
                .sorted(Comparator.comparing(Integer::intValue).reversed()) //Sorted is also an Intermediate Function, like ORDERBY in SQL
                .map((j) -> "Test" + j) //Transformation Function that change the stream type
                .limit(2) //Same as limit in SQL
                .collect(Collectors.toList()); //Terminal Function that collects the Stream to a List. Collectors is a util class.
        System.out.println(streamStringList);
        System.out.println("Original int List is : "+integerList);

        //Basic example of using reduce terminal function
        Optional<Integer> intSumOptional = integerList.stream()
                .reduce((a, b) -> a + b);
//        intSumOptional.ifPresent(x -> System.out.println(x)); //Using Lambda Expression
        intSumOptional.ifPresent(System.out::println);
    }
}