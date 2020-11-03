package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Session;
import com.nishantwrp.institutemanagement.model.Student;
import com.nishantwrp.institutemanagement.repository.MajorRepository;
import com.nishantwrp.institutemanagement.repository.RegistrationApplicationRepository;
import com.nishantwrp.institutemanagement.repository.SessionRepository;
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

    @Autowired
    private SessionRepository sessions;

    @Autowired
    private RegistrationApplicationRepository registrationApplications;

    private void getExtraFields(Student student) {
        student.setMajor(majors.getById(student.getMajorId()));
        student.setSession(sessions.getById(student.getSessionId()));
    }

    public List<Student> getAllStudentsInSession(Session session) {
        List<Student> studentList = students.getAllInSession(session.getId());
        for (int i = 0; i < studentList.size(); i++) {
            getExtraFields(studentList.get(i));
        }
        return studentList;
    }

    public Student getStudentByRollNo(String rollNo) {
        Student student = students.getByRollNo(rollNo);
        getExtraFields(student);
        return student;
    }

    public void deleteStudent(Student student) {
        students.delete(student.getRollNo());
        registrationApplications.delete(student.getApplicationId());
    }
}
