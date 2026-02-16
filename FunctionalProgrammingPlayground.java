import java.math.BigDecimal;
import java.util.function.*;

public class FunctionalProgrammingPlayground {
    public static void main(String[] args) {

        //Example of a UnaryOperator
        UnaryOperator<String> stringConcatUO = (str) -> str+str; //Lambda expression
//        UnaryOperator<String> stringConcatUO =(str) -> { //This is also a Lambda Expression.
//            return str+str; //This syntax is written when multiple lines of code needs to be written
//        };
        System.out.println("Method call for UO is "+stringConcat("Eureka"));
        System.out.println("Method call using UO is "+stringConcatUO.apply("Eureka"));

        //Example of BinaryOperator
        System.out.println("Adding two Big Decimals "+addBD(new BigDecimal("100"), new BigDecimal("150")));
        BinaryOperator<BigDecimal> addBD_BO = (bd1,bd2) -> bd1.add(bd2);
        BinaryOperator<BigDecimal> addBD_BO_MR = BigDecimal::add; //Method Reference Expression
        System.out.println("Adding two Big Decimals using BO is "+addBD_BO_MR.apply(new BigDecimal(100), new BigDecimal(150)));

        //Example of a Consumer
        someConsumingMethod(10);
        Consumer<Integer> someConsumer = (i) -> System.out.println("Using print inside Consumer Functional Interface : "+i); //Using Lambda Expression
        Consumer<Integer> someOtherConsumer = System.out::println; //Method Reference equivalent
        someOtherConsumer.accept(90);
        someConsumer.accept(10);

        //Example of a Supplier
        System.out.println("Value generated from a Supplier method is : "+someRandomSupplier());
        Supplier<Double> someRandomFISupplier = ()-> Math.random(); //Lambda Expression
        Supplier<Double> someRandomFIMRSupplier = Math::random; //Method Reference equivalent
        System.out.println("Value generated from a Functional Interface Supplier is : "+someRandomFIMRSupplier.get());

        //Example of a Predicate
        System.out.println("Is the String long enough ? : "+isStringLong("Eureka"));
        Predicate<String> isStringLongPD = (str) -> str.length()>10; //Lambda Expression
        System.out.println("Is the String long enough, with Predicate ? : "+isStringLongPD.test("Eureka"));

        //Example of BiPredicate
        System.out.println("Some Junk Logic : "+someJunkLogicMethod("Eureka", new BigDecimal(100)));
        BiPredicate<String, BigDecimal> someJunkLogicBPD = (str, bd) ->
                bd.multiply(new BigDecimal(str.length())).compareTo(new BigDecimal(1000)) > 0; //Lambda Expression
        System.out.println("Some Junk Logic, with BiPredicate : "+someJunkLogicBPD.test("Eureka",new BigDecimal(100)));

        //Example of a Function
        System.out.println("Function Logic something is : "+someFunctionLogic(new BigDecimal(58)));
        Function<BigDecimal, String> someFunctionLogicFNC = bigDecimal -> "Whatever"+bigDecimal; //Lambda Expression
        System.out.println("Function Logic something using FNC FI is : "+someFunctionLogicFNC.apply(new BigDecimal(58)));

        //Another Example of a Function
        System.out.println("Calculate String Length Multiplication is : "+strLenMul("Eureka"));
        Function<String, Integer> strLenMulFNC = (str)->str.length()*10;
        System.out.println("Calculate String Length Multiplication using FNC FI is : "+strLenMulFNC.apply("Eureka"));

        //Chaining Multiple Functions
        Function<String, Integer> firstOperation = String::length; //Method Reference
        System.out.println("Output after firstOperation is "+firstOperation.apply("Eureka"));
        Function<String, Integer> secondOperation = firstOperation.andThen(integer -> integer*10); //Lambda Expression
        System.out.println("Output after Second Operation is "+secondOperation.apply("Eureka"));

        //Example of BiFunction
        System.out.println("Some Bi Function method that needs to be figured out "+someBiFunctMethod("Eureka", new BigDecimal(100)));
        BiFunction<String, BigDecimal, Integer> someBiFuncFI = (str,bd) -> { //Lambda Expression
            BigDecimal someValue = bd.multiply(new BigDecimal(str.length()));
            return Integer.parseInt(someValue.toPlainString());
        };
        System.out.println("Some BiFunct FI output is "+someBiFuncFI.apply("Eureka", new BigDecimal(100)));
    }

    //Method equivalent of BiFunction
    private static Integer someBiFunctMethod(String str, BigDecimal bd) {
        BigDecimal someValue = bd.multiply(new BigDecimal(str.length()));
        return Integer.parseInt(someValue.toPlainString());
    }

    //Another Method equivalent of a function
    private static Integer strLenMul(String str) {
        return str.length()*10;
    }

    //Method equivalent of a function
    private static String someFunctionLogic(BigDecimal bigDecimal) {
        return "Whatever"+bigDecimal;
    }

    //Method equivalent of Bi Predicate
    private static boolean someJunkLogicMethod(String str, BigDecimal bd) {
        return bd.multiply(new BigDecimal(str.length())).compareTo(new BigDecimal(1000)) > 0;
    }

    //Method Equivalent of a Predicate
    private static boolean isStringLong(String str) {
        return str.length() > 10;
    }

    //Method equivalent of a Supplier
    private static Double someRandomSupplier() {
        return Math.random();
    }

    //Method equivalent of a Consumer
    private static void someConsumingMethod(Integer i) {
        System.out.println("In consumer Method, value is "+i);
    }

    private static BigDecimal addBD(BigDecimal bd1, BigDecimal bd2) {
        return bd1.add(bd2);
    }



    //Method equivalent of a unary operator
    private static String stringConcat(String str) {
        return str+str;
    }
}
