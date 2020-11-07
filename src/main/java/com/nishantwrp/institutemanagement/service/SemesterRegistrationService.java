package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.*;
import com.nishantwrp.institutemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterRegistrationService {
    @Autowired
    private SemesterRegistrationRepository semesterRegistrations;

    @Autowired
    private SubjectRepository subjects;

    @Autowired
    private SemesterRepository semesters;

    @Autowired
    private SemesterRegistrationSubjectRepository semesterRegistrationSubjects;

    @Autowired
    private ResultRepository results;

    private void getExtraFields(SemesterRegistration semesterRegistration) {
        List<SemesterRegistrationSubject> subjectList = semesterRegistrationSubjects
                .getSubjectsInSemesterRegistration(semesterRegistration.getId());
        for (int i = 0; i < subjectList.size(); i++) {
            Subject subject = subjects.getById(subjectList.get(i).getSubjectId());
            subjectList.get(i).setSubject(subject);

            try {
                Result result = results.getByRegistration(subjectList.get(i).getId());
                subjectList.get(i).setResult(result);
            } catch (Exception e) {}
        }

        Semester semester = semesters.getById(semesterRegistration.getSemesterId());

        semesterRegistration.setSubjectRegistrations(subjectList);
        semesterRegistration.setSemester(semester);
    }

    public SemesterRegistration getSemesterRegistrationByStudentAndSemester(Student student, Semester semester) {
        SemesterRegistration semesterRegistration = semesterRegistrations.getByStudentAndSemester(student.getRollNo(), semester.getId());
        getExtraFields(semesterRegistration);
        return semesterRegistration;
    }

    public SemesterRegistration getSemesterRegistrationById(String id) {
        SemesterRegistration semesterRegistration = semesterRegistrations.getById(Integer.parseInt(id));
        getExtraFields(semesterRegistration);
        return semesterRegistration;
    }

    public void registerForSemester(Student student, Semester semester, List<Integer> subjectIds) {
        semesterRegistrations.register(student.getRollNo(), semester.getId(), subjectIds);
    }

    public List<SemesterRegistration> getAllSemesterRegistrationsByStudent(Student student) {
        List<SemesterRegistration> semesterRegistrationList = semesterRegistrations.getAllByStudent(student.getRollNo());
        for (int i = 0; i < semesterRegistrationList.size(); i++) {
            getExtraFields(semesterRegistrationList.get(i));
        }
        return semesterRegistrationList;
    }
}
