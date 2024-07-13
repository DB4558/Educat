package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Specialisation;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SpecialisationDAO extends JpaRepository<Specialisation,Long> {

    @Query("select s from Specialisation s where s.specialisationId=?1")
    Specialisation findBySpecialisationId(Long specialisationId);

    @Modifying
    @Query("delete from Specialisation  s where s.specialisationId=?1")
    void deleteSpecialisationById(Long specialisationId);

    @Query("select count(s) from Specialisation  s")
    Integer getSpecialisationCount();
}
