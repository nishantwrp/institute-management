package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.*;
import com.nishantwrp.institutemanagement.repository.CourseStructureRepository;
import com.nishantwrp.institutemanagement.repository.MajorRepository;
import com.nishantwrp.institutemanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStructureService {
    @Autowired
    private CourseStructureRepository courseStructures;

    @Autowired
    private SubjectRepository subjects;

    @Autowired
    private MajorRepository majors;

    private void getExtraFields(CourseStructure courseStructure) {
        List<Subject> optionalSubjects = subjects.getSubjectsInCourseStructure(courseStructure.getId(), true);
        List<Subject> compulsorySubjects = subjects.getSubjectsInCourseStructure(courseStructure.getId(), false);
        courseStructure.setCompulsorySubjects(compulsorySubjects);
        courseStructure.setOptionalSubjects(optionalSubjects);

        Major major = majors.getById(courseStructure.getMajorId());
        courseStructure.setMajor(major);
    }

    public void createCourseStructure(Major major, Semester semester) {
        CourseStructure courseStructure = new CourseStructure();
        courseStructure.setMajorId(major.getId());
        courseStructure.setSemesterId(semester.getId());
        courseStructures.create(courseStructure);
    }

    public CourseStructure getStructureByMajorAndSemester(Major major, Semester semester) {
        return courseStructures.getByMajorAndSemester(major.getId(), semester.getId());
    }

    public CourseStructure getStructureById(String id) {
        CourseStructure courseStructure = courseStructures.getById(Integer.parseInt(id));
        getExtraFields(courseStructure);
        return courseStructure;
    }

    public void deleteStructure(CourseStructure structure) {
        courseStructures.delete(structure.getId());
    }

    public void addSubjectToCourseStructure(CourseStructureSubject courseStructureSubject) {
        courseStructures.addSubject(courseStructureSubject);
    }
}
