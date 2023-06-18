package org.pechatnica.employees;

public class Operator extends Employee {
    // constructors
    public Operator(String name) {
        super(name);
    }
    public Operator() {}
    @Override
    public String toString() {
        return "Operator{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", salary=" + getSalary() +
                '}';
    }
}

