package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Semester;
import com.nishantwrp.institutemanagement.model.Session;
import com.nishantwrp.institutemanagement.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesters;

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

    public void deleteSemester(Semester semester) {
        semesters.delete(semester.getId());
    }
}
