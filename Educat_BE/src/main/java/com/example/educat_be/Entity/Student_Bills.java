package com.example.educat_be.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="StudentBills", uniqueConstraints={
        @UniqueConstraint(columnNames={"billId", "studentId"})
})
public class Student_Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "student_id")
    private Students students;

    @ManyToOne
    @JoinColumn(name="billId",referencedColumnName = "Id")
    private Bills bills;

    public Student_Bills() {
    }

    public Student_Bills(Long id, Students students, Bills bills) {
        Id = id;
        this.students = students;
        this.bills = bills;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }

    @Override
    public String toString() {
        return "Student_Bills{" +
                "Id=" + Id +
                ", students=" + students +
                ", bills=" + bills +
                '}';
    }
}
