package com.example.educat_be.Entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "studentPayment")
public class Student_Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount",nullable = false)
    private Double amount;

    @Column(name = "paymentDate",nullable = false)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "student_id")
    private Students students;

    @ManyToOne
    @JoinColumn(name = "billid",referencedColumnName = "Id")
    private Bills bills;

    public Student_Payment() {
    }

    public Student_Payment(Long id, String description, Double amount, Date paymentDate, Students students, Bills bills) {
        Id = id;
        this.description = description;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.students = students;
        this.bills = bills;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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
        return "Student_Payment{" +
                "Id=" + Id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", students=" + students +
                ", bills=" + bills +
                '}';
    }
}
