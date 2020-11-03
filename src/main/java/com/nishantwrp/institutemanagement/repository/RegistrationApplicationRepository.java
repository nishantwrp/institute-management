package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<RegistrationApplication> getAllForSession(int sessionId) {
        String sql = "SELECT * FROM registration_application WHERE sessionId = ?";
        return template.query(sql, new Object[] {sessionId}, new BeanPropertyRowMapper<>(RegistrationApplication.class));
    }

    public RegistrationApplication getById(int id) {
        String sql = "SELECT * FROM registration_application WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(RegistrationApplication.class));
    }

    public void delete(int id) {
        String sql = "DELETE FROM registration_application WHERE id = ?";
        template.update(sql, id);
    }
}
