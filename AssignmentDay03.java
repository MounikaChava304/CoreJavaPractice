//Write a Java program to check if a string is a palindrome.
//""abba"",...are palindromes
//""abcd"", ""test"" are not palindromes

public class AssignmentDay03 {
    public static void main(String[] args) {
        String inputString1 = "abcd";
        String inputString2 = "abba";
        String inputString3 = "test";

        checkPalindrome(inputString1);
        checkPalindrome(inputString2);
        checkPalindrome(inputString3);
    }

    /***
     * This method checks if the input String that is passed as a parameter is a palindrome
     * and prints appropriate output
     * @param inputString a string
     */
    public static void checkPalindrome(String inputString) {
        String reverseString = "";
        //for r loop to take the last letter of the string first to create a reverse string
        for (int index = inputString.length() - 1; index >= 0; index--) {
            reverseString += inputString.charAt(index);
            //System.out.println(reverseString);
        }
        if (inputString.equals(reverseString)) {
            System.out.println(inputString + " is a palindrome");
        } else {
            System.out.println(inputString + " is not a palindrome");
        }
    }
}
