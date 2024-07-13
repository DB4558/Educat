package com.example.educat_be.DAO;

import com.example.educat_be.Entity.StudentPlacement;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentPlacementDAO extends JpaRepository<StudentPlacement,Long> {

    @Query("select s.students from StudentPlacement s where s.placement.placementId=?1")
    List<Students> getAppliedStudent(Long placementId);

    @Query("select s from PlacementFilter p,Students s where (s.specialisation.specialisationId=p.specialisation.specialisationId or p.specialisation is null) and s.cgpa>=p.placement.minimumcgpa and (s.domain.domainId=p.domain.domainId or p.domain is null) and p.placement.placementId=?1 and s.student_id NOT IN (select s1.students.student_id from StudentPlacement s1 where s1.placement.placementId=?1)")
    List<Students> getNotAppliedStudent(Long placementId);

    @Query("select s from StudentPlacement s where s.students.student_id=?2 and s.placement.placementId=?1")
    StudentPlacement getRequiredStudent(Long placementId,String studentId);

    @Query("select d from StudentPlacement d where d.students.student_id=?1 and d.acceptance='PENDING'")
    List<StudentPlacement> findApplicationById(String studentId);

    @Query("select d from StudentPlacement d where d.students.student_id=?1 and d.acceptance='OFFERED'")
    List<StudentPlacement> findOfferedApplication(String studentId);
}
