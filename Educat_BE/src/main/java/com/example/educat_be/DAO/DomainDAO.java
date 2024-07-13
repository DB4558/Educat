package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DomainDAO extends JpaRepository<Domain,Long> {

    @Query("select d from Domain d where d.domainId=?1")
    Domain findByDomainId(Long domainId);

    @Modifying
    @Query("delete from Domain d where d.domainId=?1")
    void deleteDomainById(Long domainId);

    @Query("select count(s) from Domain s")
    Integer getDomainCount();
}
