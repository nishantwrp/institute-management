package com.nishantwrp.institutemanagement.model;

import java.util.Date;

public class Payout {
    private int id;
    private String notes;
    private int amount;
    private Date date;
    private String transactionId;
    private int facultyId;

    public int getId() {
        return id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
