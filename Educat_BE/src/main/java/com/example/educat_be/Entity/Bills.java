package com.example.educat_be.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Bill")
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount",nullable = false)
    private Double amount;

    @Column(name = "billDate",nullable = false)
    private Date billDate;

    @Column(name = "deadline")
    private Date deadline;

    public Bills() {
    }

    public Bills(Long id, String description, Double amount, Date billDate, Date deadline) {
        Id = id;
        this.description = description;
        this.amount = amount;
        this.billDate = billDate;
        this.deadline = deadline;
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

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Bills{" +
                "Id=" + Id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", billDate=" + billDate +
                ", deadline=" + deadline +
                '}';
    }
}
