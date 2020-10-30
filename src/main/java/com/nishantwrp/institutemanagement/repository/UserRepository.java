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

    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return template.queryForObject(sql, new Object[] {username}, new BeanPropertyRowMapper<>(User.class));
    }
}
