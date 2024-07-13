package com.example.educat_be.DTO;

import java.time.Year;

public class SpecialisationAll {

    private String studentId;

    private String rollNumber;

    private String fname;

    private String lname;

    private String email;

    private Year graduationYear;

    private String code;

    private String name;


    public SpecialisationAll(String studentId, String rollNumber, String fname, String lname, String email, Year graduationYear, String code, String name) {
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.graduationYear = graduationYear;
        this.code = code;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Year getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Year graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
