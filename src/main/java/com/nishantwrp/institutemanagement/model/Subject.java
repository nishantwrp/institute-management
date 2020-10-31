package com.nishantwrp.institutemanagement.model;

public class Subject {
    private int id;
    private String name;
    private String code;
    private Integer facultyId;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    // Extra Properties
    private Faculty assignedTo;

    public Faculty getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Faculty assignedTo) {
        this.assignedTo = assignedTo;
    }
}
