package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.User;
import com.nishantwrp.institutemanagement.repository.FacultyRepository;
import com.nishantwrp.institutemanagement.repository.StudentRepository;
import com.nishantwrp.institutemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository users;

    @Autowired
    private FacultyRepository faculties;

    @Autowired
    private StudentRepository students;

    public String getRole(String username) {
        User user = users.getUser(username);

        if (user.getIsAdmin()) {
            return "admin";
        }

        try {
            faculties.getByEmail(username);
            return "faculty";
        } catch (Exception e) {}

        try {
            students.getByRollNo(username);
            return "student";
        } catch (Exception e) {}

        return "unknown";
    }
}
