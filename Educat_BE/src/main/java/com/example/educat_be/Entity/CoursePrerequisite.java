package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="course_prerequisite")
public class CoursePrerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name="courseId",referencedColumnName ="courseId" )
    @JsonIgnore
    private Courses courses;

    @ManyToOne
    @JoinColumn(name="preequisiteId",referencedColumnName = "courseId")
    @JsonIgnore
    private Courses prerequisite;

    @Column(name = "description")
    private String description;

    public CoursePrerequisite() {
    }

    public CoursePrerequisite(Long id, Courses courses, Courses prerequisite, String description) {
        Id = id;
        this.courses = courses;
        this.prerequisite = prerequisite;
        this.description = description;
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

    public Courses getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(Courses prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CoursePrerequisite{" +
                "Id=" + Id +
                ", courses=" + courses +
                ", prerequisite=" + prerequisite +
                ", description='" + description + '\'' +
                '}';
    }
}
