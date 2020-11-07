package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate template;

    public void createUser(String username, String password) {
        String sql = "INSERT INTO user (username, password, isAdmin) VALUES (?, ?, 0)";
        template.update(sql, username, password);
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return template.queryForObject(sql, new Object[] {username}, new BeanPropertyRowMapper<>(User.class));
    }

    public void update(User user) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        template.update(sql, user.getPassword(), user.getUsername());
    }
}
