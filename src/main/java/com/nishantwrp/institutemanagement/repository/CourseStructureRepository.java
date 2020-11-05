package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.CourseStructure;
import com.nishantwrp.institutemanagement.model.CourseStructureSubject;
import com.nishantwrp.institutemanagement.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseStructureRepository {
    @Autowired
    private JdbcTemplate template;

    public CourseStructure getByMajorAndSemester(int majorId, int semesterId) {
        String sql = "SELECT * FROM course_structure WHERE majorId = ? AND semesterId = ?";
        return template.queryForObject(sql, new Object[] {majorId, semesterId}, new BeanPropertyRowMapper<>(CourseStructure.class));
    }

    public void create(CourseStructure courseStructure) {
        String sql = "INSERT INTO course_structure (majorId, semesterId) VALUES (?, ?)";
        template.update(sql, courseStructure.getMajorId(), courseStructure.getSemesterId());
    }

    public CourseStructure getById(int id) {
        String sql = "SELECT * FROM course_structure WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(CourseStructure.class));
    }

    public void delete(int id) {
        String sql = "DELETE FROM course_structure WHERE id = ?";
        template.update(sql, id);
    }

    public void addSubject(CourseStructureSubject courseStructureSubject) {
        String sql = "INSERT INTO subject_structure_relation (courseStructureId, subjectId, optional) VALUES (?, ?, ?)";
        template.update(sql, courseStructureSubject.getCourseStructureId(), courseStructureSubject.getSubjectId(), courseStructureSubject.getOptional());
    }
}
