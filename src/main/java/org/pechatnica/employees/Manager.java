package org.pechatnica.employees;

import java.math.BigDecimal;

public class Manager extends Employee {
    // if the income is higher than a certain amount, +% to the salary
    private BigDecimal percentageAdditionalPay;
    // constructors
    public Manager(String name, BigDecimal percentageAdditionalPay) {
        super(name);
        this.percentageAdditionalPay = percentageAdditionalPay;
    }

    public Manager(BigDecimal percentageAdditionalPay) {
        this.percentageAdditionalPay = percentageAdditionalPay;
    }

    // getter and setter
    public void setPercentageAdditionalPay(BigDecimal percentageAdditionalPay) {
        this.percentageAdditionalPay = percentageAdditionalPay;
    }
    public BigDecimal getPercentageAdditionalPay() {
        return percentageAdditionalPay;
    }

    public BigDecimal getManagerSalary(){
        return super.getSalary().add(getSalary().multiply(percentageAdditionalPay).divide(BigDecimal.valueOf(100)));
    }

    @Override
    public String toString() {
        return "Manager{" +
                "percentageAdditionalPay=" + percentageAdditionalPay +
                "} " + super.toString();
    }
}

