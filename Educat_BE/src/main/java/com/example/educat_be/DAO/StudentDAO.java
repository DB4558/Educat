package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Placement;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface StudentDAO extends JpaRepository<Students,Long> {
    @Query("select count(s) from Students s where s.roll_number LIKE ?1%")
    int countByRollNumberStartingWith(String prefix);

    @Query("select s from Students s where s.student_id=?1")
   Students findByStudentId(String student_id);

    @Query("select s from Students  s where s.domain.domainId=?1")
    List<Students> findStudentByDomainId(Long domainId);

    @Query("select s.student_id,s.roll_number,s.first_name,s.last_name,s.email,s.graduation_year,s.specialisation.code,s.specialisation.name from Students  s where s.specialisation is not null")
    List<Object[]> viewSpecialisation();

    @Query("select s.student_id,s.roll_number,s.first_name,s.last_name,s.email,s.graduation_year,s.specialisation.code,s.specialisation.name from Students  s where s.specialisation is not null and s.domain.domainId=?1")
    List<Object[]> viewSpecialisationByDomain(Long domainId);

    @Query("select s from Students  s where s.specialisation.specialisationId=?1")
    List<Students> viewStudentsBySpecialisation(Long specialisationId);

    @Modifying
    @Query("delete from Students  s where s.student_id=?1")
    void deleteStudentById(String studentId);

    @Modifying
    @Query("update Students s set s.specialisation.specialisationId=null where s.specialisation.specialisationId=?1")
    void updateSpecialisation(Long specialisationId);

    @Modifying
    @Query("update Students s set s.active=false where s.student_id=?1")
    void inactivateStudent(String studentId);

 @Modifying
 @Query("update Students s set s.active=true where s.student_id=?1")
 void activateStudent(String studentId);

    @Query("select s from Students s where s.email=?1")
    Students findByEmail(String email);

    @Query("select s from Students  s where s.Id=?1")
    Students findStudentById(Long Id);

 @Query("select distinct p.placement from PlacementFilter p,Students s where (s.specialisation.specialisationId=p.specialisation.specialisationId or p.specialisation is null) and s.cgpa>=p.placement.minimumcgpa and (s.domain.domainId=p.domain.domainId or p.domain is null) and s.student_id=?1")
    List<Placement> getEligibleOffers(String studentId);

 @Query("select s from Students s where s.placement is not null")
 List<Students> findPlacedStudents();

 @Query("select s from Students s where s.placement is null")
 List<Students> findUnPlacedStudents();

 @Query("select s from Students s where s.placement.organisation.organisationId=?1")
 List<Students> getPlacedStudentsByOrganisation(Long organisationId);

 @Query("select s from Students s where s.placement.organisation.organisationId=?1 and s.graduation_year=?2")
 List<Students> getPlacedStudentsByOrganisationandYear(Long organisationId,Year year);

 @Query("select s from Students s where s.graduation_year=?1 and s.placement is not null")
 List<Students> getPlacedStudentsByYear(Year year);

 @Query("select s from Students s where s.domain.domainId=?1 and s.placement is not null")
 List<Students> getPlacedStudentsByDomain(Long domainId);

 @Query("select s from Students s where s.domain.domainId=?2 and s.graduation_year=?1 and  s.placement is not null")
 List<Students> getPlacedStudentsByYearDomain(Year year,Long domainId);

 @Query("select s from Students s where s.domain.domainId=?2 and s.graduation_year=?1 and  s.placement is  null")
 List<Students> getUnPlacedStudentsByYearDomain(Year year,Long domainId);

 @Query("select count(s) from Students s where s.domain.domainId=?2 and s.graduation_year=?1 and  s.placement is not null")
 Integer countPlacedStudentsByYearDomain(Year year,Long domainId);

 @Query("select count(s) from Students s where s.domain.domainId=?2 and s.graduation_year=?1 and  s.placement is null")
 Integer countUnPlacedStudentsByYearDomain(Year year,Long domainId);


 @Query("select count(s) from Students s where s.domain.domainId=?1 and s.placement is not null")
 Integer countPlacedStudentsByDomain(Long domainId);

 @Query("select s from Students s where s.graduation_year=?1 and s.placement is  null")
 List<Students> getUnPlacedStudentsByYear(Year year);

 @Query("select s from Students s where s.domain.domainId=?1 and s.placement is  null")
 List<Students> getUnPlacedStudentsByDomain(Long domainId);

 @Query("select count(s) from Students s where s.domain.domainId=?1 and s.placement is  null")
 Integer countUnPlacedStudentsByDomain(Long domainId);

 @Query("select count(s) from Students s where s.graduation_year=?1 and s.placement is  null")
 Integer countUnPlacedStudentsByYear(Year year);

 @Query("select count(s) from Students s where s.graduation_year=?1 and s.placement is not null")
 Integer countPlacedStudentsByYear(Year year);

 @Query("select count(s) from Students s")
 Integer getStudentCount();
}
