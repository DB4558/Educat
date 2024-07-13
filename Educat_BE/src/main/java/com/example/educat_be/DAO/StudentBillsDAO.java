package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Bills;
import com.example.educat_be.Entity.Student_Bills;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentBillsDAO extends JpaRepository<Student_Bills,Long> {

    @Query("SELECT b FROM Bills b " +
            "JOIN Student_Bills sb ON b.Id = sb.bills.Id " +
            "LEFT JOIN Student_Payment sp ON sb.bills.Id = sp.bills.Id AND sb.students.student_id = sp.students.student_id " +
            "WHERE sb.students.student_id = :studentId " +
            "GROUP BY b.Id " +
            "HAVING b.amount > COALESCE(SUM(sp.amount), 0)")
    List<Bills> viewDueBills(String studentId);

    @Query("SELECT b FROM Bills b " +
            "JOIN Student_Bills sb ON b.Id = sb.bills.Id " +
            "JOIN Student_Payment sp ON sb.bills.Id = sp.bills.Id AND sb.students.student_id = sp.students.student_id " +
            "WHERE sb.students.student_id = :studentId " +
            "GROUP BY b.Id " +
            "HAVING b.amount=SUM(sp.amount)")
    List<Bills> viewPaidBills(String studentId);


    @Query("SELECT sb.students FROM Bills b " +
            "JOIN Student_Bills sb ON b.Id = sb.bills.Id " +
            "LEFT JOIN Student_Payment sp ON sb.bills.Id = sp.bills.Id AND sb.students.student_id = sp.students.student_id " +
            "GROUP BY b.Id, sb.students.student_id " +
            "HAVING b.amount > COALESCE(SUM(sp.amount), 0)")
    List<Students> viewStudentswhoseBillsAreDue();


}
