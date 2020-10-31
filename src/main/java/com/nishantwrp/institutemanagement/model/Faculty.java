package com.nishantwrp.institutemanagement.model;

import java.util.Date;

public class Faculty {
    private int id;
    private String name;
    private String email;
    private String bio;
    private String address;
    private String phone;
    private Date dob;
    private String username;

    public Date getDob() {
        return dob;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getBio() {
        return bio;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
