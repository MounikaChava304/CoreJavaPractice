package com.eureka.assignment;

import java.math.BigDecimal;

public class Manager extends Employee {
    private Employee[] subOrdinates;
    private int empCount;

    //Constructor inherited from super class that takes parameters to create an employee object
    //Here the jobTitle and jobDuty is fixed for all managers. So just taking name and salary as i/p parameters
    public Manager(String name, BigDecimal salary) {
        super(name, salary, "Manager", "Manage people");
        subOrdinates = new Employee[4];
        empCount = 0;
    }

    /**
     * This method adds an employee as a subordinate to a manager
     * @param emp employee object is taken as input
     */
    public void setSubOrdinates(Employee emp) {
        if (empCount < subOrdinates.length) {
            subOrdinates[empCount++] = emp;
        }
    }

    /***
     * This method is used to get all subordinates under a particular manager
     */
    public void getSubOrdinates() {
            System.out.println("Employees reporting to "+getName()+" are : ");
            for (Employee subOrdinate : subOrdinates) {
                if (subOrdinate != null) {
                    System.out.println(subOrdinate.name + " , " + subOrdinate.jobTitle);
            }
        }
    }

    /***
     * Method that is used to calculate yearly bonus of the employees
     * @return yearly bonus of that employee
     */
    @Override
    public BigDecimal calculateYearlyBonus() {
        return (getSalary().multiply(new BigDecimal("0.20")).multiply(new BigDecimal("12")));
    }
}
