package com.nishantwrp.institutemanagement.model;

public class Result {
    private int id;
    private String grade;
    private int subjectRegistrationId;

    public int getId() {
        return id;
    }

    public int getSubjectRegistrationId() {
        return subjectRegistrationId;
    }

    public String getGrade() {
        return grade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSubjectRegistrationId(int subjectRegistrationId) {
        this.subjectRegistrationId = subjectRegistrationId;
    }
}
