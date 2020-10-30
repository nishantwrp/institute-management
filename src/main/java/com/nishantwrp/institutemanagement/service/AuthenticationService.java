package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.User;
import com.nishantwrp.institutemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository users;
    private String SESSION_AUTH_KEY = "AUTH_USER";

    public Boolean checkCredentials(String username, String password) {
        User user = users.getUser(username);
        return user.getPassword().equals(password);
    }

    public void loginUser(HttpSession session, String username) {
        session.setAttribute(SESSION_AUTH_KEY, username);
    }

    public void logoutUser(HttpSession session) {
        session.removeAttribute(SESSION_AUTH_KEY);
    }

    public String getCurrentUser(HttpSession session) {
        try {
            return session.getAttribute(SESSION_AUTH_KEY).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isAuthenticated(HttpSession session) {
        return getCurrentUser(session) != null;
    }
}
