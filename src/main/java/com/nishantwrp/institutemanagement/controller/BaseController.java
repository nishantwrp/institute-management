package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.service.AuthenticationService;
import com.nishantwrp.institutemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

abstract class BaseController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    public Boolean isAuthenticated(HttpSession session) {
        return authenticationService.isAuthenticated(session);
    }

    public void addDefaultAttributes(Model model, HttpSession session) {
        String currentUser = authenticationService.getCurrentUser(session);
        if (currentUser != null) {
            model.addAttribute("username", currentUser);
            model.addAttribute("userImageUrl", "https://ui-avatars.com/api/?name=" + currentUser);
            model.addAttribute("userRole", userService.getRole(currentUser));
        }
    }
}
