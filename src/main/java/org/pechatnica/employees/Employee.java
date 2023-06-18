package org.pechatnica.employees;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Employee implements Comparable<Employee> {
    private UUID id;
    private String name;
    private static BigDecimal salary;
    // constructors

    public Employee(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Employee() {
        this.id = UUID.randomUUID();
        this.name = "No name";
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public BigDecimal getSalary() {
        return salary;
    }

    public static void setSalary(BigDecimal s) {
        salary = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int compareTo(Employee o) {
        int idComparison = this.id.compareTo(o.id);
        // if they aren't equal, return;
        if (idComparison != 0) {
            return idComparison;
        }
        // equal
        return 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                 "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
