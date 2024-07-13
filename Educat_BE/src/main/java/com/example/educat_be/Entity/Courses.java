package com.example.educat_be.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Year;
import java.util.List;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="courseId")
    private Long courseId;

    @Column(name="courseCode",unique = true,nullable = false)
    private String courseCode;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "desciption")
    private String description;

    @Column(name = "year",nullable = false)
    private Year year;

    @Column(name = "term",nullable = false)
    private String term;

    @Column(name = "credits",nullable = false)
    private Integer credits;

    @Column(name="capacity",nullable = false)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "facultyId",referencedColumnName = "employeeId")
    private Employee employee;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Specialisation_Course> courseSpecialisations;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CourseDomain> courseDomains;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CoursePrerequisite> coursePrerequisites;
    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CourseSchedule> courseSchedules;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Student_Courses> studentCourses;




    public Courses() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Specialisation_Course> getCourseSpecialisations() {
        return courseSpecialisations;
    }

    public void setCourseSpecialisations(List<Specialisation_Course> courseSpecialisations) {
        this.courseSpecialisations = courseSpecialisations;
    }

    public List<CourseDomain> getCourseDomains() {
        return courseDomains;
    }

    public void setCourseDomains(List<CourseDomain> courseDomains) {
        this.courseDomains = courseDomains;
    }

    public List<CoursePrerequisite> getCoursePrerequisites() {
        return coursePrerequisites;
    }

    public void setCoursePrerequisites(List<CoursePrerequisite> coursePrerequisites) {
        this.coursePrerequisites = coursePrerequisites;
    }

    public List<CourseSchedule> getCourseSchedules() {
        return courseSchedules;
    }

    public void setCourseSchedules(List<CourseSchedule> courseSchedules) {
        this.courseSchedules = courseSchedules;
    }

    public List<Student_Courses> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Student_Courses> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Courses(Long courseId, String courseCode, String name, String description, Year year, String term, Integer credits, Integer capacity, Employee employee, List<Specialisation_Course> courseSpecialisations, List<CourseDomain> courseDomains, List<CoursePrerequisite> coursePrerequisites, List<CourseSchedule> courseSchedules, List<Student_Courses> studentCourses) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.name = name;
        this.description = description;
        this.year = year;
        this.term = term;
        this.credits = credits;
        this.capacity = capacity;
        this.employee = employee;
        this.courseSpecialisations = courseSpecialisations;
        this.courseDomains = courseDomains;
        this.coursePrerequisites = coursePrerequisites;
        this.courseSchedules = courseSchedules;
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseCode='" + courseCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", term='" + term + '\'' +
                ", credits=" + credits +
                ", capacity=" + capacity +
                ", employee=" + employee +
                ", courseSpecialisations=" + courseSpecialisations +
                ", courseDomains=" + courseDomains +
                ", coursePrerequisites=" + coursePrerequisites +
                ", courseSchedules=" + courseSchedules +
                ", studentCourses=" + studentCourses +
                '}';
    }
}
