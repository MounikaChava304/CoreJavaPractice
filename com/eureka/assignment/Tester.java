package com.eureka.assignment;

import java.math.BigDecimal;

public class Tester extends Employee {

    //Constructor inherited from super class that takes parameters to create an employee object
    //Here the jobTitle and jobDuty is fixed for all testers. So just taking name and salary as i/p parameters
    public Tester(String name, BigDecimal salary) {
        super(name, salary, "Tester", "Test Applications");
    }

    /***
     * Method that is used to calculate yearly bonus of the employees
     * @return yearly bonus of that employee
     */
    @Override
    public BigDecimal calculateYearlyBonus() {
        return (getSalary().multiply(new BigDecimal("0.05")).multiply(new BigDecimal("12")));
    }
}
