package com.nishantwrp.institutemanagement.model;

import java.util.Date;

public class Student {
    private String rollNo;
    private String name;
    private String phone;
    private Date dob;
    private String address;
    private String email;
    private String bio;
    private int sessionId;
    private String username;
    private int majorId;
    private int applicationId;

    public Date getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getUsername() {
        return username;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    // Extra Fields
    private Major major;
    private Session session;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
