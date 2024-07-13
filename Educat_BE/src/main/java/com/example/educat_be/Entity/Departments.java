package com.example.educat_be.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="departments")
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="departmentId")
    private Long departmentId;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="capacity",nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "departments", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Employee> employees;


    public Departments() {
    }

    public Departments(Long departmentId, String name, Integer capacity, List<Employee> employees) {
        this.departmentId = departmentId;
        this.name = name;
        this.capacity = capacity;
        this.employees = employees;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Departments{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", employees=" + employees +
                '}';
    }
}
