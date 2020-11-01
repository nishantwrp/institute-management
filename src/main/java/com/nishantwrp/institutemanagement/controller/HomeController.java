package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.model.RegistrationApplication;
import com.nishantwrp.institutemanagement.model.Session;
import com.nishantwrp.institutemanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    @Autowired
    private MajorService majorService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ToastService toastService;

    @Autowired
    private RegistrationApplicationService registrationApplicationService;

    // Needed to automatically convert String date in form to Date object.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        addDefaultAttributes(model, session);

        model.addAttribute("majors", majorService.getAllMajors());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return  "site/home";
    }

    @GetMapping("/registration")
    public String registration(Model model, HttpSession session, RedirectAttributes attributes) {
        addDefaultAttributes(model, session);

        List<Session> sessionList = sessionService.getAllSessions();

        Boolean registrationsOpen = false;

        for (int i = 0; i < sessionList.size(); i++) {
            Session sessionObj = sessionList.get(i);
            if (sessionObj.getRegistrationsOpen()) {
                registrationsOpen = true;
                break;
            }
        }

        if (!registrationsOpen) {
            toastService.redirectWithErrorToast(attributes, "Registrations closed.");
            return "redirect:/";
        }

        model.addAttribute("sessions", sessionList);
        model.addAttribute("application", new RegistrationApplication());
        return  "site/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute RegistrationApplication application, Model model, HttpSession session, RedirectAttributes attributes) {
        registrationApplicationService.createApplication(application);
        toastService.redirectWithSuccessToast(attributes, "Your application was successfully submitted.");
        return  "redirect:/";
    }
}
