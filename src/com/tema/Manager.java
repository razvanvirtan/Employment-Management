package com.tema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Manager extends Employee {
    private LinkedList<Request<Job, Consumer>> requests;

    Manager(Resume resume, long salary) {
        super(resume, salary);
        requests = new LinkedList<>();
    }

    Manager(Resume resume, LinkedList<Consumer> contacts) {
        super(resume, contacts);
        requests = new LinkedList<>();
    }

    public void add(Request<Job, Consumer> request) {
        requests.add(request);
    }

    public LinkedList<Request<Job, Consumer>> getRequests() {
        return requests;
    }

    public void process(Job job) {
        LinkedList<Request<Job, Consumer>> matchingRequests;
        matchingRequests = new LinkedList<>();

        // finding the department where this job is available
        Application application = Application.getInstance();
        Company company = application.getCompany(this.getCompany());

        //finding the matching requests
        for (Request<Job, Consumer> request :
                requests) {
            if (request.getKey().equals(job)) {
                matchingRequests.add(request);
            }
        }
        Collections.sort(matchingRequests);

        // hiring process
        int nrHired = 0;
        User currentUser;
        for (Request<Job, Consumer> request: matchingRequests) {
            currentUser = (User) request.getValue1();
            if (application.getUsers().contains(currentUser)) {
                hire(currentUser, job);
                nrHired++;
            }
            if (nrHired == job.getNoPositions()) {
                job.setOpenFlag(false);
                company.notifyAllObservers(job);
                break;
            }
        }

        requests.removeAll(matchingRequests);
    }

    /*
     * Function that hires currentUser for the specified job.
     */
    public void hire(User currentUser, Job job) {
        Employee newEmployee = currentUser.convert();
        Application application = Application.getInstance();
        Company company = application.getCompany(job.getCompanyName());
        LinkedList<Department> departments = company.getDepartments();

        // find the right department
        Department department = null;
        for (Department object: departments) {
            if (object.getJobs().contains(job))
                department = object;
            break;
        }

        // add the new employee to the department
        newEmployee.setSalary(job.getSalary());
        newEmployee.setCompany(job.getCompanyName());
        department.add(newEmployee);

        // modify the social network
        for (Consumer consumer:
                newEmployee.getContacts()) {
            consumer.remove(currentUser);
            consumer.add(newEmployee);
        }

        // remove the ex-user from the notifications lists
        for (Company comp:
                application.getCompanies()) {
            company.removeObserver(currentUser);
        }

        // print used for task2 testing
        System.out.println(job.getCompanyName() + " " + job.getJobName() + " " + currentUser.getName());

        application.getUsers().remove(currentUser);
    }

}
