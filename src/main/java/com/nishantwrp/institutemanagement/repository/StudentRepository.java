package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate template;

    public Student getByApplicationId(int applicationId) {
        String sql = "SELECT * FROM student WHERE applicationId = ?";
        return template.queryForObject(sql, new Object[] {applicationId}, new BeanPropertyRowMapper<>(Student.class));
    }

    public void createStudent(Student student) {
        String sql = "INSERT INTO student (rollNo, name, phone, dob, address, email, majorId, sessionId, applicationId, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(
                sql, student.getRollNo(), student.getName(), student.getPhone(), student.getDob(),
                student.getAddress(), student.getEmail(), student.getMajorId(), student.getSessionId(),
                student.getApplicationId(), student.getUsername());
    }

    public List<Student> getAllInSession(int sessionId) {
        String sql = "SELECT * FROM student WHERE sessionId = ?";
        return template.query(sql, new Object[] {sessionId}, new BeanPropertyRowMapper<>(Student.class));
    }
}
