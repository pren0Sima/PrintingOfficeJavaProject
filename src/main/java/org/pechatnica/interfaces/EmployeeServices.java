package org.pechatnica.interfaces;

import org.pechatnica.employees.Employee;

public interface EmployeeServices {
    public boolean hireEmployee(Employee employee);

    public boolean fireEmployee(Employee employee);
    public void payEmployee(Employee employee);
}
