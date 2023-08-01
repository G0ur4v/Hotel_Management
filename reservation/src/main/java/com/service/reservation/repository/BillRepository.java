package com.service.reservation.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.reservation.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findByPaid(boolean b);

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.billDate >= :thirtyDaysAgo")
    long countBillsWithinThirtyDays(@Param("thirtyDaysAgo") Date thirtyDaysAgo);

    @Query("SELECT SUM(b.netAmount) FROM Bill b WHERE b.billDate >= :thirtyDaysAgo")
    double calculateTotalRevenue(@Param("thirtyDaysAgo") Date thirtyDaysAgo);

}
