package com.tema;

import java.time.LocalDate;

public class Education implements Comparable<Education> {
    private String institution;
    private String level;
    private Double grade;
    private LocalDate startDate;
    private LocalDate endDate;

    public Education(String institution, String level, double grade,
                     LocalDate startDate, LocalDate endDate)
            throws InvalidDateException {
        if (startDate == null || (endDate != null && startDate.compareTo(endDate) > 0))
            throw new InvalidDateException("Datele sunt invalide!");

        this.institution = institution;
        this.level = level;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public int compareTo(Education toCompare) {
        if (toCompare.getEndDate() == null)
            return 1;
        if (endDate == null)
            return -1;
        if (endDate.compareTo(toCompare.getEndDate()) != 0)
            return -1 * endDate.compareTo(toCompare.getEndDate());
        else
            return -1 * grade.compareTo(toCompare.getGrade());
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
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

}
