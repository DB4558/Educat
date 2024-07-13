package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeDAO extends JpaRepository<Grade,Long> {
}
