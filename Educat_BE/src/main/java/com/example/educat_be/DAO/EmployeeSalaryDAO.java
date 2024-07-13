package com.example.educat_be.DAO;

import com.example.educat_be.Entity.EmployeeSalary;
import com.example.educat_be.Entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.List;

public interface EmployeeSalaryDAO extends JpaRepository<EmployeeSalary,Long> {

    @Query("select s.salary from EmployeeSalary s where s.employee.email=?1")
    List<Salary> findSalaryHistoryByEmail(String email);

    @Query("select s.salary from EmployeeSalary s where s.employee.employeeId=?1 and MONTH(s.salary.paymentDate)=?2")
    List<Salary> findSalaryHistoryByIdandMonth(String employeeId, Integer month);
}
