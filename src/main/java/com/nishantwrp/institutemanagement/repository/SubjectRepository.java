package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Subject> getAll() {
        String sql = "SELECT * FROM subject;";
        return template.query(sql, new BeanPropertyRowMapper<>(Subject.class));
    }

    public void createSubject(Subject subject) {
        String sql = "INSERT INTO subject (name, code) VALUES (?, ?)";
        template.update(sql, subject.getName(), subject.getCode());
    }

    public Subject getById(int id) {
        String sql = "SELECT * FROM subject WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Subject.class));
    }

    public void deleteSubject(int id) {
        String sql = "DELETE FROM subject WHERE id = ?";
        template.update(sql, id);
    }

    public void updateFaculty(int id, Integer facultyId) {
        String sql = "UPDATE subject SET facultyId=? WHERE id=?";
        template.update(sql, facultyId, id);
    }
}
