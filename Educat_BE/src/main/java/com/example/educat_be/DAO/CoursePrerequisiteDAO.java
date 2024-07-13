package com.example.educat_be.DAO;

import com.example.educat_be.Entity.CoursePrerequisite;
import com.example.educat_be.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursePrerequisiteDAO extends JpaRepository<CoursePrerequisite,Long> {

    @Query("select p.prerequisite from CoursePrerequisite p where p.courses.courseId=?1")
    List<Courses> findPrerequisiteList(Long courseId);

    @Query("select p from CoursePrerequisite p where p.courses.courseId=?1 UNION select p1 from CoursePrerequisite p1 where p1.prerequisite.courseId=?1")
    List<CoursePrerequisite> findByCourseId(Long courseId);

    @Modifying
    @Query("delete from CoursePrerequisite p where p.courses.courseId=?1 and p.prerequisite.courseId=?2")
    void removePrerequisiteForCourse(Long courseId,Long prerequisiteId);
}
