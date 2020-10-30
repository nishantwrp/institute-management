package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

abstract class BaseController {
    @Autowired
    private AuthenticationService authenticationService;

    public void addDefaultAttributes(Model model, HttpSession session) {
        String currentUser = authenticationService.getCurrentUser(session);
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }
    }
}
