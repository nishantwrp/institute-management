package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResultRepository {
    @Autowired
    private JdbcTemplate template;

    public Result getByRegistration(int subjectRegistrationId) {
        String sql = "SELECT * FROM result WHERE subjectRegistrationId = ?";
        return template.queryForObject(sql, new Object[] {subjectRegistrationId}, new BeanPropertyRowMapper<>(Result.class));
    }

    public void create(Result result) {
        String sql = "INSERT INTO result (grade, subjectRegistrationId) VALUES (?, ?)";
        template.update(sql, result.getGrade(), result.getSubjectRegistrationId());
    }
}
