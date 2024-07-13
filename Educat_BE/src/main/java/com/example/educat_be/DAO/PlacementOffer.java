package com.example.educat_be.DAO;

import com.example.educat_be.Entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlacementOffer extends JpaRepository<Placement,Long> {

    @Query("select p from Placement p where p.placementId=?1")
    Placement getPlacementById(Long placementId);
}
