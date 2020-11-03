package com.nishantwrp.institutemanagement.model;

import java.util.Date;

public class RegistrationApplication {
    private int id;
    private String name;
    private String phone;
    private Date date;
    private Date dob;
    private String about;
    private String address;
    private String email;
    private String interestedMajors;
    private int sessionId;

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getAbout() {
        return about;
    }

    public String getInterestedMajors() {
        return interestedMajors;
    }

    public String getPhone() {
        return phone;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setInterestedMajors(String interestedMajors) {
        this.interestedMajors = interestedMajors;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Extra Fields
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
