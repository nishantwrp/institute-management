package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.FeeTransaction;
import com.nishantwrp.institutemanagement.model.Semester;
import com.nishantwrp.institutemanagement.model.Student;
import com.nishantwrp.institutemanagement.repository.FeeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeeTransactionService {
    @Autowired
    private FeeTransactionRepository feeTransactions;

    public List<FeeTransaction> getAllFeeTransactionsByStudentForSemester(Student student, Semester semester) {
        return feeTransactions.getAllByStudentForSemester(student.getRollNo(), semester.getId());
    }

    public void addFeeTransaction(Student student, Semester semester, FeeTransaction feeTransaction) {
        feeTransaction.setSemesterId(semester.getId());
        feeTransaction.setStudentRollNo(student.getRollNo());
        feeTransaction.setDate(new Date());
        feeTransactions.create(feeTransaction);
    }

    public void deleteFeeTransaction(String feeId) {
        feeTransactions.delete(Integer.parseInt(feeId));
    }
}
