package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.SemesterRegistrationSubject;
import com.nishantwrp.institutemanagement.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SemesterRegistrationSubjectRepository {
    @Autowired
    private JdbcTemplate template;

    public List<SemesterRegistrationSubject> getAllForSubject(int subjectId) {
        String sql = "SELECT * FROM registration_subject_relation WHERE subjectId = ?";
        return template.query(sql, new Object[] {subjectId}, new BeanPropertyRowMapper<>(SemesterRegistrationSubject.class));
    }

    public SemesterRegistrationSubject getById(int id) {
        String sql = "SELECT * FROM registration_subject_relation WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(SemesterRegistrationSubject.class));
    }

    public List<SemesterRegistrationSubject> getSubjectsInSemesterRegistration(int registrationId) {
        String sql = "SELECT * FROM registration_subject_relation WHERE registrationId = ?";
        return template.query(sql, new Object[] {registrationId}, new BeanPropertyRowMapper<>(SemesterRegistrationSubject.class));
    }
}
