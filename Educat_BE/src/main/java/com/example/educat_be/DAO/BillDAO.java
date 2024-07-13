package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillDAO extends JpaRepository<Bills,Long> {

    @Query("select b from Bills b where b.Id=?1")
    Bills findBillById(Long billId);

    @Query("select b.amount from Bills b where b.Id=?1")
    Double viewAmountForBill(Long billId);
}
