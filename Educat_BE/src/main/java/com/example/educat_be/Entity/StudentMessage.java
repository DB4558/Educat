package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "studentMessage")
public class StudentMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Long messageId;

    @Column(name = "CourseName")
    private String courseName;

    @Column(name = "message")
    private String message;

    @Column(name = "studentId")
    private String studentId;

    @Column(name="rollnumber")
    private String rollnumber;

    @Column(name = "name")
    private String studentName;

    @Column(name = "domainName")
    private String domainName;

    @Column(name = "specializationName")
    private String specializationName;

    @Column(name="facultyId")
    private String facultyId;

    public StudentMessage() {
    }

    public StudentMessage(Long messageId, String courseName, String message, String studentId, String rollnumber, String studentName, String domainName, String specializationName, String facultyId) {
        this.messageId = messageId;
        this.courseName = courseName;
        this.message = message;
        this.studentId = studentId;
        this.rollnumber = rollnumber;
        this.studentName = studentName;
        this.domainName = domainName;
        this.specializationName = specializationName;
        this.facultyId = facultyId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }


    @Override
    public String toString() {
        return "StudentMessage{" +
                "messageId=" + messageId +
                ", courseName='" + courseName + '\'' +
                ", message='" + message + '\'' +
                ", studentId='" + studentId + '\'' +
                ", rollnumber='" + rollnumber + '\'' +
                ", studentName='" + studentName + '\'' +
                ", domainName='" + domainName + '\'' +
                ", specializationName='" + specializationName + '\'' +
                ", facultyId='" + facultyId + '\'' +
                '}';
    }
}

