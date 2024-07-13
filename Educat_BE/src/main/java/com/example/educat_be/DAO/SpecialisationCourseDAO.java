package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Courses;
import com.example.educat_be.Entity.Specialisation;
import com.example.educat_be.Entity.Specialisation_Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialisationCourseDAO extends JpaRepository<Specialisation_Course,Long> {

    @Query("select s.courses from Specialisation_Course s where s.specialisation.specialisationId=?1")
   List<Courses> findCoursesBySpecialisationId(Long specialisationId);

    @Query("select s from Specialisation_Course  s where s.specialisation.specialisationId=?1")
    List<Specialisation_Course> findBySpecialisationId(Long specialisationId);

    @Query("select s from Specialisation_Course s where s.courses.courseId=?1")
    List<Specialisation_Course> findByCourseId(Long courseId);

    @Query("select s.specialisation from Specialisation_Course  s where s.courses.courseId=?1")
    Specialisation findSpecialisationByCourse(Long courseId);

}
