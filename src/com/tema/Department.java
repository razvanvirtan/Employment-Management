package com.tema;

import java.util.ArrayList;

public abstract class Department {
    private ArrayList<Employee> employees;
    private ArrayList<Job> availableJobs;

    Department() {
        employees = new ArrayList<>();
        availableJobs = new ArrayList<>();
    }

    public abstract double getTotalSalaryBudget();

    public ArrayList<Job> getJobs() {
        return availableJobs;
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public void add(Job job) {
        availableJobs.add(job);
    }

    public void remove(Job job) { availableJobs.remove(job); }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

}
