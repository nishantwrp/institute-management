package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.service.AuthenticationService;
import com.nishantwrp.institutemanagement.service.FacultyService;
import com.nishantwrp.institutemanagement.service.StudentService;
import com.nishantwrp.institutemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

abstract class BaseController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    public Boolean isAuthenticated(HttpSession session) {
        return authenticationService.isAuthenticated(session);
    }

    public void addDefaultAttributes(Model model, HttpSession session) {
        String currentUser = authenticationService.getCurrentUser(session);
        if (currentUser != null) {
            model.addAttribute("username", currentUser);
            model.addAttribute("userImageUrl", "https://ui-avatars.com/api/?name=" + currentUser);

            String userRole = userService.getRole(currentUser);
            model.addAttribute("userRole", userRole);

            if (userRole.equals("student")) {
                model.addAttribute("userProfile", studentService.getStudentByRollNo(currentUser));
            }

            if (userRole.equals("faculty")) {
                model.addAttribute("userProfile", facultyService.getFacultyByEmail(currentUser));
            }
        }
    }
}
