package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MajorRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Major> getAll() {
        String sql = "SELECT * FROM major;";
        return template.query(sql, new BeanPropertyRowMapper<>(Major.class));
    }

    public void createMajor(Major major) {
        String sql = "INSERT INTO major (name) VALUES (?)";
        template.update(sql, major.getName());
    }

    public void deleteMajor(int id) {
        String sql = "DELETE FROM major WHERE id = ?";
        template.update(sql, id);
    }

    public Major getById(int id) {
        String sql = "SELECT * FROM major WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Major.class));
    }
}
