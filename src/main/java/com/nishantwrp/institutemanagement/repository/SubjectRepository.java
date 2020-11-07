package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.CourseStructureSubject;
import com.nishantwrp.institutemanagement.model.SemesterRegistrationSubject;
import com.nishantwrp.institutemanagement.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<Subject> getAllByFaculty(Integer facultyId) {
        String sql = "SELECT * FROM subject WHERE facultyId = ?";
        return template.query(sql, new Object[] {facultyId}, new BeanPropertyRowMapper<>(Subject.class));
    }

    public List<Subject> getSubjectsInCourseStructure(int courseStructureId, Boolean optional) {
        String sql = "SELECT * FROM subject_structure_relation WHERE courseStructureId = ? AND optional = ?";
        List<CourseStructureSubject> courseStructureSubjectList = template.query(sql, new Object[] {courseStructureId, optional}, new BeanPropertyRowMapper<>(CourseStructureSubject.class));
        List<Subject> subjectList = new ArrayList<>();
        for (int i = 0; i < courseStructureSubjectList.size(); i++) {
            subjectList.add(getById(courseStructureSubjectList.get(i).getSubjectId()));
        }
        return subjectList;
    }

    public List<Subject> getSubjectsInCourseStructure(int courseStructureId) {
        String sql = "SELECT * FROM subject_structure_relation WHERE courseStructureId = ?";
        List<CourseStructureSubject> courseStructureSubjectList = template.query(sql, new Object[] {courseStructureId}, new BeanPropertyRowMapper<>(CourseStructureSubject.class));
        List<Subject> subjectList = new ArrayList<>();
        for (int i = 0; i < courseStructureSubjectList.size(); i++) {
            subjectList.add(getById(courseStructureSubjectList.get(i).getSubjectId()));
        }
        return subjectList;
    }
}
