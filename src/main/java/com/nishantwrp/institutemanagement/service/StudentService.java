package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Session;
import com.nishantwrp.institutemanagement.model.Student;
import com.nishantwrp.institutemanagement.repository.MajorRepository;
import com.nishantwrp.institutemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository students;

    @Autowired
    private MajorRepository majors;

    private void getExtraFields(Student student) {
        student.setMajor(majors.getById(student.getMajorId()));
    }

    public List<Student> getAllStudentsInSession(Session session) {
        List<Student> studentList = students.getAllInSession(session.getId());
        for (int i = 0; i < studentList.size(); i++) {
            getExtraFields(studentList.get(i));
        }
        return studentList;
    }
}
