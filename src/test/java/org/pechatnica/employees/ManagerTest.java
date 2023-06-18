package org.pechatnica.employees;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {
    @Test
    void getManagerSalaryTest() {
        // creating an instance for manager
        BigDecimal percentageAdditionalPay = BigDecimal.valueOf(10);
        Manager manager = new Manager("Simon", percentageAdditionalPay);
        BigDecimal baseSalary = BigDecimal.valueOf(1000);
        Employee.setSalary(baseSalary);

        // Calculate the expected salary value
        BigDecimal expectedSalary = BigDecimal.valueOf(1100);
        // BigDecimal expectedSalary = baseSalary.add(baseSalary.multiply(percentageAdditionalPay).divide(BigDecimal.valueOf(100)));

        // Call the getSalary method and verify the result
        BigDecimal actualSalary = manager.getManagerSalary();
        assertEquals(expectedSalary, actualSalary);
    }
}