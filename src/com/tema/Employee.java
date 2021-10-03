package com.tema;

import java.util.LinkedList;

public class Employee extends Consumer {
    private String company;
    private long salary;
    private String department;

    Employee(Resume resume, LinkedList<Consumer> contacts) {
        super(resume);
        setContacts(contacts);
    }
    Employee(Resume resume, long salary) {
        super(resume);
        company = resume.getPreviousExperience().getFirst().getCompany();
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
