import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapsPlayground {
    public static void main(String[] args) {
//        generateMapUsingRealObjects();
    }

    private static void generateMapUsingRealObjects() {
        Map<Integer, String> sectorMap = new HashMap<>(); //No duplicate keys, values can be duplicate, no insertion order maintained
        sectorMap.put(10, "Healthcare"); //Adding values to the map
        sectorMap.put(11, "Technology");
        sectorMap.put(12, "Real Estate");

        System.out.println("Size of the Map is "+sectorMap.size());
        System.out.println("Value at key 11 is "+sectorMap.get(11));

        sectorMap.put(13, "Energy");
        sectorMap.put(10, "Mafia"); //Overrides the previous value present at the key -- Overrides Healthcare and stores Mafia in key 10
        sectorMap.put(82, "Energy"); //Duplicate values are allowed

        sectorMap.remove(82);
        System.out.println(sectorMap);

        Map<String, Stock> stocksMap = new HashMap<>();
        stocksMap.put("AAPL", new Stock("AAPL", "Apple Inc."));
        stocksMap.put("MSFT", new Stock("MSFT", "Microslop"));
        stocksMap.put("NVDA", new Stock("NVDIA", "Nvidia Inc."));
        stocksMap.put("AMD", new Stock("AMD", "Advanced Micro Devices"));

        System.out.println(stocksMap);

        System.out.println("Is Microsoft present in the map ? "+stocksMap.containsKey("MSFT"));
        System.out.println("Is a stock with name Microsoft present i the value of the map ? "
                +stocksMap.containsValue(new Stock("MSFT", "Microslop")));
        System.out.println("----------------------------------------------------------------------");

        //Iterate over a Map - Method 1
        Set<String> tickerKeySet = stocksMap.keySet();
        tickerKeySet.forEach(ticker -> System.out.println("Key : "+ticker+" , value : "+stocksMap.get(ticker) ));

        System.out.println("----------------------------------------------------------------------");

        //Iterate over a Map - Method 2
        stocksMap.forEach((ticker, stock) -> {
            System.out.println("Using BiConsumer, Key : "+ticker+" , Value : "+stock);
        });

        //Compute-ifAbsent and compute-ifPresent examples (******** Both these instance methods modify the underlying Map *******)

        //If the key is absent in the map, then calculate a value using the Input Function and add this entry(key, value) back into the map
        //If the key is present in the map, just return the value for that entry, and the map remains unchanged
        String newValue = sectorMap.computeIfAbsent(11, (key) -> "Whatever" + key);
        System.out.println("New Calculated Value is : "+newValue);

        //If the key is absent, null value is returned and the map is left untouched.
        //If the key is present, the bi function is used to calculate a new value and update the corresponding entry in the map.
        String newPresentValue = sectorMap.computeIfPresent(10, (sectorID, sectorName) -> {
            return sectorID + sectorName;
        });
        System.out.println("New Calculated Value ifPresent is : "+newPresentValue);

        List<String> tickersList = List.of("NVDA", "AMD" ,"GOOGL","MSFT","AAPL","AMD","INTL","GOOGL","AAPL","AMD");
        //Count the number of times each stock has been repeated
        Map<String, Integer> finalOutputMap = new HashMap<>();
        tickersList.forEach(eachTicker -> {
            Integer count = finalOutputMap.get(eachTicker);
            if(count == null){
                count = 0;
            }
            count = count+1;
            finalOutputMap.put(eachTicker, count);
        });
        System.out.println(finalOutputMap);

        //Count the number of times each stock has been repeated - The cool Way
        Map<String, Integer> anotherFinalOutputMap = new HashMap<>();
        tickersList.forEach((eachTicker) -> {
            int count = anotherFinalOutputMap.computeIfAbsent(eachTicker, (key) -> 0) + 1;
            anotherFinalOutputMap.put(eachTicker, count);
        });
        System.out.println(anotherFinalOutputMap);

    }
}
