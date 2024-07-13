package com.example.educat_be.DAO;

import com.example.educat_be.Entity.CourseDomain;
import com.example.educat_be.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDomainDAO extends JpaRepository<CourseDomain,Long> {

    @Query("select d.courses from CourseDomain  d where d.domain.domainId=?1")
    List<Courses> findCourseByDomain(Long domainId);

    @Query("select d from CourseDomain  d where d.courses.courseId=?1")
    List<CourseDomain> findDomainByCourse(Long courseId);

    @Modifying
    @Query("delete from CourseDomain d where d.courses.courseId=?1 and d.domain.domainId=?2")
    void removeCourseFromDomain(Long courseId,Long domainId);
}
