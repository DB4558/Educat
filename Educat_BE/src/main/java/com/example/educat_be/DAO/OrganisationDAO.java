package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganisationDAO extends JpaRepository<Organisation,Long> {

    @Query("select o from Organisation o where o.organisationId=?1")
    Organisation getOrganisationById(Long organisationId);
}
