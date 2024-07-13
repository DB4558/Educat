package com.example.educat_be.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employeeSalary", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employeeId", "salaryId"})
})
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeSalaryId")
    private Long employeeSalaryId;

    @ManyToOne
    @JoinColumn(name = "employeeId",referencedColumnName = "employeeId")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "salaryId",referencedColumnName = "salaryId")
    private Salary salary;

    public EmployeeSalary() {
    }

    public EmployeeSalary(Long employeeSalaryId, Employee employee, Salary salary) {
        this.employeeSalaryId = employeeSalaryId;
        this.employee = employee;
        this.salary = salary;
    }

    public Long getEmployeeSalaryId() {
        return employeeSalaryId;
    }

    public void setEmployeeSalaryId(Long employeeSalaryId) {
        this.employeeSalaryId = employeeSalaryId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "employeeSalaryId=" + employeeSalaryId +
                ", employee=" + employee +
                ", salary=" + salary +
                '}';
    }
}
