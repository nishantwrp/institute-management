package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.*;
import com.nishantwrp.institutemanagement.repository.CourseStructureRepository;
import com.nishantwrp.institutemanagement.repository.SemesterRegistrationRepository;
import com.nishantwrp.institutemanagement.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesters;

    @Autowired
    private CourseStructureRepository courseStructures;

    @Autowired
    private SemesterRegistrationRepository semesterRegistrations;

    public List<Semester> getAllSemestersInSession(Session session) {
        return semesters.getAllInSession(session.getId());
    }

    public void createSemester(Session session, Semester semester) {
        semester.setSessionId(session.getId());
        semesters.create(semester);
    }

    public Semester getSemesterById(String id) {
        return semesters.getById(Integer.parseInt(id));
    }

    public Semester getSemesterById(int id) {
        return semesters.getById(id);
    }

    public void deleteSemester(Semester semester) {
        semesters.delete(semester.getId());
    }

    public List<Semester> getAllSemestersForStudent(Student student) {
        List<Semester> allSemesters = semesters.getAllInSession(student.getSessionId());
        List<Semester> semesterList = new ArrayList<>();

        for (int i = 0; i < allSemesters.size(); i++) {
            try {
                courseStructures.getByMajorAndSemester(student.getMajorId(), allSemesters.get(i).getId());
                Semester semester = allSemesters.get(i);

                try {
                    SemesterRegistration semesterRegistration = semesterRegistrations.getByStudentAndSemester(
                            student.getRollNo(), semester.getId());
                    semester.setSemesterRegistration(semesterRegistration);
                } catch (Exception e) {}

                semesterList.add(semester);
            } catch (Exception e) {}
        }

        return semesterList;
    }
}
