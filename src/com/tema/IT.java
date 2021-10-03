package com.tema;

public class IT extends Department{
    public double getTotalSalaryBudget() {
        double budget = 0;

        for (Employee employee:
             getEmployees()) {
            budget += employee.getSalary();
        }

        return budget;
    }

    public String toString() {
        return "IT";
    }
}
