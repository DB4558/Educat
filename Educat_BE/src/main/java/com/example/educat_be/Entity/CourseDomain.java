package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courseDomain")
public class CourseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    @JsonIgnore
    private Courses courses;

    @ManyToOne
    @JoinColumn(name = "domainId",referencedColumnName = "domainId")
    @JsonIgnore
    private Domain domain;

    public CourseDomain() {
    }

    public CourseDomain(Long id, Courses courses, Domain domain) {
        Id = id;
        this.courses = courses;
        this.domain = domain;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "CourseDomain{" +
                "Id=" + Id +
                ", courses=" + courses +
                ", domain=" + domain +
                '}';
    }
}
