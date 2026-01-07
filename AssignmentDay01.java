/**
 * First Assignment in Java to write a method that takes in a String and 2 doubles as input, and
 * returns the concatenated output of the input String and sum of the doubles.
 * Example: Eureka 15.00 16.50 should return Eureka31.50 as output.
 */

public class AssignmentDay01 {
    public static void main(String[] args) {
        String str = "Eureka";
        double d1 = 15.00;
        double d2 = 16.50;
        System.out.println("Concatenated output of the input String and sum of two doubles is " + concatStringSumDoubles(str, d1, d2));
    }

    /**Method that returns the concatenated output of the input String and sum of the doubles
     * @param str Input String
     * @param d1  First Double input
     * @param d2  Second Double input
     * @return Adds two input doubles and concat the sum of the doubles to the input string
     */
    private static String concatStringSumDoubles(String str, double d1, double d2) {
        return str + (d1 + d2);
    }
}
