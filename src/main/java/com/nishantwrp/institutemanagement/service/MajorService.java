package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.CourseStructure;
import com.nishantwrp.institutemanagement.model.Major;
import com.nishantwrp.institutemanagement.model.Semester;
import com.nishantwrp.institutemanagement.repository.CourseStructureRepository;
import com.nishantwrp.institutemanagement.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majors;

    @Autowired
    private CourseStructureRepository courseStructures;

    public List<Major> getAllMajors() {
        return majors.getAll();
    }

    public void createMajor(Major major) {
        majors.createMajor(major);
    }

    public void deleteMajor(Major major) {
        majors.deleteMajor(major.getId());
    }

    public Major getMajorById(String id) {
        return majors.getById(Integer.parseInt(id));
    }

    public List<Major> getAllMajorsWithCourseStructure(Semester semester) {
        List<Major> majorList = majors.getAll();
        for (int i = 0; i < majorList.size(); i++) {
            try {
                CourseStructure structure = courseStructures.getByMajorAndSemester(
                        majorList.get(i).getId(), semester.getId());
                majorList.get(i).setCourseStructure(structure);
            } catch (Exception e) {}
        }
        return majorList;
    }
}
