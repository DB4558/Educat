package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "placement")
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="placementId")
    private Long placementId;

    @Column(name="profile",nullable = false)
    private String profile;

    @Column(name="description")
    private String description;

    @Column(name = "intake",nullable = false)
    private Integer intake;

    @Column(name="minimumCgpa")
    private Double minimumcgpa;

    @ManyToOne
    @JoinColumn(name="organisationId",referencedColumnName = "organisationId")
    private Organisation organisation;


    public Placement() {
    }

    public Placement(Long placementId, String profile, String description, Integer intake, Double minimumcgpa, Organisation organisation) {
        this.placementId = placementId;
        this.profile = profile;
        this.description = description;
        this.intake = intake;
        this.minimumcgpa = minimumcgpa;
        this.organisation = organisation;
    }

    public Long getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Long placementId) {
        this.placementId = placementId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntake() {
        return intake;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    public Double getMinimumcgpa() {
        return minimumcgpa;
    }

    public void setMinimumcgpa(Double minimumcgpa) {
        this.minimumcgpa = minimumcgpa;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return "Placement{" +
                "placementId=" + placementId +
                ", profile='" + profile + '\'' +
                ", description='" + description + '\'' +
                ", intake=" + intake +
                ", minimumcgpa=" + minimumcgpa +
                ", organisation=" + organisation +
                '}';
    }
}
