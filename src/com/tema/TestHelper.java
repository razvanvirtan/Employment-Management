package com.tema;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

public class TestHelper {
    // Function that converts a String in a LocalDate object
    public LocalDate convertStringToDate(String dateString) {
        if (dateString == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    // Function that converta a JSONObject in an Information object
    public Information getPersonalInfo(JSONObject object) {
        String name = ((String) object.get("name")).split(" ")[0];
        String surname = ((String) object.get("name")).split(" ")[1];
        String email = (String) object.get("email");
        String phoneNumber = (String) object.get("phone");
        String sex = (String) object.get("genre");
        LocalDate birthDate = convertStringToDate((String) object.get("date_of_birth"));

        JSONArray langs = (JSONArray) object.get("languages");
        JSONArray levels = (JSONArray) object.get("languages_level");
        HashMap<String, String> languages = new HashMap<String, String>();
        for (int i = 0; i < langs.size(); i++) {
            languages.put((String) langs.get(i), (String) levels.get(i));
        }

        return new Information(name, surname, email, phoneNumber, birthDate, sex, languages);
    }

    public LinkedList<Education> getEducation(JSONArray array) {
        LinkedList<Education> educationList = new LinkedList<Education>();

        for (Object o:
             array) {
            JSONObject education = (JSONObject) o;
            String institution = (String) education.get("name");
            String level = (String) education.get("level");
            Double grade;
            try {
                grade = (Double) education.get("grade");
            }
            catch(ClassCastException e) {
                grade = ((Long) education.get("grade")).doubleValue();
            }

            LocalDate startDate = convertStringToDate((String) education.get("start_date"));
            LocalDate endDate = convertStringToDate((String) education.get("end_date"));
            try {
                educationList.add(new Education(institution, level, grade, startDate, endDate));
            }
            catch(InvalidDateException e) {
                System.out.println("Data de sfarsit invalida pentru " + institution);
            }
        }

        return educationList;
    }

    public LinkedList<Experience> getExperience(JSONArray array) {
        LinkedList<Experience> experienceList = new LinkedList<Experience>();

        for (Object o:
                array) {
            JSONObject experience = (JSONObject) o;
            String company = (String) experience.get("company");
            String position = (String) experience.get("position");
            String department = (String) experience.get("department");
            if (department == null)
                department = "IT";
            LocalDate startDate = convertStringToDate((String) experience.get("start_date"));
            LocalDate endDate = convertStringToDate((String) experience.get("end_date"));
            try {
                experienceList.add(new Experience(startDate, endDate, position, company, department));
            }
            catch(InvalidDateException e) {
                System.out.println("Data de sfarsit invalida pentru " + company);
            }
        }

        return experienceList;
    }

    public Resume getResume(JSONObject object) throws ResumeIncompleteException {
        Information personalInfo = getPersonalInfo(object);
        LinkedList<Education> previousEducation = getEducation((JSONArray)
                object.get("education"));
        LinkedList<Experience> previousExperience = getExperience((JSONArray)
                object.get("experience"));
        Resume resume = (new Resume.ResumeBuilder(personalInfo, previousEducation))
                .previousExperience(previousExperience).build();

        return resume;
    }
}
