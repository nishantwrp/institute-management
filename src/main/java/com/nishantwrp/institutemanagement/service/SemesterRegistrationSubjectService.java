package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.*;
import com.nishantwrp.institutemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterRegistrationSubjectService {
    @Autowired
    private SemesterRegistrationSubjectRepository semesterRegistrationSubjects;

    @Autowired
    private SubjectRepository subjects;

    @Autowired
    private SemesterRegistrationRepository semesterRegistrations;

    @Autowired
    private ResultRepository results;

    @Autowired
    private SemesterRepository semesters;

    @Autowired
    private StudentRepository students;

    @Autowired
    private SessionRepository sessions;

    private void getExtraFields(SemesterRegistrationSubject semesterRegistrationSubject) {
        Subject subject = subjects.getById(semesterRegistrationSubject.getSubjectId());
        semesterRegistrationSubject.setSubject(subject);

        SemesterRegistration semesterRegistration = semesterRegistrations.getById(semesterRegistrationSubject.getRegistrationId());
        Semester semester = semesters.getById(semesterRegistration.getSemesterId());
        Student student = students.getByRollNo(semesterRegistration.getStudentRollNo());
        Session session = sessions.getById(student.getSessionId());
        student.setSession(session);
        semesterRegistration.setSemester(semester);
        semesterRegistration.setStudent(student);
        semesterRegistrationSubject.setRegistration(semesterRegistration);

        try {
            Result result = results.getByRegistration(semesterRegistrationSubject.getId());
            semesterRegistrationSubject.setResult(result);
        } catch (Exception e) {}
    }

    public List<SemesterRegistrationSubject> getAllBySubject(Subject subject) {
        List<SemesterRegistrationSubject> semesterRegistrationSubjectList = semesterRegistrationSubjects.getAllForSubject(subject.getId());

        for (int i = 0; i < semesterRegistrationSubjectList.size(); i++) {
            getExtraFields(semesterRegistrationSubjectList.get(i));
        }

        return semesterRegistrationSubjectList;
    }

    public SemesterRegistrationSubject getById(String id) {
        SemesterRegistrationSubject semesterRegistrationSubject = semesterRegistrationSubjects.getById(Integer.parseInt(id));
        getExtraFields(semesterRegistrationSubject);
        return semesterRegistrationSubject;
    }
}
