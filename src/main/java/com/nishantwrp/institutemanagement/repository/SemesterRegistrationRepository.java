package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.SemesterRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SemesterRegistrationRepository {
    @Autowired
    private JdbcTemplate template;

    public SemesterRegistration getByStudentAndSemester(String studentRollNo, int semesterId) {
        String sql = "SELECT * FROM semester_registration WHERE studentRollNo = ? AND semesterId = ?";
        return template.queryForObject(sql, new Object[] {studentRollNo, semesterId}, new BeanPropertyRowMapper<>(SemesterRegistration.class));
    }

    public SemesterRegistration getById(int id) {
        String sql = "SELECT * FROM semester_registration WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(SemesterRegistration.class));
    }

    public void register(String studentRollNo, int semesterId, List<Integer> subjectIds) {
        String sql = "INSERT INTO semester_registration (studentRollNo, semesterId) VALUES (?, ?)";
        template.update(sql, studentRollNo, semesterId);

        SemesterRegistration semesterRegistration = getByStudentAndSemester(studentRollNo, semesterId);

        for (int i = 0; i < subjectIds.size(); i++) {
            sql = "INSERT INTO registration_subject_relation (registrationId, subjectId) VALUES (?, ?)";
            template.update(sql, semesterRegistration.getId(), subjectIds.get(i));
        }
    }

    public List<SemesterRegistration> getAllByStudent(String studentRollNo) {
        String sql = "SELECT * FROM semester_registration WHERE studentRollNo = ?";
        return template.query(sql, new Object[] {studentRollNo}, new BeanPropertyRowMapper<>(SemesterRegistration.class));
    }
}
