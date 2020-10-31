package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.User;
import com.nishantwrp.institutemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository users;

    public String getRole(String username) {
        User user = users.getUser(username);

        if (user.getIsAdmin()) {
            return "admin";
        }

        return "unknown";
    }
}
