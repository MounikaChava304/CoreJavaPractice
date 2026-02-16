package com.eureka.assignment;

import java.math.BigDecimal;

public class EmployeesPlayground {
    public static void main(String[] args) {

        Developer dev1 = new Developer("Mounika", new BigDecimal("4000"));
        Developer dev2 = new Developer("Shriya", new BigDecimal("5000"));

        Tester tester1 = new Tester("Mouni", new BigDecimal("2000"));
        Tester tester2 = new Tester("Sai", new BigDecimal("3000"));

        Manager manager1 = new Manager("John", new BigDecimal("7000"));
        Manager manager2 = new Manager("Emily", new BigDecimal("7500"));

        //Adding Subordinates to Manager using setSubOrdinates Methods
        manager1.setSubOrdinates(dev1);
        manager2.setSubOrdinates(dev2);
        manager1.setSubOrdinates(tester1);
        manager2.setSubOrdinates(tester2);

        //Retrieving subOrdinates list of Manager
        manager1.getSubOrdinates();
        manager2.getSubOrdinates();

        Employee[] empArray = new Employee[]{dev1, dev2, tester1, tester2, manager1, manager2};

        System.out.println("Yearly Bonus of Developer "+dev1.getName()+" is : "+dev1.calculateYearlyBonus());
        System.out.println("Yearly Bonus of Tester "+tester1.getName()+" is : "+tester1.calculateYearlyBonus());
        System.out.println("Yearly Bonus of Manager "+manager1.getName()+" is : "+manager1.calculateYearlyBonus());

        totalHandoutBonus(empArray);
    }

    /***
     * This method calculates Total bonus handed out to all employees
     * Calculates and prints the total bonus as output
     * @param empArray contains all the employees in the company
     */
    private static void totalHandoutBonus(Employee[] empArray) {
        BigDecimal totalBonus = BigDecimal.ZERO;
        for (Employee eachEmployee : empArray) {
            totalBonus = totalBonus.add(eachEmployee.calculateYearlyBonus());
        }
        System.out.println("Total bonus of all employees is " + totalBonus);
    }
}
