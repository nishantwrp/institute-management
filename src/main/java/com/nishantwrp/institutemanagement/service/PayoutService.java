package com.nishantwrp.institutemanagement.service;

import com.nishantwrp.institutemanagement.model.Faculty;
import com.nishantwrp.institutemanagement.model.Payout;
import com.nishantwrp.institutemanagement.repository.PayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayoutService {
    @Autowired
    private PayoutRepository payouts;

    public List<Payout> getAllPayoutsByFaculty(Faculty faculty) {
        return payouts.getAllByFaculty(faculty.getId());
    }

    public void addPayout(Payout payout, Faculty faculty) {
        payout.setFacultyId(faculty.getId());
        payouts.addPayout(payout);
    }

    public Payout getPayoutById(String id) {
        return payouts.getById(Integer.parseInt(id));
    }

    public void deletePayout(Payout payout) {
        payouts.deletePayout(payout.getId());
    }
}
