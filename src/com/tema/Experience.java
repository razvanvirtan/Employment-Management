package com.tema;

import java.time.LocalDate;

public class Experience implements Comparable<Experience> {
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String company;
    private String department;

    public Experience(LocalDate startDate, LocalDate endDate, String position,
                      String company, String department) throws InvalidDateException{
        if (endDate != null && startDate.compareTo(endDate) > 0)
            throw new InvalidDateException("Datele sunt invalide!");
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.company = company;
        this.department = department;
    }

    @Override
    public int compareTo(Experience toCompare) {
        if (toCompare.getEndDate() == null)
            return 1;
        if (endDate == null)
            return -1;
        if (endDate.compareTo(toCompare.getEndDate()) != 0)
            return -1 * endDate.compareTo(toCompare.getEndDate());
        else
            return -1 * company.compareTo(toCompare.getCompany());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
