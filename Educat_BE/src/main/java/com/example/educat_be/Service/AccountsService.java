package com.example.educat_be.Service;

import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;

import java.time.Month;
import java.util.List;
import java.util.Set;

public interface AccountsService {

    Salary addSalary(Salary salary);
    Salary editSalary(Long salaryId,Salary salary);

    List<Salary> viewAllSalary();

    List<Departments> viewAllDepartments();

    List<Employee> viewAllEmployeeByDepartment(Long departmentId);

    void disburseSalaryByDepartment(Long departmentId,Long salaryId,String employeeId);

    void disburseSalaryByEmployees(Set<Long> Ids,Long salaryId,String currentEmployeeId);

    void disburseSalaryByEmployeeId(String employeeId,Long salaryId,String currentEmployeeId);

    List<Salary> viewSalaryHistoryByEmployee(String employeeId);

    List<Salary> viewSalaryHistoryByEmployeeForMonth(String employeeId, Integer month);

    Bills addBill(Bills bills);

    Bills editBill(Long billId,Bills bills);

    List<Bills> viewAllBill();

    List<Domain> viewAllDomain();

    List<Students> viewStudentsByDomain(Long domainId);

    void disburseBillsByDomain(Long domainId,Long billId);

    void disburseBillsByStudents(Set<Long> studentId,Long billId);

    void disburseBillsByStudentId(String studentId,Long billId);

    LoginResponse accountLogin(LoginDTO loginDTO);



}
