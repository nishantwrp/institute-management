package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Result;
import com.nishantwrp.institutemanagement.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    private ResultRepository results;

    public void createResult(Result result, String registrationId) {
        result.setSubjectRegistrationId(Integer.parseInt(registrationId));
        results.create(result);
    }
}
