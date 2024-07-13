package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminDAO extends JpaRepository<Admin,Long> {

    @Query("select a from Admin a where a.adminId=?1")
    Admin getAdminById(Long adminId);

    @Query("select a from Admin a where a.email=?1")
    Admin findByEmail(String email);
}
