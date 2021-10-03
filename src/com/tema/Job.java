package com.tema;

import java.util.LinkedList;

public class Job {
    private String jobName;
    private String companyName;
    private boolean openFlag;
    private Constraint<Integer> graduationYear, experienceYears;
    private Constraint<Double> GPA;
    private LinkedList<User> candidates;
    private int noPositions;
    private int salary;

    public Job(String jobName, String companyName, Constraint<Integer> graduationYear, Constraint<Integer> experienceYears, Constraint<Double> GPA, int noPositions, int salary) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.graduationYear = graduationYear;
        this.experienceYears = experienceYears;
        this.GPA = GPA;
        this.noPositions = noPositions;
        this.salary = salary;

        this.openFlag = true;
        this.candidates = new LinkedList<>();

        Company company = Application.getInstance().getCompany(companyName);
        company.notifyAllObservers(this);
    }

    public void apply(User user) {
        Application application = Application.getInstance();
        Company company = application.getCompany(companyName);
        Recruiter recruiter = company.getRecruiter(user);

        company.addObserver(user);
        candidates.add(user);
        recruiter.evaluate(this, user);
    }

    public boolean meetsRequirements(Consumer user) {
        if (graduationYear.getLowerBound() != null)
            if (user.getGraduationYear() != null
                    && user.getGraduationYear() < graduationYear.getLowerBound())
                return false;

        if (graduationYear.getUpperBound() != null)
            if (user.getGraduationYear() != null
                    && user.getGraduationYear() > graduationYear.getUpperBound())
                return false;
            else if (user.getGraduationYear() == null)
                return false;

        if (user.getExperienceYears() < experienceYears.getLowerBound() ||
                (experienceYears.getUpperBound() != null
                && user.getExperienceYears() >  experienceYears.getUpperBound()))
            return false;

       if (user.getAcademicGPA() < GPA.getLowerBound() ||
                (GPA.getUpperBound() != null && user.getAcademicGPA() >  GPA.getUpperBound())) {
           return false;
       }

        return true;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNoPositions() {
        return noPositions;
    }

    public void setNoPositions(int noPositions) {
        this.noPositions = noPositions;
    }

    public boolean isOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(boolean openFlag) {
        this.openFlag = openFlag;
    }

    public LinkedList<User> getCandidates() {
        return candidates;
    }

    public void setCandidates(LinkedList<User> candidates) {
        this.candidates = candidates;
    }

    public int getSalary() {
        return salary;
    }

    public String toString() {
        return jobName;
    }
}
