package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee,Long> {

    @Query("select count(e) from Employee  e where e.departments.departmentId=?1 group by e.departments.departmentId")
    Integer countEmployeeByDepartment(Long departmentId);

    @Query("select e from Employee  e where e.employeeId=?1")
    Employee getEmployeeById(String employeeId);

    @Query("select e from Employee  e")
    List<Employee> findallEmployee();

    @Query("select e from Employee e where e.departments.departmentId=?1")
    List<Employee> findEmployeeByDepartment(Long departmentId);

    @Modifying
    @Query("delete  from Employee e where e.employeeId=?1")
    void deleteEmployeeById(String employeeId);

    @Modifying
    @Query("update Employee e set e.departments.departmentId=null where e.departments.departmentId=?1")
    void updateDepartmentOfEmployee(Long departmentId);

    @Modifying
    @Query("update Employee e set e.active=false where e.employeeId=?1")
    void inactivateEmployee(String employeeId);

    @Query("select e from Employee  e where e.email=?1")
    Employee getEmployeeByEmail(String email);

    @Query("select count(s) from Employee  s")
    Integer getEmployeeCount();


}
