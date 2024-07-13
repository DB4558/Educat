package com.example.educat_be.DTO;

public class CountDTO {

    Integer courses;

    Integer students;

    Integer employees;

    Integer domains;

    Integer specialisations;

    public CountDTO() {
    }

    public CountDTO(Integer courses, Integer students, Integer employees, Integer domains, Integer specialisations) {
        this.courses = courses;
        this.students = students;
        this.employees = employees;
        this.domains = domains;
        this.specialisations = specialisations;
    }

    public Integer getCourses() {
        return courses;
    }

    public void setCourses(Integer courses) {
        this.courses = courses;
    }

    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public Integer getDomains() {
        return domains;
    }

    public void setDomains(Integer domains) {
        this.domains = domains;
    }

    public Integer getSpecialisations() {
        return specialisations;
    }

    public void setSpecialisations(Integer specialisations) {
        this.specialisations = specialisations;
    }
}
