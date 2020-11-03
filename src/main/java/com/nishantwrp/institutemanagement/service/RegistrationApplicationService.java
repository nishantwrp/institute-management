package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.RegistrationApplication;
import com.nishantwrp.institutemanagement.model.Session;
import com.nishantwrp.institutemanagement.model.Student;
import com.nishantwrp.institutemanagement.repository.RegistrationApplicationRepository;
import com.nishantwrp.institutemanagement.repository.StudentRepository;
import com.nishantwrp.institutemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegistrationApplicationService {
    @Autowired
    private RegistrationApplicationRepository registrationApplications;

    @Autowired
    private StudentRepository students;

    @Autowired
    private UserRepository users;

    @Autowired
    private UtilsService utilsService;

    private void getExtraFields(RegistrationApplication application) {
        try {
            Student student = students.getByApplicationId(application.getId());
            application.setStudent(student);
        } catch (Exception e) {}
    }

    public void createApplication(RegistrationApplication registrationApplication) {
        registrationApplication.setDate(new Date());
        registrationApplications.createApplication(registrationApplication);
    }

    public List<RegistrationApplication> getApplicationsForSession(Session session) {
        List<RegistrationApplication> applications = registrationApplications.getAllForSession(session.getId());
        for (int i = 0; i < applications.size(); i++) {
            getExtraFields(applications.get(i));
        }
        return applications;
    }

    public RegistrationApplication getApplicationById(String id) {
        RegistrationApplication application = registrationApplications.getById(Integer.parseInt(id));
        getExtraFields(application);
        return application;
    }

    public void deleteApplication(RegistrationApplication application) {
        registrationApplications.delete(application.getId());
    }

    public void acceptApplication(RegistrationApplication application, Student student) {
        student.setName(application.getName());
        student.setPhone(application.getPhone());
        student.setDob(application.getDob());
        student.setAddress(application.getAddress());
        student.setEmail(application.getEmail());
        student.setApplicationId(application.getId());
        student.setSessionId(application.getSessionId());

        users.createUser(
                student.getRollNo(),
                utilsService.convertDateToString(student.getDob()));

        student.setUsername(student.getRollNo());
        students.createStudent(student);
    }
}
