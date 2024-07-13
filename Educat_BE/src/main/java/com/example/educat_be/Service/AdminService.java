package com.example.educat_be.Service;

import com.example.educat_be.DTO.*;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;

import java.util.List;

public interface AdminService{
    Domain saveDomain(Domain domain);

    Domain modifyDomain(Long domainId,Domain domain);

    Domain viewDomainById(Long domainId);

    List<Domain> viewDomain();


    Specialisation saveSpecialisation(Specialisation specialisation);

    Specialisation editSpecialisation(Long specialisationId,Specialisation specialisation);

    Specialisation viewSpecialisationById(Long specialisationId);

    List<Specialisation> viewSpecialisation();

    void deleteSpecialisation(Long specialisationId);

    Courses saveCourse(Courses courses);

    Courses editCourses(Long courseId,Courses courses);

    Courses viewCourseById(Long courseId);

    List<Courses> viewCourseList();

    void deleteCourse(Long courseId);

    List<Courses> viewPrerequisiteListByCourse(Long courseId);

    CoursePrerequisite addprerequisite(Long courseId,Long prerequisiteId,CoursePrerequisite coursePrerequisite);

    Specialisation_Course addCourseToSpecialisation(Long courseId,Long specialisationId);

    List<Courses> viewCoursesBySpecialisation(Long specialisationId);

    CourseSchedule addCourseSchedule(Long courseId,CourseSchedule courseSchedule);

    List<CourseSchedule> viewScheduleByCourse(Long courseId);

    CourseSchedule editCourseSchedule(Long Id,CourseSchedule courseSchedule);

    void deleteCourseSchedule(Long Id);

    CourseDomain addCourseToDomain(Long courseId,Long domainId);

    List<Courses> viewCoursesByDomain(Long domainId);

    List<CourseScheduleDTO> findCourseDetailsandScheduleByDomainId(Long domainId);

    Students addStudent(Students students,Long domainId);

    Students editStudent(String student_id,Students students);

    Students viewStudentById(String studentId);

    List<Students> viewStudent();


    List<Students> viewStudentsByDomain(Long domainId);

    List<Students>  viewStudentsByCourse(Long courseId);

    Departments addDepartment(Departments departments);

    Departments editDepartment(Long departmentId,Departments departments);

    Departments viewDepartmentById(Long departmentId);

    List<Departments> viewDepartments();

    void deleteDepartment(Long departmentId);

    Employee addEmployee(Long departmentId,Employee employee);

    Employee editEmployee(String employeeId,Employee employee);

    Employee viewEmployeeById(String employeeId);

    List<Employee> viewEmployee();

    List<Employee> viewEmployeeByDepartment(Long departmentId);



    void computeSpecialisation(Long Id);

    List<SpecialisationAll> viewSpecialisationAll();



    List<Students> viewStudentsBySpecialisation(Long specialisationId);



     void removeCourseFromDomain(Long courseId,Long domainId);

     void removePrerequisiteForCourse(Long courseId,Long prerequisiteId);

     void inactivateStudent(String studentId);

     void inactivateEmployee(String employeeId);

     void activateEmployee(String employeeId);

     Admin addAdmin(Admin admin);

     Admin viewAdmin(String email);

     void activateStudent(String studentId);

     Admin changePassword(String email,String password);

     LoginResponse adminLogin(LoginDTO loginDTO);

     CountDTO getCounts();

     CourseSchedule viewCourseScheduleById(Long Id);

     Grade addGrade(Grade grade);

     List<Grade> viewGrade();

     void Logout(String email);

     Grade viewGradeById(Long gradeId);

     Grade editGrade(Long gradeId,Grade grade);



}