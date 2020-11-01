package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationApplicationRepository {
    @Autowired
    private JdbcTemplate template;

    public void createApplication(RegistrationApplication application) {
        String sql = "INSERT INTO registration_application (name, phone, date, dob, about, address, email, interestedMajors, sessionId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(
                sql, application.getName(), application.getPhone(), application.getDate(),
                application.getDob(), application.getAbout(), application.getAddress(),
                application.getEmail(), application.getInterestedMajors(), application.getSessionId());
    }
}
