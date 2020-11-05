package com.nishantwrp.institutemanagement.model;

public class Major {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Extra fields
    // Can change according to semester
    private CourseStructure courseStructure;

    public CourseStructure getCourseStructure() {
        return courseStructure;
    }

    public void setCourseStructure(CourseStructure courseStructure) {
        this.courseStructure = courseStructure;
    }
}
