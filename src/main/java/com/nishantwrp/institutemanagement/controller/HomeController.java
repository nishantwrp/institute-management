package com.nishantwrp.institutemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        addDefaultAttributes(model, session);
        return  "site/home";
    }
}
