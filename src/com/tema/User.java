package com.tema;

import java.util.LinkedList;

public class User extends Consumer implements Observer {
    private LinkedList<String> interestedCompanies;

    public LinkedList<Notification> getNotifications() {
        return notifications;
    }

    private LinkedList<Notification> notifications;

    User(Resume resume, LinkedList<String> interestedCompanies) {
        super(resume);
        this.interestedCompanies = interestedCompanies;
        notifications = new LinkedList<>();
    }

    User(Resume resume, LinkedList<Consumer> contacts,
         LinkedList<String> interestedCompanies) {
        super(resume, contacts);
        this.interestedCompanies = interestedCompanies;
        notifications = new LinkedList<>();
    }

    public Employee convert() {
        return new Employee(getResume(), getContacts());
    }

    public Double getTotalScore() {
        return getAcademicGPA() + 1.5 * getExperienceYears();
    }

    public void update(Notification notification) {
        for (Notification n:
             notifications)
            if (n.getJob() == notification.getJob())
                return;
        notifications.add(notification);
    }

    public LinkedList<String> getInterestedCompanies() {
        return interestedCompanies;
    }

}