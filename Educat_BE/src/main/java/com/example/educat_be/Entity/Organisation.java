package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organisationId")
    private Long organisationId;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    public Organisation() {
    }

    public Organisation(Long organisationId, String name, String address) {
        this.organisationId = organisationId;
        this.name = name;
        this.address = address;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "organisationId=" + organisationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
