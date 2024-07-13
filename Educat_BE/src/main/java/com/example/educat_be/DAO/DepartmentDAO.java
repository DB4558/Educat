package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentDAO extends JpaRepository<Departments,Long> {

    @Query("select d from Departments  d where d.departmentId=?1")
    Departments findByDepartmentId(Long departmentId);

    @Modifying
    @Query("delete from Departments d where d.departmentId=?1")
    void deleteByDepartmentId(Long departmentId);


}
