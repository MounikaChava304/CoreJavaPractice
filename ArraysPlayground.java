import java.util.Arrays;

public class ArraysPlayground {
    public static void main(String[] args) {
//  Initializing an int array and assign it values.
        int[] numArray = new int[]{1, 2, 3, 4, 5};
//  Initializing an int array of size 5.
        int[] squareArray = new int[5];
//       squareArray = new int[]{1,4,9,14,25};

        System.out.println("Size of the numsArray is " + numArray.length);
        System.out.println("Value at position 3 of the numsArray is " + numArray[2]);//Array indexes are inclusive, they go from 0 to length-1

        for (int index = 0; index < numArray.length; index++) { //Iteration of a loop
            System.out.println("Value at index " + index + " of numArray is " + numArray[index]);
            squareArray[index] = numArray[index] * numArray[index];//This code gets repeated as many times the loop runs
        }

        for (int index = numArray.length - 1; index >= 0; index--) {
            System.out.println("Value at index " + index + " of numArray is " + numArray[index]);
        }
        //For-each loop
        for (int eachElement : squareArray) {
            System.out.println("Value in square Array is " + eachElement);
        }

        //For-each loop on a String Array, Each Ticker will be of type String
        String[] tickerArray = new String[]{"AAPL", "GOOGL", "TSLA", "AMD", "NVDA"};
        for (String eachTicker : tickerArray) {
            System.out.println("Ticker Symbol " + eachTicker);
        }
        Arrays.sort(tickerArray);
        System.out.println("Ticker Array after sort is " + Arrays.toString(tickerArray));

        int[] anotherNumArray = new int[]{8, 34, 76, 4, 91, 24, 52};
        Arrays.sort(anotherNumArray); //Arrays is a Utility class provided by the Java Language, which only has static methods that provide functionality based on inputs provided
//        for (int eachNumber : anotherNumArray) {
//            System.out.println(eachNumber);
//        }
        System.out.println("Array anotherNumArray is " + Arrays.toString(anotherNumArray));
    }
}
