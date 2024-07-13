package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Component
@Table(name="students")
public class Students implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_sequence", allocationSize = 1)
    private Long Id;

    @Column(name = "student_id",nullable = false,unique = true)
    private String student_id;

    @Column(name = "roll_number",nullable = false,unique = true)
    private String roll_number;

    @Column(name = "fname",nullable = false)
    private String first_name;

    @Column(name = "lname")
    private String last_name;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "cgpa",nullable = false)
    private Float cgpa;

    @Column(name = "total_credits",nullable = false)
    private Integer total_credits;

    @Column(name = "graduation_year")
    private Year graduation_year;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="profile_photo", columnDefinition = "MEDIUMTEXT")
    private String photo;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "domainId",referencedColumnName = "domainId",unique = false)
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "specialisationId",referencedColumnName = "specialisationId")
    private Specialisation specialisation;

    @ManyToOne
    @JoinColumn(name = "placementId",referencedColumnName = "placementId")
    private Placement placement;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Student_Courses> studentCourses;

    public Students() {
    }

    public Students(Long id, String student_id, String roll_number, String first_name, String last_name, String email, Float cgpa, Integer total_credits, Year graduation_year, String password, String photo, Boolean active, Domain domain, Specialisation specialisation, Placement placement, List<Student_Courses> studentCourses) {
        Id = id;
        this.student_id = student_id;
        this.roll_number = roll_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.cgpa = cgpa;
        this.total_credits = total_credits;
        this.graduation_year = graduation_year;
        this.password = password;
        this.photo = photo;
        this.active = active;
        this.domain = domain;
        this.specialisation = specialisation;
        this.placement = placement;
        this.studentCourses = studentCourses;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
        generateEmail();
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        generateEmail();
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getCgpa() {
        return cgpa;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getTotal_credits() {
        return total_credits;
    }

    public void setTotal_credits(Integer total_credits) {
        this.total_credits = total_credits;
    }

    public Year getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(Year graduation_year) {
        this.graduation_year = graduation_year;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("STUDENT"));
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

    @PrePersist
    public void generateStudent_Id() {
        if (Id != null) {
             student_id= "S" + String.format("%02d", Id);
            generateEmail();
        }
    }
    public void generateEmail() {
        if (this.first_name != null && !this.first_name.trim().isEmpty() && this.student_id != null) {
            this.email = this.first_name.trim().toLowerCase().replaceAll("\\s+", "") + "."+this.student_id+ "@educat.com";
        }
    }

    public boolean isPasswordMatch(String enteredPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(enteredPassword, this.password);
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public List<Student_Courses> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Student_Courses> studentCourses) {
        this.studentCourses = studentCourses;
    }


    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    @Override
    public String toString() {
        return "Students{" +
                "Id=" + Id +
                ", student_id='" + student_id + '\'' +
                ", roll_number='" + roll_number + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", cgpa=" + cgpa +
                ", total_credits=" + total_credits +
                ", graduation_year=" + graduation_year +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", active=" + active +
                ", domain=" + domain +
                ", specialisation=" + specialisation +
                ", placement=" + placement +
                ", studentCourses=" + studentCourses +
                '}';
    }
}
