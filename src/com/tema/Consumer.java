package com.tema;

import java.time.LocalDate;
import java.util.*;


public abstract class Consumer {
    private LinkedList<Consumer> contacts;
    private Resume resume;
    int degreeInFriendship; // used in BFS to show when this Consumer was visited
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Consumer(Resume resume) {
        contacts = new LinkedList<>();
        this.resume = resume;
        degreeInFriendship = -1;
    }

    Consumer(Resume resume, LinkedList<Consumer> contacts) {
        this.contacts = contacts;
        this.resume = resume;
    }

    /*
     * functions used to edit/get consumer's personal info
     */
    String getName() {return resume.getPersonalInfo().getName(); }

    String getSurname() {return resume.getPersonalInfo().getSurname();}

    String getEmail() { return resume.getPersonalInfo().getEmail(); }

    String getPhoneNumber() { return  resume.getPersonalInfo().getPhoneNumber(); }

    void editName(String name) {
        resume.getPersonalInfo().setName(name);
    }

    void editSurname(String surname) {
        resume.getPersonalInfo().setSurname(surname);
    }

    void editEmail(String email) {
        resume.getPersonalInfo().setEmail(email);
    }

    void editPhoneNumber(String phoneNumber) {
        resume.getPersonalInfo().setPhoneNumber(phoneNumber);
    }

    void editBirthDate(LocalDate birthDate) {
        resume.getPersonalInfo().setBirthDate(birthDate);
    }

    void addLanguage(String language, String level) {
        resume.getPersonalInfo().getLanguages().put(language, level);
    }

    /*
     * Other functions.
     */
    public void add(Education education) {
        resume.getPreviousEducation().add(education);
        Collections.sort(resume.getPreviousEducation());
    }

    public void add(Experience experience) {
        resume.getPreviousExperience().add(experience);
        Collections.sort(resume.getPreviousExperience());
    }

    public void add(Consumer consumer) {
        contacts.add(consumer);
    }

    public int getDegreeInFriendship(Consumer consumer) {
        int returnedDegree;
        LinkedList<Consumer> consumerQueue = new LinkedList<Consumer>();
        LinkedList<Consumer> visitedConsumers = new LinkedList<Consumer>();

        Consumer current = this;
        current.degreeInFriendship = 0;
        consumerQueue.addFirst(current);
        visitedConsumers.add(current);

        do {
            current = consumerQueue.getFirst();
            for (Consumer contact:
                 current.contacts)
                if (contact.degreeInFriendship == -1) {
                    contact.degreeInFriendship = current.degreeInFriendship + 1;
                    if (contact.equals(consumer))
                        break;
                    consumerQueue.addLast(contact);
                    visitedConsumers.add(contact);
                }
            consumerQueue.removeFirst();
        } while(!consumerQueue.isEmpty());

        returnedDegree = consumer.degreeInFriendship;

        for (Consumer modified:
             visitedConsumers) {
            modified.degreeInFriendship = -1;
        }

        return returnedDegree;
    }


    public void remove(Consumer consumer) {
        contacts.remove(consumer);
    }

    public Integer getGraduationYear() {
        for (Education e:
                resume.getPreviousEducation()) {
            if (e.getLevel().compareTo("college") == 0 && e.getEndDate() != null)
                return e.getEndDate().getYear();
        }
        return null;
    }


    public Integer getExperienceYears() {
        int experienceYears = 0;
        int extraMonths = 0;

        for (Experience e:
                getResume().getPreviousExperience()) {
            experienceYears += e.getEndDate().getYear();
            experienceYears -= e.getStartDate().getYear();
            if (e.getStartDate().getYear() != e.getEndDate().getYear()) {
                experienceYears--;
                extraMonths += e.getEndDate().getMonthValue() + 12 - e.getStartDate().getMonthValue();
            }
            else
                extraMonths += e.getEndDate().getMonthValue() - e.getStartDate().getMonthValue();
        }
        experienceYears += (extraMonths / 12);
        if ((extraMonths % 12) >= 3 ) {
            experienceYears++;
        }
        return experienceYears;
    }

    /*
     * This function calculates the GPA, excluding un-finished cycles.
     */
    public Double getMeanGPA() {
        double mean = 0.0;
        int nr = 0;

        for (Education e:
                resume.getPreviousEducation()) {
            if (e.getEndDate() != null) {
                mean = mean + e.getGrade();
                nr++;
            }
        }
        mean = mean / nr;

        return mean;
    }

    /*
     * This function calculates the GPA, including un-finished cycles.
     */
    public Double getAcademicGPA() {
        double academicGPA = 0.0;
        int nr = 0;

        for (Education e:
                getResume().getPreviousEducation()) {
            academicGPA = academicGPA + e.getGrade();
            nr++;
        }

        academicGPA = academicGPA / nr;

        return academicGPA;
    }

    public LinkedList<Consumer> getContacts() {
        return contacts;
    }

    public void setContacts(LinkedList<Consumer> contacts) {
        this.contacts = contacts;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String toString() {
        return getName() + " " + getSurname();
    }
}
