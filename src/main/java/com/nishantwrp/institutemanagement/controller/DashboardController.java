package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.model.Major;
import com.nishantwrp.institutemanagement.model.Payout;
import com.nishantwrp.institutemanagement.model.Subject;
import com.nishantwrp.institutemanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DashboardController extends BaseController {
    @Autowired
    private FacultyService facultyService;

    @Autowired
    private ToastService toastService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private PayoutService payoutService;

    // Needed to automatically convert String date in form to Date object.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);
        return "dashboard/index";
    }

    @GetMapping("/dashboard/manage/faculty")
    public String manageFaculty(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "dashboard/faculties";
    }

    @GetMapping("/dashboard/add/faculty")
    public String addFaculty(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("faculty", new Faculty());
        return "dashboard/addFaculty";
    }

    @PostMapping("/dashboard/add/faculty")
    public String postAddFaculty(@ModelAttribute Faculty faculty, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        try {
            facultyService.addFaculty(faculty);
            toastService.redirectWithSuccessToast(attributes, "Faculty added successfully.");
            return "redirect:/dashboard/manage/faculty";
        } catch (Exception e) {}

        toastService.displayErrorToast(model, "Faculty with same email exists already.");
        model.addAttribute("faculty", faculty);
        return "dashboard/addFaculty";
    }

    @GetMapping("/dashboard/manage/faculty/{facultyId}")
    public String manageFaculty(@PathVariable("facultyId") String facultyId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);
        model.addAttribute("faculty", faculty);
        model.addAttribute("payouts", payoutService.getAllPayoutsByFaculty(faculty));
        model.addAttribute("deleteUrl", "/dashboard/manage/faculty/" + facultyId + "/delete");
        return "dashboard/faculty";
    }

    @GetMapping("/dashboard/manage/faculty/{facultyId}/add/payout")
    public String addPayout(@PathVariable("facultyId") String facultyId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);
        model.addAttribute("faculty", faculty);
        model.addAttribute("payout", new Payout());
        return "dashboard/addPayout";
    }

    @PostMapping("/dashboard/manage/faculty/{facultyId}/add/payout")
    public String postAddPayout(@PathVariable("facultyId") String facultyId, @ModelAttribute Payout payout, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);
        payout.setDate(new Date());
        payoutService.addPayout(payout, faculty);
        toastService.redirectWithSuccessToast(attributes, "Payout added successfully.");
        return "redirect:/dashboard/manage/faculty/" + facultyId;
    }

    @GetMapping("/dashboard/manage/faculty/{facultyId}/payout/{payoutId}/delete")
    public String deletePayout(@PathVariable("facultyId") String facultyId, @PathVariable("payoutId") String payoutId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Payout payout = payoutService.getPayoutById(payoutId);
        payoutService.deletePayout(payout);
        toastService.redirectWithSuccessToast(attributes, "Payout deleted successfully.");
        return "redirect:/dashboard/manage/faculty/" + facultyId;
    }

    @GetMapping("/dashboard/manage/faculty/{facultyId}/delete")
    public String deleteFaculty(@PathVariable("facultyId") String facultyId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);

        try {
            facultyService.deleteFaculty(faculty);
            toastService.redirectWithSuccessToast(redirectAttributes, "Faculty deleted successfully.");
            return "redirect:/dashboard/manage/faculty";
        } catch (Exception e) {}

        toastService.redirectWithErrorToast(redirectAttributes, "Unassign all the subjects assigned to this faculty first.");
        return "redirect:/dashboard/manage/faculty/" + facultyId;
    }

    @GetMapping("/dashboard/manage/subjects")
    public String manageSubjects(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "dashboard/subjects";
    }

    @GetMapping("/dashboard/add/subject")
    public String addSubject(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("subject", new Subject());
        return "dashboard/addSubject";
    }

    @PostMapping("/dashboard/add/subject")
    public String postAddSubject(@ModelAttribute Subject subject, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        subjectService.createSubject(subject);
        toastService.redirectWithSuccessToast(attributes, "Subject added successfully.");
        return "redirect:/dashboard/manage/subjects";
    }

    @GetMapping("/dashboard/manage/subject/{subjectId}")
    public String manageSubject(@PathVariable("subjectId") String subjectId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("subject", subject);
        model.addAttribute("deleteUrl", "/dashboard/manage/subject/" + subjectId + "/delete");

        if (subject.getAssignedTo() == null) {
            model.addAttribute("faculties", facultyService.getAllFaculties());
            model.addAttribute("assignUrl", "/dashboard/manage/subject/" + subjectId + "/assign");
        } else {
            model.addAttribute("unassignUrl", "/dashboard/manage/subject/" + subjectId + "/unassign");
        }

        return "dashboard/subject";
    }

    @GetMapping("/dashboard/manage/subject/{subjectId}/delete")
    public String deleteSubject(@PathVariable("subjectId") String subjectId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Subject subject = subjectService.getSubjectById(subjectId);
        subjectService.deleteSubject(subject);
        toastService.redirectWithSuccessToast(redirectAttributes, "Subject deleted successfully.");
        return "redirect:/dashboard/manage/subjects";
    }

    @GetMapping("/dashboard/manage/subject/{subjectId}/unassign")
    public String unassignSubject(@PathVariable("subjectId") String subjectId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Subject subject = subjectService.getSubjectById(subjectId);
        subjectService.updateFaculty(subject, null);
        toastService.redirectWithSuccessToast(redirectAttributes, "Subject unassigned successfully.");
        return "redirect:/dashboard/manage/subject/" + subjectId;
    }

    @PostMapping("/dashboard/manage/subject/{subjectId}/assign")
    public String assignSubject(@PathVariable("subjectId") String subjectId, @ModelAttribute Subject subject, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        subjectService.updateFaculty(subject, subject.getFacultyId());
        toastService.redirectWithSuccessToast(redirectAttributes, "Subject assigned successfully.");
        return "redirect:/dashboard/manage/subject/" + subjectId;
    }

    @GetMapping("/dashboard/manage/majors")
    public String manageMajors(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("majors", majorService.getAllMajors());
        return "dashboard/majors";
    }

    @GetMapping("/dashboard/add/major")
    public String addMajor(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("major", new Major());
        return "dashboard/addMajor";
    }

    @PostMapping("/dashboard/add/major")
    public String postAddMajor(@ModelAttribute Major major, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        majorService.createMajor(major);
        toastService.redirectWithSuccessToast(attributes, "Major added successfully.");
        return "redirect:/dashboard/manage/majors";
    }

    @GetMapping("/dashboard/manage/major/{majorId}/delete")
    public String deleteMajor(@PathVariable("majorId") String majorId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Major major = majorService.getMajorById(majorId);
        majorService.deleteMajor(major);
        toastService.redirectWithSuccessToast(redirectAttributes, "Major deleted successfully.");
        return "redirect:/dashboard/manage/majors";
    }
}
