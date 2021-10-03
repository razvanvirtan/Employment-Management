package com.tema;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void parseJSON() throws IOException, ParseException {
        TestHelper helpers = new TestHelper();
        //creating application
        Application application = Application.getInstance();

        //creating departments and companies
        Company Google, Amazon;
        Google = new Company("Google");
        Amazon = new Company("Amazon");

        application.add(Google);
        application.add(Amazon);

        DepartmentFactory departmentFactory = DepartmentFactory.getInstance();
        Google.add(departmentFactory.createDepartment("IT"));
        Google.add(departmentFactory.createDepartment("Management"));
        Google.add(departmentFactory.createDepartment("Marketing"));
        Google.add(departmentFactory.createDepartment("Finance"));
        Amazon.add(departmentFactory.createDepartment("IT"));
        Amazon.add(departmentFactory.createDepartment("Management"));
        Amazon.add(departmentFactory.createDepartment("Marketing"));
        Amazon.add(departmentFactory.createDepartment("Finance"));

        // reading from JSON file
        LinkedList<Consumer> allConsumers = new LinkedList<Consumer>();
        Object obj = new JSONParser().parse(new FileReader("resources/consumers.json"));
        JSONObject jo = (JSONObject) obj;

        // completing already hired employees
        JSONArray employees = ((JSONArray) jo.get("employees"));
        for (Object o :
                employees) {
            JSONObject employeeObj = (JSONObject) o;
            long salary = (long) employeeObj.get("salary");
            Resume resume;

            try {
                resume = helpers.getResume(employeeObj);
                Employee newEmployee = new Employee(resume, salary);
                Company company = application.getCompany(newEmployee.getCompany());
                for (Department department:
                        company.getDepartments()) {
                    if (department.toString().equals(newEmployee.getResume()
                            .getPreviousExperience().getFirst().getDepartment())) {
                        department.add(newEmployee);
                    }
                }
                newEmployee.setPassword("POO");
                allConsumers.add(newEmployee);
            } catch (ResumeIncompleteException e) {
                    System.out.println("Resume incomplete!");
            }
        }

            // completing recruiters
        JSONArray recruiters = (JSONArray) jo.get("recruiters");
        for (Object o:
                recruiters) {
            JSONObject recruiterObj = (JSONObject) o;
            long salary = (long) recruiterObj.get("salary");
            Resume resume;
            try {
                resume = helpers.getResume(recruiterObj);
                Recruiter newRecruiter = new Recruiter(resume, salary);
                Company company = application.getCompany(newRecruiter.getCompany());
                for (Department department:
                        company.getDepartments()) {
                    if (department.toString().equals("IT"))
                        department.add(newRecruiter);
                }
                company.add(newRecruiter);
                newRecruiter.setPassword("POO");
                allConsumers.add(newRecruiter);
            }
            catch (ResumeIncompleteException e) {
                System.out.println("Resume incomplete");
            }
        }

            // completing managers
        JSONArray managers = (JSONArray) jo.get("managers");
        for (Object o :
                managers) {
            JSONObject managerObj = (JSONObject) o;
            long salary = (long) managerObj.get("salary");
            Resume resume;
            try {
                resume = helpers.getResume(managerObj);
                Manager newManager = new Manager(resume, salary);
                Company company = application.getCompany(newManager.getCompany());
                company.setManager(newManager);
                newManager.setPassword("POO");
            }
            catch(ResumeIncompleteException e) {
                System.out.println("Resume incomplete!");
            }
        }

        // completing users
        JSONArray users = (JSONArray) jo.get("users");
        for (Object o:
                users) {
            JSONObject userObj = (JSONObject) o;
            Resume resume;
            try {
                resume = helpers.getResume(userObj);
                JSONArray companies = (JSONArray) userObj.get("interested_companies");
                LinkedList<String> interestedCompanies = new LinkedList<>();
                for (Object o1:
                        companies) {
                    interestedCompanies.add((String) o1);
                }
                User newUser = new User(resume, interestedCompanies);
                application.add(newUser);
                allConsumers.add(newUser);
                newUser.setPassword("POO");
            }
            catch(ResumeIncompleteException e) {
                System.out.println("Resume incomplete!");
            }
        }

        // creating social network
        // (U1, U2)
        allConsumers.get(14).add(allConsumers.get(15));
        allConsumers.get(15).add(allConsumers.get(14));
        // (U1, E3)
        allConsumers.get(14).add(allConsumers.get(2));
        allConsumers.get(2).add(allConsumers.get(14));
        // (U2, R1)
        allConsumers.get(15).add(allConsumers.get(10));
        allConsumers.get(10).add(allConsumers.get(15));
        // (U2, E7)
        allConsumers.get(15).add(allConsumers.get(6));
        allConsumers.get(6).add(allConsumers.get(15));
        // (U3, E3)
        allConsumers.get(16).add(allConsumers.get(2));
        allConsumers.get(2).add(allConsumers.get(16));
        // (U3, U4)
        allConsumers.get(16).add(allConsumers.get(17));
        allConsumers.get(17).add(allConsumers.get(16));
        // (U4, E10)
        allConsumers.get(17).add(allConsumers.get(9));
        allConsumers.get(9).add(allConsumers.get(17));
        // (E2, E10)
        allConsumers.get(1).add(allConsumers.get(9));
        allConsumers.get(9).add(allConsumers.get(1));
        // (E2, R3)
        allConsumers.get(1).add(allConsumers.get(12));
        allConsumers.get(12).add(allConsumers.get(1));
        // (E3, E6)
        allConsumers.get(2).add(allConsumers.get(5));
        allConsumers.get(5).add(allConsumers.get(2));
        // (E3, R2)
        allConsumers.get(2).add(allConsumers.get(11));
        allConsumers.get(11).add(allConsumers.get(2));
        // (E6, R4)
        allConsumers.get(5).add(allConsumers.get(13));
        allConsumers.get(13).add(allConsumers.get(5));

        // creating available jobs
        application.getCompany("Google").getDepartments().get(0).add(
                new Job("SDE", "Google",
                        new Constraint<Integer>(2020, 2002),
                        new Constraint<Integer>(6, 2),
                        new Constraint<Double>(null, 9.0),
                        1, 10000)
            );
        application.getCompany("Google").getDepartments().get(0).add(
                new Job("SDE Intern", "Google",
                        new Constraint<Integer>(null, null),
                        new Constraint<Integer>(2, 0),
                        new Constraint<Double>(null, 9.0),
                        1, 5000)
            );
        application.getCompany("Amazon").getDepartments().get(0).add(
                new Job("SDE", "Amazon",
                        new Constraint<Integer>(2020, 2014),
                        new Constraint<Integer>(null, 1),
                        new Constraint<Double>(null, 9.0),
                        1, 12000)
            );
        application.getCompany("Amazon").getDepartments().get(0).add(
                new Job("SDE Intern", "Amazon",
                        new Constraint<Integer>(null, null),
                        new Constraint<Integer>(2, 0),
                        new Constraint<Double>(null, 9.35),
                        1, 6000)
            );

            // apply to jobs
        for (User user:
                application.getUsers()) {
            ArrayList<Job> jobs = application.getJobs(user.getInterestedCompanies());
            for (Job job:
                    jobs) {
                job.apply(user);
            }
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        TestHelper helpers = new TestHelper();
        //creating application
        Application application = Application.getInstance();

        parseJSON();

        // Test getDegreeInFriendship (U1 - Daniel & U4 - Linette)
        System.out.println("--------------------------------------");
        System.out.println("Test degreeInFriendship:");
        System.out.println(application.getUsers().get(0).getName() + " " +
                application.getUsers().get(3).getName() + " " +
                application.getUsers().get(0).getDegreeInFriendship(
                        application.getUsers().get(3)
                ));

        System.out.println();
        System.out.println("Angajarile facute:");
        // process applications
        for (Company company:
                application.getCompanies()) {
            for (Job job:
                    company.getJobs()) {
                company.getManager().process(job);
                }
            }
        }
}
