package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.AuthenticationAndAuthorisation.ServiceDetails;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyServiceImpl implements FacultyService, ServiceDetails {


    @Autowired
    CourseDAO courseDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    Student_CoursesDAO studentCoursesDAO;

    @Autowired
    CourseScheduleDAO courseScheduleDAO;

    @Autowired
    EmployeeSalaryDAO employeeSalaryDAO;

    @Autowired
    GradeDAO gradeDAO;

    @Autowired
    SpecialisationCourseDAO specialisationCourseDAO;

    @Autowired
    CourseTADAO courseTADAO;

    @Autowired
    StudentMessageDAO studentMessageDAO;

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public Boolean selectCourse(Long courseId,String employeeId){

       Integer count;
       count=courseDAO.findCourseByIdandTime(courseId,employeeId);
       if(count==null)
           count=0;
       if(count>0)
           return false;
       else
       {
           Courses courses=courseDAO.findByCourseId(courseId);
           Employee employee=employeeDAO.getEmployeeById(employeeId);
           courses.setEmployee(employee);
           courseDAO.save(courses);
           return true;
       }

    }


    @Transactional
    public List<Courses> viewCourses(){
        List<Courses> coursesList=courseDAO.findCoursewithNoFaculty();
        return coursesList;
    }

    @Transactional
    public List<Courses> viewCoursesByFaculty(String employeeId){
        List<Courses> coursesList=courseDAO.viewCourseByFaculty(employeeId);
        return coursesList;
    }

    @Transactional
    public List<Students> viewStudentsByCourse(Long courseId){
        List<Students> students=studentCoursesDAO.viewStudentByCourse(courseId);
        return students;
    }

    @Override
    public Student_Courses gradeStudent(Long courseId,Long studentId,Long gradeId){

       Student_Courses studentCourses=studentCoursesDAO.getByCourseAndStudent(courseId,studentId);
        Optional<Grade> optionalgrade=gradeDAO.findById(gradeId);
        Grade grade=optionalgrade.orElse(null);

        studentCourses.setGrade(grade);
        return studentCoursesDAO.save(studentCourses);

    }

    @Override
    public void gradeMultipleStudent(Set<Long> studentId,Long courseId, Long gradeId){

        List<Students> students=studentDAO.findAllById(studentId);
        Optional<Grade> optionalgrade=gradeDAO.findById(gradeId);
        Grade grade=optionalgrade.orElse(null);

        students.forEach(students1 -> {
            Student_Courses studentCourses=studentCoursesDAO.getByCourseAndStudent(courseId,students1.getId());
            studentCourses.setGrade(grade);
            studentCoursesDAO.save(studentCourses);
        });

    }

    @Transactional
    public List<CourseSchedule> getSchedulesByCourse(Long courseId){
        List<CourseSchedule> courseScheduleList=courseScheduleDAO.findScheduleByCourse(courseId);
        return  courseScheduleList;
    }

    @Transactional
    public Specialisation getSpecialisationByCourse(Long courseId){
        Specialisation specialisation=specialisationCourseDAO.findSpecialisationByCourse(courseId);
        return specialisation;
    }


    @Transactional
    public List<CourseSchedule> getScheduleByFaculty(String employeeId){
        List<CourseSchedule> courseScheduleList=courseScheduleDAO.getScheduleByFaculty(employeeId);
        return courseScheduleList;
    }

    @Transactional
    public List<Salary> viewSalaryHistoryByEmployee(String employeeId){
        List<Salary> salaryList=employeeSalaryDAO.findSalaryHistoryByEmail(employeeId);
        return salaryList;
    }

    @Transactional
    public List<Salary> viewSalaryHistoryByEmployeeForMonth(String employeeId, Integer month){
        List<Salary> salaryList=employeeSalaryDAO.findSalaryHistoryByIdandMonth(employeeId,month);
        return salaryList;
    }

    @Override
    public void registerTA(Long courseId,String studentId){
        Courses courses=courseDAO.findByCourseId(courseId);
        Students students=studentDAO.findByStudentId(studentId);
        CourseTA courseTA=new CourseTA();
        courseTA.setCourses(courses);
        courseTA.setStudents(students);
        courseTADAO.save(courseTA);
    }

    @Transactional
    public List<Students> viewTAByCourse(Long courseId){
        List<Students> studentsList=courseTADAO.viewTaByCourse(courseId);
        return studentsList;
    }

    @Transactional
    public List<StudentMessage> getStudentMessage(String employeeId){
        List<StudentMessage> studentMessages=studentMessageDAO.getStudentmessageById(employeeId);
        return studentMessages;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee= employeeDAO.getEmployeeByEmail(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return employee;
    }

    @Override
    public LoginResponse facultyLogin(LoginDTO credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        Employee employee= employeeDAO.getEmployeeByEmail(email);
        if (employee != null && employee.getActive()) {
            if (employee.isPasswordMatch(password)) {
                String token = tokenDAO.findByUsername(email);
                if (token != null) {
                    return new LoginResponse("User Already logged in", false, null);
                }
                TokenDTO tokens = jwtTokenProvider.generateToken(employee);
                return new LoginResponse("Login Successful", true, tokens);
            } else {
                return new LoginResponse("Password not matched", false, null);
            }
        } else {
            return new LoginResponse("Invalid User.", false, null);
        }
    }
    }

