package com.example.educat_be.DAO;

import com.example.educat_be.Entity.CourseSchedule;
import com.example.educat_be.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDAO extends JpaRepository<Courses,Long> {

    @Query("select c from Courses c where c.courseId=?1")
    Courses findByCourseId(Long courseId);

    @Modifying
    @Query("delete from Courses c where c.courseId=?1")
    void deleteCourseById(Long courseId);

    @Query("select c.courseCode,c.name,c.term,c.year,c.credits,c.capacity,c.employee.employeeId,c.employee.fname,c.employee.lname,c.employee.title,c.employee.email from Courses c where c.employee is not null")
    List<Object[]> viewFacultyByCourse();

    @Modifying
    @Query("update Courses c set c.employee.employeeId=null where c.employee.employeeId=?1")
    void updateFacultyOfCourse(String employeeId);


    @Query("select count(cs) from CourseSchedule cs where cs.courses.courseId=?1 and EXISTS(select 1 from CourseSchedule cs1 where cs1.courses.courseId!=?1 and cs1.courses.employee.employeeId=?2 and cs.day=cs1.day and cs.time=cs1.time)")
    Integer findCourseByIdandTime(Long courseId, String employeeId);

    @Query("select c from Courses c where c.employee is null")
    List<Courses>findCoursewithNoFaculty();

    @Query("select c from Courses c where c.employee.employeeId=?1")
    List<Courses> viewCourseByFaculty(String employeeId);

    @Query("select count(s) from Courses  s")
    Integer getCourseCount();

}
