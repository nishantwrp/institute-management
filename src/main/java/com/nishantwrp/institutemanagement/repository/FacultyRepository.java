package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacultyRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Faculty> getAll() {
        String sql = "SELECT * FROM faculty;";
        return template.query(sql, new BeanPropertyRowMapper<>(Faculty.class));
    }

    public void createFaculty(Faculty faculty, String username) {
        String sql = "INSERT INTO faculty (name, email, bio, address, phone, dob, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(
                sql, faculty.getName(), faculty.getEmail(), faculty.getBio(),
                faculty.getAddress(), faculty.getPhone(), faculty.getDob(), username);
    }

    public Faculty getById(int id) {
        String sql = "SELECT * FROM faculty WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Faculty.class));
    }

    public void deleteFaculty(int id) {
        String sql = "DELETE FROM faculty WHERE id = ?";
        template.update(sql, id);
    }
}
