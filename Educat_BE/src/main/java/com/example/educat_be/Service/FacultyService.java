package com.example.educat_be.Service;

import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;

import java.util.List;
import java.util.Set;

public interface FacultyService {

    Boolean selectCourse(Long courseId,String employeeId);

    List<Courses> viewCourses();

    List<Courses>  viewCoursesByFaculty(String employeeId);

    List<Students> viewStudentsByCourse(Long courseId);

    Student_Courses gradeStudent(Long courseId,Long studentId,Long gradeId);

    void gradeMultipleStudent(Set<Long> studentId,Long courseId,Long gradeId);

    List<CourseSchedule> getSchedulesByCourse(Long courseId);

    Specialisation getSpecialisationByCourse(Long courseId);

    List<CourseSchedule> getScheduleByFaculty(String employeeId);
    List<Salary> viewSalaryHistoryByEmployee(String employeeId);

    List<Salary> viewSalaryHistoryByEmployeeForMonth(String employeeId, Integer month);


    void registerTA(Long courseId,String studentId);

    List<Students> viewTAByCourse(Long courseId);

    List<StudentMessage> getStudentMessage(String employeeId);

    LoginResponse facultyLogin(LoginDTO loginDTO);
}
