/**
 * First class written in Java that introduces primitive dataTypes and simple methods
 */
public class Eureka {
    public static void main(String[] args) { //Default method in Java
        System.out.println("Eureka !!!!!!");

        int i = 56; //Variable declaration and assignment of a value
        int j;  //Variable declaration
        j = 45;   //value assignment

        int k = i + j;//statically typed language -- datatype is defined clearly, also a Type Safe Language.
        System.out.println("Value of i is " + i);
        System.out.println("Sum of i and j is " + (i + j));

//        float floatSum = sumFloatMethod();//Calling code taking the return value of sumFloatMethod and assigning to floatSum method
//        System.out.println("Sum of floats f1 and f2 is "+floatSum);
        float f1 = 53.25f;
        float f2 = 94.56f;
        double doublef1 = (double)f1; //TypeCasting - converting values from one type to another
        double anotherDoublef1 = f1; //Implicit typecasting

        System.out.println("Sum of floats f1 and f2 is " + sumFloatMethod(f1, f2));
        System.out.println("Sum of static floats f1 and f2 is " + sumFloatMethod());

        double d1 = 13.56; //int,float,double,boolean are primitive data types
        double d2 = 14.98;
        System.out.println("Product of d1 and d2 is " +multiplyDoubleValues(d1,d2));

        boolean b1 = true;
//        b1 = false;
        System.out.println("Value of b1 is " + b1);

        String x = "Eureka"; //String Literal
        String y = new String("Technologies"); //Using new keyword
        System.out.println("Concatenation of both strings x and y is " + x + " " + y);

        System.out.println("Concatenation of both string using a method is "+concatStrings(x,y));

        char firstCharX = 'x'; //Camelcase - first word is lowercase and subsequent words start with uppercase
        char secondChar = 'y';
        System.out.println("Concatenation of two chars firstCharX and secondChar is " + firstCharX + secondChar);
    }

    /**
     * Method that returns the product of 2 given double inputs
     * @param d1 First double input
     * @param d2 Second double input
     * @return Product of the inputs
     */
    private static double multiplyDoubleValues(double d1, double d2) {
        return d1*d2;
    }

    //    Method overloading
    private static float sumFloatMethod(float f1, float f2) {
        return f1+f2;
    }

    /**
     * This method returns the sum of 2 float numbers -- Javadoc
     *
     * @return float Sum of given float input
     */
    private static float sumFloatMethod() {
        float f1 = 45.34f;
        float f2 = 54.78f;
        return (f1 + f2);
    }

    private static String concatStrings(String s1, String s2){
        return s1+" "+s2;
    }
}