package com.tema;

import java.time.LocalDate;
import java.util.HashMap;

public class Information {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String sex;
    private HashMap<String, String> languages;

    public Information(String name, String surname, String email,
                       String phoneNumber, LocalDate birthDate, String sex,
                       HashMap<String, String> languages) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sex = sex;
        this.languages = languages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setLanguages(HashMap<String, String> languages) {
        this.languages = languages;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public HashMap<String, String> getLanguages() {
        return languages;
    }

    public String getName() {
        return name;
    }
}
