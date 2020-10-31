package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.repository.FacultyRepository;
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
    private UtilsService utilsService;

    public List<Faculty> getAllFaculties() {
        return faculties.getAll();
    }

    public void addFaculty(Faculty faculty) {
        users.createUser(faculty.getEmail(), utilsService.convertDateToString(faculty.getDob()));
        faculties.createFaculty(faculty, faculty.getEmail());
    }
}
