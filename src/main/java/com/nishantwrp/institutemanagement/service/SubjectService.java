package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.CourseStructure;
import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.model.Subject;
import com.nishantwrp.institutemanagement.repository.FacultyRepository;
import com.nishantwrp.institutemanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjects;

    @Autowired
    private FacultyRepository faculties;

    private void getExtraProperties(Subject subject) {
        if (subject.getFacultyId() != null) {
            subject.setAssignedTo(faculties.getById(subject.getFacultyId()));
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjectList = subjects.getAll();
        for (int i = 0; i < subjectList.size(); i++) {
            getExtraProperties(subjectList.get(i));
        }
        return subjectList;
    }

    public void createSubject(Subject subject) {
        subjects.createSubject(subject);
    }

    public Subject getSubjectById(String id) {
        Subject subject = subjects.getById(Integer.parseInt(id));
        getExtraProperties(subject);
        return subject;
    }

    public void deleteSubject(Subject subject) {
        subjects.deleteSubject(subject.getId());
    }

    public void updateFaculty(Subject subject, Integer facultyId) {
        subjects.updateFaculty(subject.getId(), facultyId);
    }

    public List<Subject> getAllSubjectsNotPresentInCourseStructure(CourseStructure courseStructure) {
        List<Subject> allSubjects = subjects.getAll();
        List<Subject> included = subjects.getSubjectsInCourseStructure(courseStructure.getId());
        List<Subject> subjectList = new ArrayList<>();

        for (int i = 0; i < allSubjects.size(); i++) {
            Subject subject = allSubjects.get(i);

            Boolean flag = true;

            for (int j = 0; j < included.size(); j++) {
                if (included.get(j).getId() == subject.getId()) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                subjectList.add(subject);
            }

        }
        return subjectList;
    }

    public List<Subject> getAllSubjectsByFaculty(Faculty faculty) {
        return subjects.getAllByFaculty(faculty.getId());
    }
}
