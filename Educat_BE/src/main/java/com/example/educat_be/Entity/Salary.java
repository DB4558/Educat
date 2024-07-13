package com.example.educat_be.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="salary")
public class Salary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="salaryId")
    private Long SalaryId;

    @Column(name = "paymentDate",nullable = false)
    private Date paymentDate;

    @Column(name = "amount",nullable = false)
    private Double amount;

    @Column(name = "description")
    private String description;

    public Salary() {
    }

    public Salary(Long salaryId, Date paymentDate, Double amount, String description) {
        SalaryId = salaryId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.description = description;
    }

    public Long getSalaryId() {
        return SalaryId;
    }

    public void setSalaryId(Long salaryId) {
        SalaryId = salaryId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "SalaryId=" + SalaryId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
