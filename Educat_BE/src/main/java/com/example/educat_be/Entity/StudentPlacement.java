package com.example.educat_be.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="studentPlacement")
public class StudentPlacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long Id;

    @Column(name = "about")
    private String about;

    @Column(name="acceptanceStatus")
    private String acceptance;

    @Column(name = "comments")
    private String comments;

    @Column(name="date")
    private Date date;

    @Column(name="CVApplication", columnDefinition = "MEDIUMTEXT")
    private String cvApplication;

    @ManyToOne
    @JoinColumn(name="placementId",referencedColumnName = "placementId")
    private Placement placement;

    @ManyToOne
    @JoinColumn(name="studentId",referencedColumnName = "student_id")
    private Students students;

    public StudentPlacement() {
    }

    public StudentPlacement(Long id, String about, String acceptance, String comments, Date date, String cvApplication, Placement placement, Students students) {
        Id = id;
        this.about = about;
        this.acceptance = acceptance;
        this.comments = comments;
        this.date = date;
        this.cvApplication = cvApplication;
        this.placement = placement;
        this.students = students;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public String getCvApplication() {
        return cvApplication;
    }

    public void setCvApplication(String cvApplication) {
        this.cvApplication = cvApplication;
    }

    @Override
    public String toString() {
        return "StudentPlacement{" +
                "Id=" + Id +
                ", about='" + about + '\'' +
                ", acceptance='" + acceptance + '\'' +
                ", comments='" + comments + '\'' +
                ", date=" + date +
                ", cvApplication='" + cvApplication + '\'' +
                ", placement=" + placement +
                ", students=" + students +
                '}';
    }
}
