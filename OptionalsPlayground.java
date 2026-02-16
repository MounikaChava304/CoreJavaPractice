import java.util.Optional;

public class OptionalsPlayground {
    public static void main(String[] args) {
        String inputString = "Eureka";
//        String inputString = null;
        String outputString = null;
//        if(inputString != null){
//            outputString = inputString;
//        }else{
//            outputString = "Whatever";
//        }
//        System.out.println(outputString);

        Optional<String> inputStrOptional = Optional.ofNullable(inputString);
        if(!inputStrOptional.isEmpty()){
            outputString = inputStrOptional.get();
        }else{
            outputString = "Whatever";
        }
        System.out.println("Output String : "+outputString);

        String anotherOutputString = Optional.ofNullable(inputString).orElse("Whatever");
        System.out.println("Another Output String : "+anotherOutputString);

        inputStrOptional.ifPresent((str) -> System.out.println(str)); //Lambda expression for the consumer
        inputStrOptional.ifPresent(System.out::println); //Method reference for the consumer
    }
}
