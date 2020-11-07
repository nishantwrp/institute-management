package com.nishantwrp.institutemanagement.model;

public class SemesterRegistrationSubject {
    private int id;
    private int registrationId;
    private int subjectId;

    public int getId() {
        return id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    // Extra Fields
    private SemesterRegistration registration;
    private Subject subject;
    private Result result;

    public Subject getSubject() {
        return subject;
    }

    public SemesterRegistration getRegistration() {
        return registration;
    }

    public Result getResult() {
        return result;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setRegistration(SemesterRegistration registration) {
        this.registration = registration;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
