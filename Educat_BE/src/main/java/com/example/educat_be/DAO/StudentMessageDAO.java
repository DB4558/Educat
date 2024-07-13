package com.example.educat_be.DAO;

import com.example.educat_be.Entity.StudentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentMessageDAO extends JpaRepository<StudentMessage,Long> {

    @Query("select s from StudentMessage s where s.facultyId=?1")
    List<StudentMessage> getStudentmessageById(String employeeId);
}
