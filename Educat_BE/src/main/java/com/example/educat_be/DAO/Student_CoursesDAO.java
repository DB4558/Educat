package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Courses;
import com.example.educat_be.Entity.Specialisation;
import com.example.educat_be.Entity.Student_Courses;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Student_CoursesDAO extends JpaRepository<Student_Courses,Long> {

    @Query("select s.student from Student_Courses  s where s.course.courseId=?1")
    List<Students> viewStudentByCourse(Long courseId);

    @Query("select s.specialisation from Student_Courses c,Specialisation_Course s,Courses c1 where c.course.courseId=s.courses.courseId and c.course.courseId=c1.courseId and c.student.Id=?1 group by s.specialisation.specialisationId having sum(c1.credits)>=20")
    Specialisation computeSpecialisation(Long Id);

    @Query("select q from Student_Courses q where q.course.courseId=?1")
    List<Student_Courses> findStudentByCourse(Long courseId);

    @Query("select s from Student_Courses  s where s.student.student_id=?1")
    List<Student_Courses> findByStudentId(String studentId);

    @Query("select count(s) from Student_Courses  s where s.student.Id=?1 group by s.student.Id")
    Integer countCourseByStudent(Long studentId);

    @Query("SELECT CASE WHEN EXISTS (" +
            "SELECT 1 FROM CoursePrerequisite cp " +
            "WHERE cp.courses.courseId = ?1 AND cp.prerequisite.courseId NOT IN (" +
            "SELECT sc.course.courseId FROM Student_Courses sc " +
            "WHERE sc.student.Id = ?2)) " +
            "THEN false ELSE true END")
    Boolean selectCourse(Long courseId, Long studentId);

    @Query("select s.course from Student_Courses  s where s.student.Id=?1")
    List<Courses> viewCoursesByStudent(Long studentId);

    @Query("select c.courseCode,c.name,g.letterGrade,g.gradePoints from Student_Courses s,Grade g,Courses  c where s.grade.gradeId=g.gradeId and c.courseId=s.course.courseId and s.student.Id=?1")
    List<Object[]> viewGrade(Long studentId);

    @Query("select s from Student_Courses  s where s.course.courseId=?1 and s.student.Id=?2")
    Student_Courses getByCourseAndStudent(Long courseId,Long studentId);

}
