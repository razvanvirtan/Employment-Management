package com.tema;

public class Marketing extends Department {
    public double getTotalSalaryBudget() {
        double budget = 0;

        for (Employee employee:
                getEmployees()) {
            if (employee.getSalary() > 5000)
                budget += employee.getSalary() * 1.1;
            else if (employee.getSalary() > 3000)
                budget += employee.getSalary() * 1.16;
            else
                budget += employee.getSalary();
        }
        return budget;
    }

    public String toString() {
        return "Marketing";
    }
}
