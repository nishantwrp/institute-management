package com.nishantwrp.institutemanagement.model;

public class CourseStructureSubject {
    private int id;
    private int subjectId;
    private int courseStructureId;
    private Boolean optional;

    public int getId() {
        return id;
    }

    public Boolean getOptional() {
        return optional;
    }

    public int getCourseStructureId() {
        return courseStructureId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseStructureId(int courseStructureId) {
        this.courseStructureId = courseStructureId;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
