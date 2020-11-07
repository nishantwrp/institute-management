package com.nishantwrp.institutemanagement.controller;

import com.nishantwrp.institutemanagement.form.OptionalSubjectsForm;
import com.nishantwrp.institutemanagement.model.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RegistrationApplicationService registrationApplicationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseStructureService courseStructureService;

    @Autowired
    private SemesterRegistrationService semesterRegistrationService;

    @Autowired
    private FeeTransactionService feeTransactionService;

    @Autowired
    private SemesterRegistrationSubjectService semesterRegistrationSubjectService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

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

    @PostMapping("/dashboard/studentUpdate")
    public String studentUpdate(@ModelAttribute Student userProfile, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        userProfile.setRollNo(model.getAttribute("username").toString());
        studentService.updateStudent(userProfile);
        toastService.redirectWithSuccessToast(attributes, "Profile updated successfully.");
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/facultyUpdate")
    public String facultyUpdate(@ModelAttribute Faculty userProfile, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        Faculty faculty = facultyService.getFacultyByEmail(model.getAttribute("username").toString());
        userProfile.setId(faculty.getId());
        userProfile.setEmail(faculty.getEmail());
        facultyService.updateFaculty(userProfile);
        toastService.redirectWithSuccessToast(attributes, "Profile updated successfully.");
        return "redirect:/dashboard";
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

    @GetMapping("/dashboard/manage/sessions")
    public String manageSessions(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("sessions", sessionService.getAllSessions());
        return "dashboard/sessions";
    }

    @GetMapping("/dashboard/add/session")
    public String addSession(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("sessionObj", new Session());
        return "dashboard/addSession";
    }

    @PostMapping("/dashboard/add/session")
    public String postAddSession(@ModelAttribute Session sessionObj, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        sessionService.createSession(sessionObj);
        toastService.redirectWithSuccessToast(attributes, "Session added successfully.");
        return "redirect:/dashboard/manage/sessions";
    }

    @GetMapping("/dashboard/manage/session/{sessionId}")
    public String manageSession(@PathVariable("sessionId") String sessionId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        model.addAttribute("sessionObj", sessionObj);
        model.addAttribute("applications", registrationApplicationService.getApplicationsForSession(sessionObj));
        model.addAttribute("students", studentService.getAllStudentsInSession(sessionObj));
        model.addAttribute("semesters", semesterService.getAllSemestersInSession(sessionObj));
        return "dashboard/session";
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/application/{applicationId}")
    public String manageApplication(@PathVariable("sessionId") String sessionId, @PathVariable("applicationId") String applicationId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        RegistrationApplication registrationApplication = registrationApplicationService.getApplicationById(applicationId);
        model.addAttribute("sessionObj", sessionObj);
        model.addAttribute("applicationObj", registrationApplication);
        model.addAttribute("student", new Student());
        model.addAttribute("majors", majorService.getAllMajors());
        return "dashboard/application";
    }

    @PostMapping("/dashboard/manage/session/{sessionId}/application/{applicationId}/accept")
    public String acceptApplication(@ModelAttribute Student student, @PathVariable("sessionId") String sessionId, @PathVariable("applicationId") String applicationId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        RegistrationApplication registrationApplication = registrationApplicationService.getApplicationById(applicationId);
        try {
            registrationApplicationService.acceptApplication(registrationApplication, student);
            toastService.redirectWithSuccessToast(attributes, "Application accepted successfully.");
            return "redirect:/dashboard/manage/session/" + sessionId;
        } catch (Exception e) { }

        toastService.redirectWithErrorToast(attributes, "A student with the same roll no found.");
        return "redirect:/dashboard/manage/session/" + sessionId + "/application/" + applicationId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/application/{applicationId}/delete")
    public String deleteApplication(@PathVariable("sessionId") String sessionId, @PathVariable("applicationId") String applicationId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        RegistrationApplication registrationApplication = registrationApplicationService.getApplicationById(applicationId);
        registrationApplicationService.deleteApplication(registrationApplication);
        toastService.redirectWithSuccessToast(attributes, "Application deleted successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/toggleCompletion")
    public String toggleCompleteSession(@PathVariable("sessionId") String sessionId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        sessionService.toggleSessionCompletionStatus(sessionObj);
        toastService.redirectWithSuccessToast(attributes, "Session updated successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/toggleRegistrations")
    public String toggleRegistrationSession(@PathVariable("sessionId") String sessionId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        sessionService.toggleSessionRegistrationsStatus(sessionObj);
        toastService.redirectWithSuccessToast(attributes, "Session updated successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/delete")
    public String deleteSession(@PathVariable("sessionId") String sessionId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        sessionService.deleteSession(sessionObj);
        toastService.redirectWithSuccessToast(attributes, "Session deleted successfully.");
        return "redirect:/dashboard/manage/sessions";
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/add/semester")
    public String addSemester(@PathVariable("sessionId") String sessionId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        model.addAttribute("sessionObj", sessionObj);
        model.addAttribute("semester", new Semester());
        return "dashboard/addSemester";
    }

    @PostMapping("/dashboard/manage/session/{sessionId}/add/semester")
    public String postAddSemester(@ModelAttribute Semester semester, @PathVariable("sessionId") String sessionId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        semesterService.createSemester(sessionObj, semester);
        toastService.redirectWithSuccessToast(attributes, "Semester added successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}")
    public String manageSemester(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        Semester semester = semesterService.getSemesterById(semesterId);
        List<Major> majors = majorService.getAllMajorsWithCourseStructure(semester);
        model.addAttribute("sessionObj", sessionObj);
        model.addAttribute("semester", semester);
        model.addAttribute("majors", majors);
        return "dashboard/semester";
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/add/structure/{majorId}")
    public String addStructure(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, @PathVariable("majorId") String majorId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Major major = majorService.getMajorById(majorId);
        Semester semester = semesterService.getSemesterById(semesterId);
        courseStructureService.createCourseStructure(major, semester);
        CourseStructure structure = courseStructureService.getStructureByMajorAndSemester(major, semester);
        toastService.redirectWithSuccessToast(attributes, "Course structure created successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId + "/semester/" + semesterId + "/structure/" + structure.getId();
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/structure/{structureId}")
    public String manageStructure(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, @PathVariable("structureId") String structureId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        CourseStructure structure = courseStructureService.getStructureById(structureId);
        Semester semester = semesterService.getSemesterById(semesterId);
        model.addAttribute("semester", semester);
        model.addAttribute("structure", structure);
        model.addAttribute("sessionObj", sessionObj);
        return "dashboard/courseStructure";
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/structure/{structureId}/delete")
    public String deleteStructure(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, @PathVariable("structureId") String structureId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        CourseStructure structure = courseStructureService.getStructureById(structureId);
        courseStructureService.deleteStructure(structure);
        toastService.redirectWithSuccessToast(attributes, "Course structure deleted successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId + "/semester/" + semesterId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/structure/{structureId}/add/subject")
    public String addSubjectToStructure(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, @PathVariable("structureId") String structureId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Session sessionObj = sessionService.getSessionById(sessionId);
        CourseStructure structure = courseStructureService.getStructureById(structureId);
        Semester semester = semesterService.getSemesterById(semesterId);
        CourseStructureSubject courseStructureSubject = new CourseStructureSubject();
        courseStructureSubject.setCourseStructureId(structure.getId());

        List<Subject> subjectList = subjectService.getAllSubjectsNotPresentInCourseStructure(structure);

        if (subjectList.size() == 0) {
            toastService.redirectWithErrorToast(attributes, "All subjects already present in this course structure.");
            return "redirect:/dashboard/manage/session/" + sessionId + "/semester/" + semesterId + "/structure/" + structureId;
        }

        model.addAttribute("sessionObj", sessionObj);
        model.addAttribute("semester", semester);
        model.addAttribute("structure", structure);
        model.addAttribute("courseStructureSubject", courseStructureSubject);
        model.addAttribute("subjects", subjectList);
        return "dashboard/addSubjectToStructure";
    }

    @PostMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/structure/{structureId}/add/subject")
    public String postAddSubjectToStructure(@ModelAttribute CourseStructureSubject courseStructureSubject, @PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, @PathVariable("structureId") String structureId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        courseStructureService.addSubjectToCourseStructure(courseStructureSubject);
        toastService.redirectWithSuccessToast(attributes, "Subject added to this course structure successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId + "/semester/" + semesterId + "/structure/" + structureId;
    }

    @GetMapping("/dashboard/manage/session/{sessionId}/semester/{semesterId}/delete")
    public String deleteSemester(@PathVariable("sessionId") String sessionId, @PathVariable("semesterId") String semesterId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Semester semester = semesterService.getSemesterById(semesterId);
        semesterService.deleteSemester(semester);
        toastService.redirectWithSuccessToast(attributes, "Semester deleted successfully.");
        return "redirect:/dashboard/manage/session/" + sessionId;
    }

    @GetMapping("/dashboard/manage/student/{rollNo}")
    public String manageStudent(@PathVariable("rollNo") String rollNo, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Student student = studentService.getStudentByRollNo(rollNo);
        List<SemesterRegistration> semesterRegistrations = semesterRegistrationService.getAllSemesterRegistrationsByStudent(student);

        model.addAttribute("student", student);
        model.addAttribute("semesterRegistrations", semesterRegistrations);
        return "dashboard/student";
    }

    @GetMapping("/dashboard/manage/student/{rollNo}/registration/{registrationId}")
    public String manageStudent(@PathVariable("rollNo") String rollNo, @PathVariable("registrationId") String registrationId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Student student = studentService.getStudentByRollNo(rollNo);
        SemesterRegistration semesterRegistration = semesterRegistrationService.getSemesterRegistrationById(registrationId);
        Semester semester = semesterService.getSemesterById(semesterRegistration.getSemesterId());
        List<FeeTransaction> feeTransactions = feeTransactionService.getAllFeeTransactionsByStudentForSemester(student, semester);

        model.addAttribute("student", student);
        model.addAttribute("semester", semester);
        model.addAttribute("semesterRegistration", semesterRegistration);
        model.addAttribute("feeTransactions", feeTransactions);
        return "dashboard/semesterRegistration";
    }

    @GetMapping("/dashboard/manage/student/{rollNo}/delete")
    public String deleteStudent(@PathVariable("rollNo") String rollNo, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("admin")) {
            return "redirect:/";
        }

        Student student = studentService.getStudentByRollNo(rollNo);
        studentService.deleteStudent(student);
        toastService.redirectWithSuccessToast(attributes, "Student deleted successfully.");
        return "redirect:/dashboard/manage/session/" + student.getSessionId();
    }

    @GetMapping("/dashboard/student/semesters")
    public String studentSemesters(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        List<Semester> semesters = semesterService.getAllSemestersForStudent(student);
        model.addAttribute("semesters", semesters);
        return "dashboard/studentSemesters";
    }

    @GetMapping("/dashboard/student/semester/{semesterId}/register")
    public String studentRegisterSemester(@PathVariable("semesterId") String semesterId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        Semester semester = semesterService.getSemesterById(semesterId);
        CourseStructure courseStructure = courseStructureService.getStructureByMajorAndSemester(student.getMajor(), semester);
        model.addAttribute("courseStructure", courseStructure);

        OptionalSubjectsForm optionalSubjectsForm = new OptionalSubjectsForm().buildForm(courseStructure.getOptionalSubjects());
        model.addAttribute("optionalSubjectsForm", optionalSubjectsForm);
        return "dashboard/studentSemesterRegister";
    }

    @PostMapping("/dashboard/student/semester/{semesterId}/register")
    public String postStudentRegisterSemester(@ModelAttribute OptionalSubjectsForm optionalSubjectsForm, @PathVariable("semesterId") String semesterId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        Semester semester = semesterService.getSemesterById(semesterId);
        CourseStructure courseStructure = courseStructureService.getStructureByMajorAndSemester(student.getMajor(), semester);

        List<Subject> selectedOptionalSubjects = optionalSubjectsForm.getSelectedSubjects();
        List<Subject> compulsorySubjects = courseStructure.getCompulsorySubjects();

        List<Integer> subjectIds = new ArrayList<>();

        for (int i = 0; i < compulsorySubjects.size(); i++) {
            subjectIds.add(compulsorySubjects.get(i).getId());
        }

        for (int i = 0; i < selectedOptionalSubjects.size(); i++) {
            subjectIds.add(selectedOptionalSubjects.get(i).getId());
        }

        semesterRegistrationService.registerForSemester(student, semester, subjectIds);
        toastService.redirectWithSuccessToast(attributes, "Registered for semester successfully.");
        return "redirect:/dashboard/student/semester/" + semesterId;
    }

    @GetMapping("/dashboard/student/semester/{semesterId}")
    public String getSemester(@PathVariable("semesterId") String semesterId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        Semester semester = semesterService.getSemesterById(semesterId);
        SemesterRegistration semesterRegistration = semesterRegistrationService.getSemesterRegistrationByStudentAndSemester(student, semester);
        List<FeeTransaction> feeTransactions = feeTransactionService.getAllFeeTransactionsByStudentForSemester(student, semester);

        model.addAttribute("semester", semester);
        model.addAttribute("semesterRegistration", semesterRegistration);
        model.addAttribute("feeTransactions", feeTransactions);
        return "dashboard/studentSemester";
    }

    @GetMapping("/dashboard/student/semester/{semesterId}/add/fee")
    public String addFee(@PathVariable("semesterId") String semesterId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        Semester semester = semesterService.getSemesterById(semesterId);

        model.addAttribute("semester", semester);
        model.addAttribute("feeTransaction", new FeeTransaction());
        return "dashboard/addFee";
    }

    @PostMapping("/dashboard/student/semester/{semesterId}/add/fee")
    public String postAddFee(@ModelAttribute FeeTransaction feeTransaction, @PathVariable("semesterId") String semesterId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        Semester semester = semesterService.getSemesterById(semesterId);
        feeTransactionService.addFeeTransaction(student, semester, feeTransaction);
        toastService.redirectWithSuccessToast(attributes, "Fee transaction added successfully.");
        return "redirect:/dashboard/student/semester/" + semesterId;
    }

    @GetMapping("/dashboard/student/semester/{semesterId}/fee/{feeId}/delete")
    public String deleteFee(@PathVariable("semesterId") String semesterId, @PathVariable("feeId") String feeId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        feeTransactionService.deleteFeeTransaction(feeId);
        toastService.redirectWithSuccessToast(attributes, "Fee transaction deleted successfully.");
        return "redirect:/dashboard/student/semester/" + semesterId;
    }

    @GetMapping("/dashboard/student/subject/{subjectRegistrationId}")
    public String studentSubjectRegistration(@PathVariable("subjectRegistrationId") String subjectRegistrationId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("student")) {
            return "redirect:/";
        }

        String rollNo = model.getAttribute("username").toString();
        Student student = studentService.getStudentByRollNo(rollNo);
        SemesterRegistrationSubject semesterRegistrationSubject = semesterRegistrationSubjectService.getById(subjectRegistrationId);

        try {
            Faculty faculty = facultyService.getFacultyById(semesterRegistrationSubject.getSubject().getFacultyId());
            model.addAttribute("faculty", faculty);
        } catch (Exception e) {
            model.addAttribute("faculty", null);
        }

        model.addAttribute("subjectRegistration", semesterRegistrationSubject);
        return "dashboard/studentSubjectRegistration";
    }

    @GetMapping("/dashboard/faculty/subjects")
    public String facultySubjects(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        String userEmail = model.getAttribute("username").toString();
        Faculty faculty = facultyService.getFacultyByEmail(userEmail);
        List<Subject> subjects = subjectService.getAllSubjectsByFaculty(faculty);

        model.addAttribute("subjects", subjects);
        return "dashboard/facultySubjects";
    }

    @GetMapping("/dashboard/faculty/subject/{subjectId}")
    public String facultySubject(@PathVariable("subjectId") String subjectId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        String userEmail = model.getAttribute("username").toString();
        Faculty faculty = facultyService.getFacultyByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(subjectId);
        List<SemesterRegistrationSubject> semesterRegistrationSubjectList = semesterRegistrationSubjectService.getAllBySubject(subject);

        model.addAttribute("subject", subject);
        model.addAttribute("registrations", semesterRegistrationSubjectList);
        return "dashboard/facultySubject";
    }

    @GetMapping("/dashboard/faculty/subject/{subjectId}/registration/{registrationId}")
    public String facultySubjectRegistration(@PathVariable("subjectId") String subjectId, @PathVariable("registrationId") String registrationId, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        String userEmail = model.getAttribute("username").toString();
        Faculty faculty = facultyService.getFacultyByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(subjectId);
        SemesterRegistrationSubject semesterRegistrationSubject = semesterRegistrationSubjectService.getById(registrationId);

        model.addAttribute("subject", subject);
        model.addAttribute("registration", semesterRegistrationSubject);

        if (semesterRegistrationSubject.getResult() == null) {
            model.addAttribute("result", new Result());
        }

        return "dashboard/facultySubjectRegistration";
    }

    @PostMapping("/dashboard/faculty/subject/{subjectId}/registration/{registrationId}/grade")
    public String facultyGradeSubjectRegistration(@ModelAttribute Result result, @PathVariable("subjectId") String subjectId, @PathVariable("registrationId") String registrationId, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        resultService.createResult(result, registrationId);
        toastService.redirectWithSuccessToast(attributes, "Graded this student successfully.");
        return "redirect:/dashboard/faculty/subject/" + subjectId + "/registration/" + registrationId;
    }

    @GetMapping("/dashboard/faculty/payouts")
    public String facultyPayouts(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);

        String userRole = model.getAttribute("userRole").toString();
        if (!userRole.equals("faculty")) {
            return "redirect:/";
        }

        String userEmail = model.getAttribute("username").toString();
        Faculty faculty = facultyService.getFacultyByEmail(userEmail);
        List<Payout> payouts = payoutService.getAllPayoutsByFaculty(faculty);

        model.addAttribute("payouts", payouts);
        return "dashboard/facultyPayouts";
    }

    @GetMapping("/dashboard/changePassword")
    public String changePassword(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);
        model.addAttribute("userObj", new User());
        return "dashboard/changePassword";
    }

    @PostMapping("/dashboard/changePassword")
    public String postChangePassword(@ModelAttribute User userObj, Model model, HttpSession session, RedirectAttributes attributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, session);
        userService.changePassword(model.getAttribute("username").toString(), userObj);
        toastService.redirectWithSuccessToast(attributes, "Password changed successfully.");
        return "redirect:/dashboard/changePassword";
    }
}
