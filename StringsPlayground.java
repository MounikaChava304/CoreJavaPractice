public class StringsPlayground {
    public static void main(String[] args) {
        String s1 = "Eureka"; //String literal definition and Strings go into Literal Pool (special memory area for all the strings)
        String s2 = new String("Tech");//Using the new keyword

        String s3 = "Eureka";
        String s4 = new String("Eureka");

        String concatString = s1 + s2;
        String anotherConcatString = s1.concat(s2);

        System.out.println("Concatenate two strings s1 and s2 : " + concatString);
        System.out.println("Concatenate two strings s1 and s2 using .concat method : " + anotherConcatString);

        System.out.println("Length of the String s4 is " + s4.length());
        System.out.println("Uppercase of anotherConcatString is : " + anotherConcatString.toUpperCase());
        System.out.println("Value of anotherConcatString after uppercase is : " + anotherConcatString);//String immutability

        System.out.println("Character at position 3 of " + anotherConcatString + " is " + anotherConcatString.charAt(2));//Index is counted from 0. It is called Inclusive index

        //Substrings
        System.out.println("Substring variation 1 for " + anotherConcatString + " is " + anotherConcatString.substring(2)); //Inclusive beginIndex
        System.out.println("Substring variation 2 for " + anotherConcatString + " is " + anotherConcatString.substring(2, 6)); //Inclusive beginIndex and exclusive endIndex

        System.out.println("Does the string " + anotherConcatString + " contains eka ? " + anotherConcatString.contains("eka"));
        System.out.println("Does the string " + anotherConcatString + " contains xyz ? " + anotherConcatString.contains("xyz"));

        System.out.println("Position of string eka in " + anotherConcatString + " is " + anotherConcatString.indexOf("eka"));

        System.out.println("Comparing two strings " + anotherConcatString + " , " + concatString + " is " + anotherConcatString.compareTo(s3));

        //String split, where comma(,) is the delimiter
        String tickerSymbols = "AAPL|GOOGL|AMD|NVDA|UNH|V";
        String[] tickerArray = tickerSymbols.split("\\|");// [|] also interprets | as data, '\\' is escape character that lets | to be interpreted as data
        for (String eachTicker : tickerArray) {
            System.out.println("Ticker : " + eachTicker);
        }

    }
}
