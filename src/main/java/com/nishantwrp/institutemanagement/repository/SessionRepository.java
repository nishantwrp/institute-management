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

    public void create(Session session) {
        String sql = "INSERT INTO session (startYear, isComplete, registrationsOpen) VALUES (?, ?, ?)";
        template.update(sql, session.getStartYear(), session.getIsComplete(), session.getRegistrationsOpen());
    }

    public Session getById(int id) {
        String sql = "SELECT * FROM session WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Session.class));
    }

    public void update(Session session) {
        String sql = "UPDATE session SET startYear=?, isComplete=?, registrationsOpen=? WHERE id = ?";
        template.update(
                sql, session.getStartYear(), session.getIsComplete(),
                session.getRegistrationsOpen(), session.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM session WHERE id = ?";
        template.update(sql, id);
    }
}
