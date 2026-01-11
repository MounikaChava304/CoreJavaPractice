package com.eureka.inheritance;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    private static final BigDecimal cashBackRate = new BigDecimal("2.0");

    public CheckingAccount(String accountNumber, BigDecimal accountBalance) {
        super(accountNumber, accountBalance);
    }

    /***
     * Custom overridden WithDraw method that implements CashBack feature
     * @param withDrawlAmount amount that needs to be withdrawn
     * @return account balance
     */
    @Override //Annotation
    public BigDecimal withDraw(BigDecimal withDrawlAmount) {
        BigDecimal cashBackAmount = (withDrawlAmount.multiply(cashBackRate.divide(new BigDecimal("100"))));
        accountBalance = accountBalance.subtract(withDrawlAmount);
        super.deposit(cashBackAmount);
        return getAccountBalance();
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance + '\'' +
                ", cashBackRate =" + cashBackRate +
                '}';
    }

    /***
     * This is a method that child class is forced to implement as
     * it was declared as abstract method in parent class
     */
    @Override
    void printAccountDetails() {
        System.out.println("Checking Account Number is " + getAccountNumber() +
                " , Account Balance is " + getAccountBalance() + " , CashBack Rate is " + cashBackRate);
    }
}
