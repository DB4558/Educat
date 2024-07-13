package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domainId")
    private Long domainId;

    @Column(name = "program",nullable = false)
    private String program;

    @Column(name = "batch",nullable = false)
    private String batch;

    @Column(name = "capacity",nullable = false)
    private Long capacity;

    @Column(name = "qualification")
    private String qualification;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CourseDomain> courseDomains;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Students> students;


    public Domain() {
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }


    public List<CourseDomain> getCourseDomains() {
        return courseDomains;
    }

    public void setCourseDomains(List<CourseDomain> courseDomains) {
        this.courseDomains = courseDomains;
    }

    public Domain(Long domainId) {
        this.domainId = domainId;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }

    public Domain(Long domainId, String program, String batch, Long capacity, String qualification, List<CourseDomain> courseDomains, List<Students> students) {
        this.domainId = domainId;
        this.program = program;
        this.batch = batch;
        this.capacity = capacity;
        this.qualification = qualification;
        this.courseDomains = courseDomains;
        this.students = students;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "domainId=" + domainId +
                ", program='" + program + '\'' +
                ", batch='" + batch + '\'' +
                ", capacity=" + capacity +
                ", qualification='" + qualification + '\'' +
                ", courseDomains=" + courseDomains +
                ", students=" + students +
                '}';
    }
}
