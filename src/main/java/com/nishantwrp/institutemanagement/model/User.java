package com.nishantwrp.institutemanagement.model;

public class User {
    private String username;
    private String password;
    private Boolean isAdmin;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
