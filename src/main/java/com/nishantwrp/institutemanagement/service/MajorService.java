package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Major;
import com.nishantwrp.institutemanagement.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majors;

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
}
