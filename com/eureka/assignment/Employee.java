package com.eureka.assignment;

import java.math.BigDecimal;

public abstract class Employee {
    protected String name;
    protected BigDecimal salary;
    protected String jobTitle;
    protected String jobDuty;

    private Employee() {
    }

    public Employee(String name, BigDecimal salary, String jobTitle, String jobDuty) {
        this.name = name;
        this.salary = salary;
        this.jobTitle = jobTitle;
        this.jobDuty = jobDuty;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDuty() {
        return jobDuty;
    }

    public void setJobDuty(String jobDuty) {
        this.jobDuty = jobDuty;
    }

    //Abstract method that is implemented in subclasses
    public abstract BigDecimal calculateYearlyBonus();
}
