package com.example.educat_be.DTO;

public class GradeDTO {

    private String code;

    private String CourseName;

    private String grade;

    private Double points;

    public GradeDTO(String code, String courseName, String grade, Double points) {
        this.code = code;
        CourseName = courseName;
        this.grade = grade;
        this.points = points;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
