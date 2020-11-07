package com.nishantwrp.institutemanagement.repository;

import com.nishantwrp.institutemanagement.model.FeeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeeTransactionRepository {
    @Autowired
    private JdbcTemplate template;

    public List<FeeTransaction> getAllByStudentForSemester(String studentRollNo, int semesterId) {
        String sql = "SELECT * FROM fee_transaction WHERE studentRollNo = ? AND semesterId = ?";
        return template.query(sql, new Object[] {studentRollNo, semesterId}, new BeanPropertyRowMapper<>(FeeTransaction.class));
    }

    public void create(FeeTransaction feeTransaction) {
        String sql = "INSERT INTO fee_transaction (studentRollNo, semesterId, transactionId, amount, date) VALUES (?, ?, ?, ?, ?)";
        template.update(
                sql, feeTransaction.getStudentRollNo(), feeTransaction.getSemesterId(),
                feeTransaction.getTransactionId(), feeTransaction.getAmount(),
                feeTransaction.getDate());
    }

    public void delete(int id) {
        String sql = "DELETE FROM fee_transaction WHERE id = ?";
        template.update(sql, id);
    }
}
