package com.example.educat_be.Controller;


import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.*;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AdminService;
import com.example.educat_be.Service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    TokenDAO tokenDAO;

    @PostMapping("/addDomain")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Domain> saveDomain(@RequestBody Domain domain) {

            Domain savedomian = adminService.saveDomain(domain);
            return ResponseEntity.ok(savedomian);
    }
    @PostMapping("/addGrade")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Grade> addGrade(@RequestBody Grade grade) {

            Grade savedomian = adminService.addGrade(grade);
            return ResponseEntity.ok(savedomian);

    }

    @PutMapping("/modifyDomain/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Domain> modifyDomain(@PathVariable Long domainId, @RequestBody Domain domain) {
        try {
            Domain savedomain = adminService.modifyDomain(domainId, domain);
            return ResponseEntity.ok(savedomain);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/editGrade/{gradeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Grade> modifyDomain(@PathVariable Long gradeId, @RequestBody Grade grade) {

            Grade grade1=adminService.editGrade(gradeId,grade);
            return ResponseEntity.ok(grade1);

    }

    @GetMapping("/viewDomainById/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Domain> viewDomainById(@PathVariable Long domainId) {

            Domain domain = adminService.viewDomainById(domainId);
            return new ResponseEntity<>(domain, HttpStatus.OK);

    }

    @GetMapping("/viewGradeById/{gradeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Grade> viewGradeById(@PathVariable Long gradeId) {
        try {
            Grade grade=adminService.viewGradeById(gradeId);
            return new ResponseEntity<>(grade, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/viewDomainList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Domain>> viewDomain(){

            List<Domain> domainList=adminService.viewDomain();
            return new ResponseEntity<>(domainList,HttpStatus.OK);

    }

    @GetMapping("/viewGradeList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Grade>> viewGrade(){

            List<Grade> domainList=adminService.viewGrade();
            return new ResponseEntity<>(domainList,HttpStatus.OK);

    }

    @PostMapping("/addSpecialisation")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Specialisation> saveSpecialisation(@RequestBody Specialisation specialisation){

            Specialisation specialisation1=adminService.saveSpecialisation(specialisation);
            return ResponseEntity.ok(specialisation1);

    }

    @PutMapping("/editSpecialisation/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Specialisation> editSpecialisation(@PathVariable Long specialisationId,@RequestBody Specialisation specialisation){

            Specialisation specialisation1=adminService.editSpecialisation(specialisationId,specialisation);
            return ResponseEntity.ok(specialisation1);

    }

    @GetMapping("/viewSpecialisationById/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Specialisation> viewSpecialisationById(@PathVariable Long specialisationId) {

            Specialisation specialisation = adminService.viewSpecialisationById(specialisationId);
            return new ResponseEntity<>(specialisation, HttpStatus.OK);

    }

    @GetMapping("/viewSpecialisation")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Specialisation>> viewSpecialisation(){

           List <Specialisation> specialisation=adminService.viewSpecialisation();
            return new ResponseEntity<>(specialisation,HttpStatus.OK);

    }

    @DeleteMapping("/deleteSpecialisation/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSpecialisationById(@PathVariable Long specialisationId){

            adminService.deleteSpecialisation(specialisationId);
            return ResponseEntity.ok("Specialisation Deleted successfully");

    }

    @PostMapping("/addCourse")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Courses> saveCourse(@RequestBody Courses courses){

            Courses courses1=adminService.saveCourse(courses);
            return new ResponseEntity<>(courses1,HttpStatus.OK);

    }

    @PutMapping("/editCourse/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Courses> editCourses(@PathVariable Long courseId,@RequestBody Courses courses){

          Courses courses1=adminService.editCourses(courseId,courses);
          return new ResponseEntity<>(courses1,HttpStatus.OK);

    }

    @GetMapping("/viewCoursesByid/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Courses> viewCourseById(@PathVariable Long courseId){

            Courses courses=adminService.viewCourseById(courseId);
            return new ResponseEntity<>(courses,HttpStatus.OK);

    }

    @GetMapping("/viewCourseList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Courses>> viewCourseList(){

            List<Courses> courses=adminService.viewCourseList();
            return new ResponseEntity<>(courses,HttpStatus.OK);


    }

    @DeleteMapping("/deleteCourse/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId){

            adminService.deleteCourse(courseId);
            return ResponseEntity.ok("Course deleted successfully");

    }

    @PostMapping("/addprerequisite/{courseId}/{prerequisiteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CoursePrerequisite> addprerequisite(@PathVariable Long courseId,@PathVariable Long prerequisiteId,@RequestBody CoursePrerequisite coursePrerequisite){

            CoursePrerequisite coursePrerequisite1=adminService.addprerequisite(courseId,prerequisiteId,coursePrerequisite);
            return new ResponseEntity<>(coursePrerequisite1,HttpStatus.OK);

    }

    @GetMapping("/viewPrerequisiteList/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Courses>> viewPrerequisiteList(@PathVariable Long courseId){

            List<Courses> coursesList=adminService.viewPrerequisiteListByCourse(courseId);
            return new ResponseEntity<>(coursesList,HttpStatus.OK);

    }

    @PostMapping("/addCourseToSpecialisation/{courseId}/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Specialisation_Course> addCourseToSpecialisation(@PathVariable Long courseId,@PathVariable Long specialisationId){

            Specialisation_Course specialisationCourse=adminService.addCourseToSpecialisation(courseId,specialisationId);
            return new ResponseEntity<>(specialisationCourse,HttpStatus.OK);

    }

    @GetMapping("/viewCoursesBySpecialisation/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Courses>> viewCoursesBySpecialisation(@PathVariable Long specialisationId){

            List<Courses> coursesList=adminService.viewCoursesBySpecialisation(specialisationId);
            return new ResponseEntity<>(coursesList,HttpStatus.OK);

    }

    @PostMapping("/addCourseSchedule/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CourseSchedule> addCourseSchedule(@PathVariable Long courseId,@RequestBody CourseSchedule courseSchedule){

            CourseSchedule courseSchedule1=adminService.addCourseSchedule(courseId,courseSchedule);
            return new ResponseEntity<>(courseSchedule1,HttpStatus.OK);

    }

    @GetMapping("/viewScheduleByCourse/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CourseSchedule>> viewScheduleByCourse(@PathVariable Long courseId){

            List<CourseSchedule> courseScheduleList=adminService.viewScheduleByCourse(courseId);
            return new ResponseEntity<>(courseScheduleList,HttpStatus.OK);

    }

    @GetMapping("/viewScheduleById/{Id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CourseSchedule> viewScheduleById(@PathVariable Long Id){

            CourseSchedule courseScheduleList=adminService.viewCourseScheduleById(Id);
            return new ResponseEntity<>(courseScheduleList,HttpStatus.OK);

    }

    @PutMapping("/editCourseSchedule/{Id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CourseSchedule> editCourseSchedule(@PathVariable Long Id,@RequestBody CourseSchedule courseSchedule){

            CourseSchedule courseSchedule1=adminService.editCourseSchedule(Id,courseSchedule);
            return new ResponseEntity<>(courseSchedule1,HttpStatus.OK);

    }

    @DeleteMapping("/deleteCourseSchedule/{scheduleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteCourseSchedule(@PathVariable Long scheduleId){

            adminService.deleteCourseSchedule(scheduleId);
            return ResponseEntity.ok("Schedule Deleted successfully");

    }
    @PostMapping("/addCoursetoDomain/{courseId}/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CourseDomain> addCourseToDomain(@PathVariable Long courseId,@PathVariable Long domainId){

            CourseDomain courseDomain1=adminService.addCourseToDomain(courseId,domainId);
            return new ResponseEntity<>(courseDomain1,HttpStatus.OK);

    }

    @GetMapping("/viewCoursesByDomain/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Courses>> viewCoursesByDomain(@PathVariable Long domainId){
        List<Courses> coursesList=adminService.viewCoursesByDomain(domainId);
        return new ResponseEntity<>(coursesList,HttpStatus.OK);
    }


    @GetMapping("/getCourseandScheduleByDomain/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CourseScheduleDTO>> getCourseandScheduleByDomain(@PathVariable Long domainId) {
        List<CourseScheduleDTO> courseDetails = adminService.findCourseDetailsandScheduleByDomainId(domainId);
        if (courseDetails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courseDetails);
    }

    @PostMapping("/addStudent/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Students> addStudent(@RequestBody Students students,@PathVariable Long domainId){
        Students students1=adminService.addStudent(students,domainId);
        return new ResponseEntity<>(students1,HttpStatus.OK);
    }

    @PutMapping("/editStudent/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Students> editStudent(@PathVariable String studentId,@RequestBody Students students){
        Students students1=adminService.editStudent(studentId,students);
        return new ResponseEntity<>(students1,HttpStatus.OK);
    }

    @GetMapping("/viewStudentById/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Students> viewStudentById(@PathVariable String studentId){
       Students students=adminService.viewStudentById(studentId);
       return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/viewStudents")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Students>> viewStudent(){
        List<Students> studentsList=adminService.viewStudent();
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }


    @GetMapping("/viewStudentByDomain/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Students>> viewStudentByDomain(@PathVariable Long domainId){
        List<Students> studentsList=adminService.viewStudentsByDomain(domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewStudentsByCourse/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Students>> viewStudentsByCourse(@PathVariable Long courseId){
        List<Students> studentsList=adminService.viewStudentsByCourse(courseId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @PostMapping("/addDepartment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Departments> addDepartment(@RequestBody Departments departments){
        Departments departments1=adminService.addDepartment(departments);
        return new ResponseEntity<>(departments1,HttpStatus.CREATED);
    }

    @PutMapping("/editDepartment/{departmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Departments> editDepartment(@PathVariable Long departmentId,@RequestBody Departments departments){
        Departments departments1=adminService.editDepartment(departmentId,departments);
        return new ResponseEntity<>(departments1,HttpStatus.OK);
    }

    @GetMapping("/viewDepartmentById/{departmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Departments> viewDepartmentById(@PathVariable Long departmentId){
        Departments departments=adminService.viewDepartmentById(departmentId);
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }

    @GetMapping("/viewDepartments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Departments>> viewDepartments(){
        List<Departments> departmentsList=adminService.viewDepartments();
        return new ResponseEntity<>(departmentsList,HttpStatus.OK);
    }

    @DeleteMapping("/deleteDepartment/{departmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId){
        adminService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @PostMapping("/addEmployee/{departmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee>  addEmployee(@PathVariable Long departmentId,@RequestBody Employee employee){
        Employee saveEmployee=adminService.addEmployee(departmentId,employee);
        return new ResponseEntity<>(saveEmployee,HttpStatus.CREATED);
    }

    @PutMapping("/editEmployee/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> editEmployee(@PathVariable String employeeId,@RequestBody Employee employee){
        Employee employee1=adminService.editEmployee(employeeId,employee);
        return new ResponseEntity<>(employee1,HttpStatus.OK);
    }

    @GetMapping("/viewEmployeeById/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> viewEmployeeById(@PathVariable String employeeId){
        Employee employee=adminService.viewEmployeeById(employeeId);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @GetMapping("/viewEmployee")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Employee>> viewEmployee(){
        List<Employee> employeeList=adminService.viewEmployee();
        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }

    @GetMapping("/viewEmployeeByDepartment/{departmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Employee>> viewEmployeeByDepartment(@PathVariable Long departmentId){
        List<Employee> employeeList=adminService.viewEmployeeByDepartment(departmentId);
        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }


    @GetMapping("/computeSpecialisation/{Id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> computeSpecialisation(@PathVariable Long Id){
        adminService.computeSpecialisation(Id);
        return ResponseEntity.ok("Specialisation computed successfully");
    }

    @GetMapping("/viewSpecialisationAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SpecialisationAll>> viewSpecialisationAll(){
        List<SpecialisationAll> studentsList=adminService.viewSpecialisationAll();
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }




    @GetMapping("/viewStudentsBySpecialisation/{specialisationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Students>> viewStudentsBySpecialisation(@PathVariable Long specialisationId){
        List<Students> studentsList=adminService.viewStudentsBySpecialisation(specialisationId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }


    @DeleteMapping("/removeCourseFromDomain/{courseId}/{domainId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> removeCourseFromDomain(@PathVariable Long courseId,@PathVariable Long domainId){
        adminService.removeCourseFromDomain(courseId,domainId);
        return ResponseEntity.ok("Removed Course from respective Domain");
    }

    @DeleteMapping("/removeCoursePrerequisite/{courseId}/{prerequisiteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> removePrerequisiteForCourse(@PathVariable  Long courseId,@PathVariable Long prerequisiteId){
        adminService.removePrerequisiteForCourse(courseId,prerequisiteId);
        return ResponseEntity.ok(("Prerequisite for the course removed"));
    }

    @PutMapping("/inactivateStudent/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> inactivateStudent(@PathVariable String studentId){
        adminService.inactivateStudent(studentId);
        return ResponseEntity.ok("Student inactivated");
    }
    @PutMapping("/activateStudent/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> activateStudent(@PathVariable String studentId){
        adminService.activateStudent(studentId);
        return ResponseEntity.ok("Student activated");
    }

    @PutMapping("/inactivateEmployee/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> inactivateEmployee(@PathVariable String employeeId){
        adminService.inactivateEmployee(employeeId);
        return ResponseEntity.ok("Employee inactivated");
    }

    @PutMapping("/activateEmployee/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> activateEmployee(@PathVariable String employeeId){
        adminService.activateEmployee(employeeId);
        return ResponseEntity.ok("Employee inactivated");
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
        Admin admin1=adminService.addAdmin(admin);
        return new ResponseEntity<>(admin1,HttpStatus.OK);
    }

    @GetMapping("/viewAdmin/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Admin> viewAdmin(@PathVariable String email){
        Admin admin1=adminService.viewAdmin(email);
        return new ResponseEntity<>(admin1,HttpStatus.OK);
    }

    @PutMapping("/changePassword/{email}/{password}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Admin> changePassword(@PathVariable String email,@PathVariable String password){
        Admin admin=adminService.changePassword(email, password);
        return new ResponseEntity<>(admin,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = adminService.adminLogin(credentials);

        if (loginResponse.getStatus()) {
            TokenDTO tokens = loginResponse.getTokenDTO();
            Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.getRefreshtoken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setDomain("localhost");
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(24 * 60 * 60);

            response.addCookie(refreshTokenCookie);



            return ResponseEntity
                    .status(loginResponse.getStatus() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                    .body(new FinalResponse(loginResponse.getMessage(), loginResponse.getStatus(), loginResponse.getTokenDTO().getAccesstoken()));

        }
        else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new FinalResponse(
                            loginResponse.getMessage(),
                            false,
                            null
                    ));
        }
        }


    @Transactional
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
        }

        String newAccessToken = authenticationService.refreshAccessToken(refreshToken);
        if (newAccessToken != null) {
            tokenDAO.updateAccessToken(newAccessToken,refreshToken);
            return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }

    private String extractRefreshToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .peek(cookie -> System.out.println("Received cookie: " + cookie.getName() + " Value: " + cookie.getValue()))
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    @GetMapping("/counts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CountDTO> getCounts(){
        CountDTO countDTO=adminService.getCounts();
        return new ResponseEntity<>(countDTO,HttpStatus.OK);
    }

    @GetMapping("/logout/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> logout(@PathVariable String email){
        adminService.Logout(email);
        return ResponseEntity.ok("Logout succeed");
    }


}
