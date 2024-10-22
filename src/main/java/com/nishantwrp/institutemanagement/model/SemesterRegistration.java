package com.nishantwrp.institutemanagement.model;

import java.util.List;

public class SemesterRegistration {
    private int id;
    private String studentRollNo;
    private int semesterId;

    public int getId() {
        return id;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    // Extra Fields
    private List<SemesterRegistrationSubject> subjectRegistrations;
    private Semester semester;
    private Student student;

    public List<SemesterRegistrationSubject> getSubjectRegistrations() {
        return subjectRegistrations;
    }

    public void setSubjectRegistrations(List<SemesterRegistrationSubject> subjectRegistrations) {
        this.subjectRegistrations = subjectRegistrations;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
