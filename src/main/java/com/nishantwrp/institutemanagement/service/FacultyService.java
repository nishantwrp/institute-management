package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.model.Subject;
import com.nishantwrp.institutemanagement.repository.FacultyRepository;
import com.nishantwrp.institutemanagement.repository.SubjectRepository;
import com.nishantwrp.institutemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository faculties;

    @Autowired
    private UserRepository users;

    @Autowired
    private SubjectRepository subjects;

    @Autowired
    private UtilsService utilsService;

    private void getExtraProperties(Faculty faculty) {
        List<Subject> subjectList = subjects.getAllByFaculty(faculty.getId());
        faculty.setAssignedSubjects(subjectList);
    }

    public List<Faculty> getAllFaculties() {
        return faculties.getAll();
    }

    public void addFaculty(Faculty faculty) {
        users.createUser(faculty.getEmail(), utilsService.convertDateToString(faculty.getDob()));
        faculties.createFaculty(faculty, faculty.getEmail());
    }

    public Faculty getFacultyById(String id) {
        Faculty faculty = faculties.getById(Integer.parseInt(id));
        getExtraProperties(faculty);
        return faculty;
    }

    public Faculty getFacultyById(int id) {
        return faculties.getById(id);
    }

    public Faculty getFacultyByEmail(String email) {
        Faculty faculty = faculties.getByEmail(email);
        getExtraProperties(faculty);
        return faculty;
    }

    public void deleteFaculty(Faculty faculty) {
        faculties.deleteFaculty(faculty.getId());
    }

    public void updateFaculty(Faculty faculty) {
        faculties.update(faculty);
    }
}
