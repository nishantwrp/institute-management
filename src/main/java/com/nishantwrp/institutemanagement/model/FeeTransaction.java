package com.nishantwrp.institutemanagement.model;

import java.util.Date;

public class FeeTransaction {
    private int id;
    private int amount;
    private Date date;
    private String transactionId;
    private String studentRollNo;
    private int semesterId;

    public int getId() {
        return id;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
