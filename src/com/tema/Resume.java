package com.tema;

import java.util.Collections;
import java.util.LinkedList;

public class Resume {
    private Information personalInfo;
    private LinkedList<Education> previousEducation;
    private LinkedList<Experience> previousExperience;

    public Information getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(Information personalInfo) {
        this.personalInfo = personalInfo;
    }

    public LinkedList<Education> getPreviousEducation() {
        return previousEducation;
    }

    public void setPreviousEducation(LinkedList<Education> previousEducation) {
        this.previousEducation = previousEducation;
    }

    public LinkedList<Experience> getPreviousExperience() {
        return previousExperience;
    }

    public void setPreviousExperience(LinkedList<Experience> previousExperience) {
        this.previousExperience = previousExperience;
    }

    Resume(ResumeBuilder builder) {
        personalInfo = builder.personalInfo;
        previousEducation = builder.previousEducation;
        previousExperience = builder.previousExperience;
        Collections.sort(previousEducation);
        Collections.sort(previousExperience);
    }

    public static class ResumeBuilder {
        private Information personalInfo;
        private LinkedList<Education> previousEducation;
        private LinkedList<Experience> previousExperience;

        public ResumeBuilder(Information personalInfo,
                             LinkedList<Education> previousEducation) {
            this.personalInfo = personalInfo;
            this.previousEducation = previousEducation;
            previousExperience = new LinkedList<>();
        }

        public ResumeBuilder(Information personalInfo,
                             Education education) {
            this.personalInfo = personalInfo;
            previousEducation = new LinkedList<>();
            previousEducation.add(education);
            previousExperience = new LinkedList<>();
        }

        public ResumeBuilder previousExperience(LinkedList<Experience> previousExperience) {
            this.previousExperience = previousExperience;
            return this;
        }

        public ResumeBuilder previousExperience(Experience experience) {
            previousExperience.add(experience);
            return this;
        }

        public ResumeBuilder previousEducation(Education education) {
            previousEducation.add(education);
            return this;
        }

        public Resume build() throws ResumeIncompleteException{
            if (personalInfo == null)
                throw new ResumeIncompleteException("Lipsesc datele personale!");
            if (previousEducation == null || previousEducation.isEmpty())
                throw new ResumeIncompleteException("Nu există studii!");

            return new Resume(this);
        }
    }
}
