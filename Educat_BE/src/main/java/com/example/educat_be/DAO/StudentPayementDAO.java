package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Student_Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentPayementDAO extends JpaRepository<Student_Payment,Long> {

    @Query("select p from Student_Payment p where p.students.student_id=?1")
    List<Student_Payment> viewAllPaymentsByStudent(String studentId);

    @Query("SELECT CASE WHEN COALESCE(SUM(sp.amount), 0) < b.amount THEN true ELSE false END " +
            "FROM Bills b " +
            "LEFT JOIN Student_Payment sp ON b.Id = sp.bills.Id AND sp.students.student_id = :studentId " +
            "WHERE b.Id = :billId " +
            "GROUP BY b.Id, b.amount")
    Boolean willPay(Long billId,String studentId);

    @Query("select p from Student_Payment p where p.students.student_id=?2 and p.bills.Id=?1")
    List<Student_Payment> viewPaymentsForaBillByStudent(Long billId,String studentId);

    @Query("select sum(p.amount) from Student_Payment p where p.bills.Id=?1 and p.students.student_id=?2 group by p.bills.Id")
     Double getdueAmount(Long billId,String studentId);


}
