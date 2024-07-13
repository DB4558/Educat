package com.example.educat_be.DAO;

import com.example.educat_be.Entity.PlacementFilter;
import com.example.educat_be.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlacementFilterDAO extends JpaRepository<PlacementFilter,Long> {

    @Query("select s from PlacementFilter p,Students s where (s.specialisation.specialisationId=p.specialisation.specialisationId or p.specialisation is null) and s.cgpa>=p.placement.minimumcgpa and (s.domain.domainId=p.domain.domainId or p.domain is null) and p.placement.placementId=?1")
    List<Students> getEligibleStudents(Long placementFilterId);

    @Query("select p from PlacementFilter p where p.placement.organisation.organisationId=?1")
    List<PlacementFilter> viewAllOffersByOrganisationId(Long organisationId);
}
