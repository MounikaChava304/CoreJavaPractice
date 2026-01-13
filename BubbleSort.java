import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] numberArray = new int[]{1, 5, 4, 8, 7, 9};
        int length = numberArray.length;

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (numberArray[j] > numberArray[j + 1]) {
                    int temp = numberArray[j];
                    numberArray[j] = numberArray[j + 1];
                    numberArray[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(numberArray));
    }
}
