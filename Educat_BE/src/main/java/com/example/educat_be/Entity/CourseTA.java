package com.example.educat_be.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "CourseTA")
public class CourseTA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    private Courses courses;

    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "student_id")
    private Students students;

    public CourseTA() {
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

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public CourseTA(Long id, Courses courses, Students students) {
        Id = id;
        this.courses = courses;
        this.students = students;
    }

    @Override
    public String toString() {
        return "CourseTA{" +
                "Id=" + Id +
                ", courses=" + courses +
                ", students=" + students +
                '}';
    }
}
