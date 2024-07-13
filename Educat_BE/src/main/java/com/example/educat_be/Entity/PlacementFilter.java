package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="placementFilter")
public class PlacementFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="placementFilterId")
    private Long placementFilterId;

    @ManyToOne
    @JoinColumn(name = "placementId",referencedColumnName = "placementId")
    private Placement placement;

    @ManyToOne
    @JoinColumn(name="specialisationId",referencedColumnName = "specialisationId")
    private Specialisation specialisation;

    @ManyToOne
    @JoinColumn(name="domainId",referencedColumnName = "domainId")
    private Domain domain;

    public PlacementFilter() {
    }

    public PlacementFilter(Long placementFilterId, Placement placement, Specialisation specialisation, Domain domain) {
        this.placementFilterId = placementFilterId;
        this.placement = placement;
        this.specialisation = specialisation;
        this.domain = domain;
    }

    public Long getPlacementFilterId() {
        return placementFilterId;
    }

    public void setPlacementFilterId(Long placementFilterId) {
        this.placementFilterId = placementFilterId;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "PlacementFilter{" +
                "placementFilterId=" + placementFilterId +
                ", placement=" + placement +
                ", specialisation=" + specialisation +
                ", domain=" + domain +
                '}';
    }
}
