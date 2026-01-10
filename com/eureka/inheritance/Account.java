package com.eureka.inheritance;

import java.math.BigDecimal;

public abstract class Account {//Abstract classes cannot have objects created from them
    protected String accountNumber;
    protected BigDecimal accountBalance;

    private Account() {
    }

    public Account(String accountNumber, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /***
     * Method that returns money from an account
     * @param withDrawlAmount amount that needs to be withdrawn
     * @return accountBalance
     */
    public BigDecimal withDraw(BigDecimal withDrawlAmount) {
        accountBalance = accountBalance.subtract(withDrawlAmount);
        return getAccountBalance();
    }

    /***
     * Method that deposits money into an account
     * @param depositAmount amount that needs to be deposited
     * @return account balance
     */
    public BigDecimal deposit(BigDecimal depositAmount) {
        accountBalance = accountBalance.add(depositAmount);
        return getAccountBalance();
    }

    //Abstract methods does not have any implementation in super class but has to be implemented in subclass
    abstract void printAccountDetails();
}
