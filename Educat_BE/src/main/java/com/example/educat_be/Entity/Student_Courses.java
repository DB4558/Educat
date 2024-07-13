package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="student_courses")
public class Student_Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "Id")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "gradeId", referencedColumnName = "gradeId")
    private Grade grade;



    public Student_Courses() {
    }

    public Student_Courses(Students student, Courses course, Grade grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = Id;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student_Courses{" +
                "Id=" + Id +
                ", student=" + student +
                ", course=" + course +
                ", grade=" + grade +
                '}';
    }
}



