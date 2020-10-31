package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessions;
}
