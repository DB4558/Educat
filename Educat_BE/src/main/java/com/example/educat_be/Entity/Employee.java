package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Component
@Table(name="employee")
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_sequence", allocationSize = 1)
    private Long Id;

    @Column(name = "employeeId",nullable = false,unique = true)
    private String employeeId;

    @Column(name = "Fname",nullable = false)
    private String fname;

    @Column(name = "Lname")
    private String lname;

    @Column(name="Title",nullable = false)
    private String title;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="profile_photo", columnDefinition = "MEDIUMTEXT")
    private String photo;

    @Column(name = "active")
    private Boolean active;


    @ManyToOne
    @JoinColumn(name="departmentId",referencedColumnName = "departmentId")
    private Departments departments;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Courses> courses;

    @PrePersist
    public void generateEmployeeId() {
        if (Id != null) {
            employeeId= "E" + String.format("%02d", Id);
            generateEmail();
        }
    }
    public void generateEmail() {
        if (this.fname != null && !this.fname.trim().isEmpty() && this.employeeId != null) {
            this.email = this.fname.trim().toLowerCase().replaceAll("\\s+", "") + "."+this.employeeId+ "@educat.com";
        }
    }

    public boolean isPasswordMatch(String enteredPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(enteredPassword, this.password);
    }

    public Employee() {
    }

    public Employee(Long id, String employeeId, String fname, String lname, String title, String email, String password, String photo, Boolean active, Departments departments, List<Courses> courses) {
        Id = id;
        this.employeeId = employeeId;
        this.fname = fname;
        this.lname = lname;
        this.title = title;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.active = active;
        this.departments = departments;
        this.courses = courses;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        generateEmail();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
        generateEmail();;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("EMPLOYEE"));

    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }


    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", employeeId='" + employeeId + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", active=" + active +
                ", departments=" + departments +
                ", courses=" + courses +
                '}';
    }
}
