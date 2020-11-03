package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SemesterRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Semester> getAllInSession(int sessionId) {
        String sql = "SELECT * FROM semester WHERE sessionId = ?";
        return template.query(sql, new Object[] {sessionId}, new BeanPropertyRowMapper<>(Semester.class));
    }

    public void create(Semester semester) {
        String sql = "INSERT INTO semester (name, fee, sessionId) VALUES (?, ?, ?)";
        template.update(sql, semester.getName(), semester.getFee(), semester.getSessionId());
    }

    public Semester getById(int id) {
        String sql = "SELECT * FROM semester WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Semester.class));
    }

    public void delete(int id) {
        String sql = "DELETE FROM semester WHERE id = ?";
        template.update(sql, id);
    }
}
