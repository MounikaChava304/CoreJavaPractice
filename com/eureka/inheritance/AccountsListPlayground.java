package com.eureka.inheritance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountsListPlayground {
    public static void main(String[] args) {

        List<CheckingAccount> checkingAccountList = new ArrayList<>();
        CheckingAccount checkingAccount1 = new CheckingAccount("CHK1", new BigDecimal("100"));
        CheckingAccount checkingAccount2 = new CheckingAccount("CHK2", new BigDecimal("200"));
        checkingAccountList.add(checkingAccount1);
        checkingAccountList.add(checkingAccount2);
        checkingAccount1.deposit(new BigDecimal(10));
        checkingAccount1.withDraw(new BigDecimal("10"));
        checkingAccount2.deposit(new BigDecimal("10"));
        checkingAccount2.withDraw(new BigDecimal("10"));

        List<SavingsAccount> savingsAccountList = new ArrayList<>();
        SavingsAccount savingsAccount1 = new SavingsAccount("SAV1", new BigDecimal("100"));
        SavingsAccount savingsAccount2 = new SavingsAccount("SAV2", new BigDecimal("150"));
        savingsAccountList.add(savingsAccount1);
        savingsAccountList.add(savingsAccount2);
        savingsAccount1.deposit(new BigDecimal("15"));
        savingsAccount1.withDraw(new BigDecimal("15"));

        //calculateBankAccountTotals(checkingAccountList);

        //Polymorphism in action here
        List<Account> accountList = new ArrayList<>();
        accountList.add(checkingAccount1);//Able to add subClass objects to List reference of superClass
        accountList.add(checkingAccount2);
        accountList.add(savingsAccount1);
        accountList.add(savingsAccount2);

        calculateBankAccountTotals (accountList);
//      calculateBankAccountTotals (checkingAccountList); //Doesn't compile
        anotherCalculateBankAccountTotals(checkingAccountList);
        anotherCalculateBankAccountTotals(savingsAccountList);
        anotherCalculateBankAccountTotals(accountList);

    }

    /**
     * Method that calculates the totals of account balances sent in the input List
     * (? extends Account) is a Generic definition which specifies that the List can have objects of type Account or its subclasses
     * (? super Account)  is a Generic definition which specifies that the List can have objects of type Account or its superclasses
     * @param accountList - Accepts a List containing objects of type Account or its sub-classes
     */
    private static void anotherCalculateBankAccountTotals(List<? extends Account> accountList) {
        BigDecimal totalBankBalance = BigDecimal.ZERO;
        for (Account eachAccount : accountList) {
            totalBankBalance = totalBankBalance.add(eachAccount.getAccountBalance());
        }
        System.out.println("Total Bank Balance from anotherMethod is "+totalBankBalance);
    }

    /**
     * Method that calculates the totals of account balances sent in the input List
     * @param accountList - Accepts a List containing objects of type Account ONLY
     */
    private static void calculateBankAccountTotals(List<Account> accountList) {
        BigDecimal totalBanksBalance = BigDecimal.ZERO;
        for (Account eachAccount : accountList) {
            totalBanksBalance = totalBanksBalance.add(eachAccount.getAccountBalance());
        }
        System.out.println("Total Bank Balance is "+totalBanksBalance);
    }
}

