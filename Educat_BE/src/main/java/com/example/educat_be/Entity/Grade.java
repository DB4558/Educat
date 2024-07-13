package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gradeId")
    private Long gradeId;

    @Column(name = "letterGrade",unique = true)
    private String letterGrade;

    @Column(name="gradePoints",nullable = false)
    private Double gradePoints;

    @Column(name="comments")
    private String comments;

    public Grade() {
    }

    public Grade(Long gradeId, String letterGrade, Double gradePoints, String comments) {
        this.gradeId = gradeId;
        this.letterGrade = letterGrade;
        this.gradePoints = gradePoints;
        this.comments = comments;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public Double getGradePoints() {
        return gradePoints;
    }

    public void setGradePoints(Double gradePoints) {
        this.gradePoints = gradePoints;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", letterGrade='" + letterGrade + '\'' +
                ", gradePoints=" + gradePoints +
                ", comments='" + comments + '\'' +
                '}';
    }
}
