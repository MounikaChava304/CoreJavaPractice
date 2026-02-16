package com.eureka.inheritance;

import java.math.BigDecimal;

public class AccountsPlayground {
    public static void main(String[] args) {
        CheckingAccount checkingAccount1 = new CheckingAccount("CHK1", new BigDecimal("100"));
        System.out.println("Balance after deposit in checking Account " + checkingAccount1.getAccountNumber() +
                " is : " + checkingAccount1.deposit(new BigDecimal("20")));
        System.out.println("Balance after withdrawal in checking Account " + checkingAccount1.getAccountNumber() +
                " is : " + checkingAccount1.withDraw(new BigDecimal("10")));

        SavingsAccount savingsAccount1 = new SavingsAccount("SAV1", new BigDecimal("100"));
        System.out.println("Balance after deposit in savings Account " + savingsAccount1.getAccountNumber() +
                " is : " + savingsAccount1.deposit(new BigDecimal("20")));
        System.out.println("Balance after withdrawal in savings Account " + savingsAccount1.getAccountNumber() +
                " is : " + savingsAccount1.withDraw(new BigDecimal("10")));
        System.out.println("Balance after interest deposit in savings Account " + savingsAccount1.getAccountNumber() +
                " is : " + savingsAccount1.depositInterestIntoAccount());

        //Polymorphism - Super class reference variable point to a subclass object
        Account checkingAccount2 = new CheckingAccount("CHK2", new BigDecimal("250"));
        Account savingsAccount2 = new SavingsAccount("SAV2", new BigDecimal("300"));

        checkingAccount2.deposit(new BigDecimal("15"));
        checkingAccount2.withDraw(new BigDecimal("15"));
        System.out.println("Balance in checking account " + checkingAccount2.getAccountNumber() + " is " + checkingAccount2.getAccountBalance());

        System.out.println("Checking Account 2 is " + checkingAccount2); //This works because of toString() in CheckingAccount class
        System.out.println("Savings Account 2 is " + savingsAccount2); //This works because of toString() in Account class as toString() method is not overridden in SavingsAccount class.

        savingsAccount2.deposit(new BigDecimal("15"));
        savingsAccount2.withDraw(new BigDecimal("15"));
        System.out.println("Balance in savings account " + savingsAccount2.getAccountNumber() + " is " + savingsAccount2.getAccountBalance());

//      BigDecimal testBD = new BigDecimal("100");

        System.out.println("Is checking account2 an account ? " + (checkingAccount2 instanceof Account));
        System.out.println("Is checking account1 an account ? " + (checkingAccount1 instanceof Account));
//      System.out.println("Is saving account1 an account ? "+(savingsAccount1 instanceof CheckingAccount));// Same as below
//      System.out.println("Is testBD an account ? "+(testBD instanceof Account)); //Not even compiling as Big Decimal is not part of Accounts inheritance hierarchy

        checkingAccount1.printAccountDetails();
        checkingAccount2.printAccountDetails();
        savingsAccount1.printAccountDetails();
        savingsAccount2.printAccountDetails();

        CheckingAccount checkingAccount3 = new CheckingAccount("CHK3", new BigDecimal("1"));
        Account savingsAccount3 = new SavingsAccount("CHK3", new BigDecimal("5"));
        if (checkingAccount3.equals(savingsAccount3)) {
            System.out.println("Both Accounts are same");
        } else {
            System.out.println("Both Accounts are not same");
        }

        //This only works because of Java's Polymorphism
        Account[] accountsArray = new Account[]{checkingAccount1, checkingAccount2, savingsAccount1, savingsAccount2};
        calculateTotalBankDepositBalance(accountsArray);
    }
    /***
     * Method that takes an Account Array and calculates the sum of all bank balances
     * @param accountsArray that contains all Checking and Savings Accounts in the Bank - Polymorphism in action
     */
    private static void calculateTotalBankDepositBalance(Account[] accountsArray) {
        BigDecimal totalBankBalance = BigDecimal.ZERO;
        for (Account eachAccount : accountsArray) {
            totalBankBalance = totalBankBalance.add(eachAccount.getAccountBalance());
        }
        System.out.println("Total Bank Deposit Balance is " + totalBankBalance);
    }
}
