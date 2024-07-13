package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalaryDAO extends JpaRepository<Salary,Long> {

  @Query("select s from Salary s where s.SalaryId=?1")
  Salary findSalaryById(Long salaryId);
}
