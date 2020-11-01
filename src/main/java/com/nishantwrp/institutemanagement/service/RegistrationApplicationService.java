package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.RegistrationApplication;
import com.nishantwrp.institutemanagement.repository.RegistrationApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegistrationApplicationService {
    @Autowired
    private RegistrationApplicationRepository registrationApplications;

    public void createApplication(RegistrationApplication registrationApplication) {
        registrationApplication.setDate(new Date());
        registrationApplications.createApplication(registrationApplication);
    }
}
