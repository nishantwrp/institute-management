package com.nishantwrp.institutemanagement.model;

public class Session {
    private int id;
    private int startYear;
    private Boolean registrationsOpen;
    private Boolean isComplete;

    public Boolean getRegistrationsOpen() {
        return registrationsOpen;
    }

    public int getId() {
        return id;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegistrationsOpen(Boolean registrationsOpen) {
        this.registrationsOpen = registrationsOpen;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void toggleIsComplete() {
        this.isComplete = !this.isComplete;
    }

    public void toggleRegistrationsOpen() {
        this.registrationsOpen = !this.registrationsOpen;
    }
}
