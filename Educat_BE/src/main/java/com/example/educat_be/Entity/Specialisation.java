package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Year;
import java.util.List;

@Entity
@Table(name = "specialisation")
public class Specialisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialisationId")
    private Long specialisationId;

    @Column(name = "code",unique = true,nullable = false)
    private String code;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "year",nullable = false)
    private Year year;

    @Column(name="credits_required",nullable = false)
    private Integer credits_required;

    @JsonIgnore
    @OneToMany(mappedBy = "specialisation",cascade = CascadeType.ALL)
    private List<Students> students;


    @JsonIgnore
    @OneToMany(mappedBy = "specialisation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Specialisation_Course> specialisationCourses;

    public Specialisation() {
    }

    public Specialisation(Long specialisationId, String code, String name, String description, Year year, Integer credits_required, List<Students> students, List<Specialisation_Course> specialisationCourses) {
        this.specialisationId = specialisationId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.year = year;
        this.credits_required = credits_required;
        this.students = students;
        this.specialisationCourses = specialisationCourses;
    }

    public List<Specialisation_Course> getSpecialisationCourses() {
        return specialisationCourses;
    }

    public void setSpecialisationCourses(List<Specialisation_Course> specialisationCourses) {
        this.specialisationCourses = specialisationCourses;
    }

    public Long getSpecialisationId() {
        return specialisationId;
    }

    public void setSpecialisationId(Long specialisationId) {
        this.specialisationId = specialisationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Integer getCredits_required() {
        return credits_required;
    }

    public void setCredits_required(Integer credits_required) {
        this.credits_required = credits_required;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Specialisation{" +
                "specialisationId=" + specialisationId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", credits_required=" + credits_required +
                ", students=" + students +
                ", specialisationCourses=" + specialisationCourses +
                '}';
    }
}
