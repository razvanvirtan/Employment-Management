package com.tema;

import java.util.ArrayList;
import java.util.LinkedList;

public class Application {
    private static Application application;
    private ArrayList<Company> companies;
    private ArrayList<User> users;

    private Application() {
        companies = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static Application getInstance() {
        if (application == null)
            application = new Application();
        return application;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Company getCompany(String name) {
        for (Company company:
             companies) {
            if (company.getName().compareTo(name) == 0)
                return company;
        }
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean remove(Company company) {
        if (companies.contains(company)) {
            companies.remove(company);
            return true;
        }
        return false;
    }

    public boolean remove(User user) {
        if (users.contains(user)) {
            users.remove(user);
            return true;
        }
        return false;
    }

    public ArrayList<Job> getJobs(LinkedList<String> companies) {
        ArrayList<Job> availableJobs = new ArrayList<>();

        for (String companyName:
             companies) {
            availableJobs.addAll(getCompany(companyName).getJobs());
        }

        return availableJobs;
    }

    /*
     * function that returns all employees from all companies, used to search
     * for an employee during the login process or when searching other users
     * in GUI
     */
    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Company company:
             companies) {
            for (Department department:
                 company.getDepartments()) {
                employees.addAll(department.getEmployees());
            }
        }
        return employees;
    }
}
