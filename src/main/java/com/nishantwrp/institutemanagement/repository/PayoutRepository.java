package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.Payout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayoutRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Payout> getAllByFaculty(int facultyId) {
        String sql = "SELECT * FROM payout WHERE facultyId = ?";
        return template.query(sql, new Object[] {facultyId}, new BeanPropertyRowMapper<>(Payout.class));
    }

    public void addPayout(Payout payout) {
        String sql = "INSERT INTO payout (notes, amount, date, transactionId, facultyId) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, payout.getNotes(), payout.getAmount(), payout.getDate(), payout.getTransactionId(), payout.getFacultyId());
    }

    public Payout getById(int id) {
        String sql = "SELECT * FROM payout WHERE id = ?";
        return template.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Payout.class));
    }

    public void deletePayout(int id) {
        String sql = "DELETE FROM payout WHERE id = ?";
        template.update(sql, id);
    }
}
