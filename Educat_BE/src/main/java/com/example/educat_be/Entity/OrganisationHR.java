package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="organisationHR")
public class OrganisationHR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="organisationId")
    private Long organisationHRId;

    @Column(name = "fname",nullable = false)
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name="phone")
    private Number phone;

    @OneToOne
    @JoinColumn(name="organisationId",referencedColumnName = "organisationId")
    private Organisation organisation;

    public OrganisationHR() {
    }

    public OrganisationHR(Long organisationHRId, String fname, String lname, String email, Number phone, Organisation organisation) {
        this.organisationHRId = organisationHRId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.organisation = organisation;
    }

    public Long getOrganisationHRId() {
        return organisationHRId;
    }

    public void setOrganisationHRId(Long organisationHRId) {
        this.organisationHRId = organisationHRId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Number getPhone() {
        return phone;
    }

    public void setPhone(Number phone) {
        this.phone = phone;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return "OrganisationHR{" +
                "organisationHRId=" + organisationHRId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", organisation=" + organisation +
                '}';
    }
}
