package com.nishantwrp.institutemanagement.controller;

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
        model.addAttribute("student", student);
        return "dashboard/student";
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
}
