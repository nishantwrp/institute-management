package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SessionRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Session> getAllSessions() {
        String sql = "SELECT * FROM session;";
        return  template.query(sql, new BeanPropertyRowMapper<>(Session.class));
    }
}
