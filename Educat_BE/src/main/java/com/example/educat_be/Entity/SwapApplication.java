package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="swapApplication")
public class SwapApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @Column(name="applicantMessage")
    private String applicantMessage;

    @Column(name="status")
    private String status;

    @Column(name = "recipientMessage")
    private String recipientMessage;

    @ManyToOne
    @JoinColumn(name="applicant",referencedColumnName = "student_id")
    private Students applicant;

    @ManyToOne
    @JoinColumn(name = "recipient",referencedColumnName = "student_id")
    private Students recipient;

    public SwapApplication() {
    }

    public SwapApplication(Long id, String applicantMessage, String status, String recipientMessage, Students applicant, Students recipient) {
        Id = id;
        this.applicantMessage = applicantMessage;
        this.status = status;
        this.recipientMessage = recipientMessage;
        this.applicant = applicant;
        this.recipient = recipient;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getApplicantMessage() {
        return applicantMessage;
    }

    public void setApplicantMessage(String applicantMessage) {
        this.applicantMessage = applicantMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipientMessage() {
        return recipientMessage;
    }

    public void setRecipientMessage(String recipientMessage) {
        this.recipientMessage = recipientMessage;
    }

    public Students getApplicant() {
        return applicant;
    }

    public void setApplicant(Students applicant) {
        this.applicant = applicant;
    }

    public Students getRecipient() {
        return recipient;
    }

    public void setRecipient(Students recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "SwapApplication{" +
                "Id=" + Id +
                ", applicantMessage='" + applicantMessage + '\'' +
                ", status='" + status + '\'' +
                ", recipientMessage='" + recipientMessage + '\'' +
                ", applicant=" + applicant +
                ", recipient=" + recipient +
                '}';
    }
}
