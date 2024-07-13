package com.example.educat_be.DAO;

import com.example.educat_be.Entity.CourseSchedule;
import com.example.educat_be.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseScheduleDAO extends JpaRepository<CourseSchedule,Long> {

    @Query("select s from CourseSchedule s where s.courses.courseId=?1")
    List<CourseSchedule> findScheduleByCourse(Long courseId);

    @Modifying
    @Query("delete from CourseSchedule s where s.Id=?1")
    void deleteByScheduleId(Long Id);

    @Query("select t from CourseSchedule  t where t.courses.courseId IN (select d.courses.courseId from CourseDomain  d where d.domain.domainId=?1)")
     List<CourseSchedule> viewScheduleByDomain(Long domainId);


    @Query("select t.courses.courseCode,t.courses.name,t.courses.credits,t.courses.capacity,t.time,t.day,t.room from CourseSchedule  t where t.courses.courseId IN (select d.courses.courseId from CourseDomain  d where d.domain.domainId=?1)")
    List<Object[]> findCourseDetailsandScheduleByDomainId(Long domainId);

    @Query("select cs from CourseSchedule  cs where cs.courses.employee.employeeId=?1")
    List<CourseSchedule> getScheduleByFaculty(String employeeId);

    @Query("select t.courses.courseCode,t.courses.name,t.courses.credits,t.courses.capacity,t.time,t.day,t.room from CourseSchedule t,Student_Courses s where t.courses.courseId=s.course.courseId and s.student.Id=?1")
     List<Object[]> viewScheduleByStudent(Long studentId);

}
