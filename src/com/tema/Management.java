package com.tema;

public class Management extends Department{
    public double getTotalSalaryBudget() {
        double budget = 0;

        for (Employee employee:
                getEmployees()) {
            budget += employee.getSalary() * 1.16;
        }

        return budget;
    }

    public String toString() {
        return "Management";
    }
}
