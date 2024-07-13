package com.example.educat_be.DAO;

import com.example.educat_be.Entity.CourseTA;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseTADAO extends JpaRepository<CourseTA,Long> {

    @Query("select s.students from CourseTA  s where s.courses.courseId=?1")
    List<Students> viewTaByCourse(Long courseId);

    @Query("select c from CourseTA  c where c.courses.courseId=?1")
    List<CourseTA> getTAByCourse(Long courseId);
}
