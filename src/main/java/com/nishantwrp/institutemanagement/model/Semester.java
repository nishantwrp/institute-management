package com.nishantwrp.institutemanagement.model;

public class Semester {
    private int id;
    private int fee;
    private String name;
    private int sessionId;

    public String getName() {
        return name;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getId() {
        return id;
    }

    public int getFee() {
        return fee;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
