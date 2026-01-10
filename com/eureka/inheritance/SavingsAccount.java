package com.eureka.inheritance;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    private static final BigDecimal savingsAPR = new BigDecimal("3.0");

    public SavingsAccount(String accountNumber, BigDecimal accountBalance) {
        super(accountNumber, accountBalance);//Calling the super class Account's parameterized constructor
    }

    /***
     * This is a method that child class is forced to implement as
     * it was declared as abstract method in parent class
     */
    @Override
    void printAccountDetails() {
        System.out.println("Savings Account Number is " + getAccountNumber() +
                " , Account Balance is " + getAccountBalance() + " , savings Interest Rate is " + savingsAPR);
    }

    /***
     * Method that is used to calculate interest amount t be deposited each month into the savings account
     * @return accountBalance
     */
    public BigDecimal depositInterestIntoAccount() {
        BigDecimal interestAmount = accountBalance.multiply(savingsAPR.divide(new BigDecimal("100")));
        super.deposit(interestAmount);
        return getAccountBalance();
    }
}
