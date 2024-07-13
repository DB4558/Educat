package com.example.educat_be.DTO;

import java.time.Year;

public class FacultyDTO {

    private String code;

    private String name;

    private String term;

    private Year year;

    private Integer credit;

    private Integer capacity;

    private String employeeId;

    private String fname;

    private String lname;

    private String title;

    private String email;

    public FacultyDTO(String code, String name, String term, Year year, Integer credit, Integer capacity, String employeeId, String fname, String lname, String title, String email) {
        this.code = code;
        this.name = name;
        this.term = term;
        this.year = year;
        this.credit = credit;
        this.capacity = capacity;
        this.employeeId = employeeId;
        this.fname = fname;
        this.lname = lname;
        this.title = title;
        this.email = email;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
