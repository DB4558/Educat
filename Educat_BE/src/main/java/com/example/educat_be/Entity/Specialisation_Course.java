package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="Specialisation_Course")
public class Specialisation_Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name="specialisationId",referencedColumnName = "specialisationId")
    private Specialisation specialisation;

    @ManyToOne
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    private Courses courses;

    public Specialisation_Course() {
    }

    public Specialisation_Course(Long id, Specialisation specialisation, Courses courses) {
        Id = id;
        this.specialisation = specialisation;
        this.courses = courses;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Specialisation_Course{" +
                "Id=" + Id +
                ", specialisation=" + specialisation +
                ", courses=" + courses +
                '}';
    }
}
