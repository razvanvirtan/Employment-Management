package com.tema;

import java.util.ArrayList;
import java.util.LinkedList;

public class Company extends Subject {
    private String name;
    private Manager manager;
    private LinkedList<Department> departments;
    private LinkedList<Recruiter> recruiters;

    Company(String name) {
        this.name = name;;
        departments = new LinkedList<>();
        recruiters = new LinkedList<>();
    }

    public void notifyAllObservers(Job job) {
        for (Observer observer:
             observers) {
            if (job.isOpenFlag()) // an available job was added
                observer.update(new Notification(job, "OPEN"));
            else if (job.getCandidates().contains(observer))
                observer.update(new Notification(job, "REJECTED"));
            else
                observer.update(new Notification(job, "CLOSED"));
        }
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.add(employee);
    }

    public void remove(Employee employee) {
        for (Department department:
             departments) {
            if (department.getEmployees().contains(employee)) {
                department.remove(employee);
                break;
            }
        }
        if (recruiters.contains((Recruiter) employee))
            recruiters.remove((Recruiter) employee);
    }

    public void remove(Department department) {
            for (Employee employee:
                 department.getEmployees()) {
                if (recruiters.contains((Recruiter) employee))
                    recruiters.remove((Recruiter) employee);
            }
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    public void move(Department source, Department destination) {
        destination.getEmployees().addAll(source.getEmployees());
        source.getEmployees().clear();
    }

    public void move(Employee employee, Department newDepartment) {
        newDepartment.getEmployees().add(employee);
        for (Department department:
             departments) {
            if (department != newDepartment &&
                    department.getEmployees().contains(employee)) {
                department.remove(employee);
                break;
            }
        }
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        for (Department department:
             departments) {
            if (department.getEmployees().contains(employee))
                return true;
        }
        return false;
    }

    public boolean contains(Recruiter recruiter) {
        return recruiters.contains(recruiter);
    }

    public Recruiter getRecruiter(User user) {
        LinkedList<Consumer> consumerQueue = new LinkedList<Consumer>();
        LinkedList<Consumer> visitedConsumers = new LinkedList<Consumer>();

        // find user's connections
        Consumer current = user;
        current.degreeInFriendship = 0;
        consumerQueue.addFirst(current);
        visitedConsumers.add(current);
        do {
            current = consumerQueue.getFirst();
            for (Consumer contact:
                    current.getContacts()) {
                if (contact.degreeInFriendship == -1) {
                    contact.degreeInFriendship = current.degreeInFriendship + 1;
                    consumerQueue.addLast(contact);
                    visitedConsumers.add(contact);
                }
            }
            consumerQueue.removeFirst();
        } while (!consumerQueue.isEmpty());

        // find the right recruiter among user's connections
        Recruiter recruiter = recruiters.getFirst();
        for (Recruiter iterator:
             recruiters) {
            if (iterator.degreeInFriendship > recruiter.degreeInFriendship)
                recruiter = iterator;
            if (iterator.degreeInFriendship == recruiter.degreeInFriendship)
                if (iterator.getRating() > recruiter.getRating())
                    recruiter = iterator;
        }
        // restore the degreeInFriendship field (un-visit all connections)
        for (Consumer modified:
                visitedConsumers) {
            modified.degreeInFriendship = -1;
        }

        return recruiter;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> availableJobs = new ArrayList<Job>();

        for (Department department:
             departments) {
            availableJobs.addAll(department.getJobs());
        }

        return availableJobs;
    }

    public LinkedList<Department> getDepartments() {
        return departments;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Recruiter> getRecruiters() {
        return recruiters;
    }

    public String toString() {
        return name;
    }
}
