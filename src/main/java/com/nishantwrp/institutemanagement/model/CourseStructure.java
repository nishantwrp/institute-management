package com.nishantwrp.institutemanagement.model;

import java.util.List;

public class CourseStructure {
    private int id;
    private int majorId;
    private int semesterId;

    public int getId() {
        return id;
    }

    public int getMajorId() {
        return majorId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    // Extra Fields
    private List<Subject> optionalSubjects;
    private List<Subject> compulsorySubjects;
    private Major major;

    public List<Subject> getCompulsorySubjects() {
        return compulsorySubjects;
    }

    public List<Subject> getOptionalSubjects() {
        return optionalSubjects;
    }

    public void setCompulsorySubjects(List<Subject> compulsorySubjects) {
        this.compulsorySubjects = compulsorySubjects;
    }

    public void setOptionalSubjects(List<Subject> optionalSubjects) {
        this.optionalSubjects = optionalSubjects;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
