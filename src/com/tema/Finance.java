package com.tema;

import java.time.LocalDate;

public class Finance extends Department {
    public double getTotalSalaryBudget() {
        double budget = 0;
        int employement = 0;

        for (Employee employee:
                getEmployees()) {
            /*
             * calculate experience years on this position, same logic as in
             * getExperienceYears()
             */
            LocalDate currentDate = LocalDate.now();
            LocalDate hiringDate = employee.getResume().getPreviousExperience()
                                    .getLast().getStartDate();
            employement += currentDate.getYear();
            employement -= hiringDate.getYear();
            employement--;
            if (currentDate.getMonthValue() + 12 - hiringDate.getMonthValue() >= 3)
                employement++;

            // calculate total budget
            if (employement >= 1)
                budget += employee.getSalary() * 1.16;
            else
                budget += employee.getSalary() * 1.1;
        }

        return budget;
    }

    public String toString() {
        return "Finance";
    }
}
