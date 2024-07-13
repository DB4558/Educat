package com.example.educat_be.Controller;

import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AuthenticationService;
import com.example.educat_be.Service.FacultyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenDAO tokenDAO;

    @GetMapping("/selectCourse/{courseId}/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> selectCourse(@PathVariable Long courseId,@PathVariable String employeeId){
        Boolean status=facultyService.selectCourse(courseId,employeeId);
        if(status==true)
            return ResponseEntity.ok("Course selected sucessfully");
        else
            return ResponseEntity.ok("Cannot select course with same timeslot,Please select other courses");
        }

        @GetMapping("/viewCourses")
        @PreAuthorize("hasAuthority('EMPLOYEE')")
        public ResponseEntity<List<Courses>> viewCourses(){
        List<Courses> coursesList=facultyService.viewCourses();
        return new ResponseEntity<>(coursesList, HttpStatus.OK);
        }

    @GetMapping("/viewCoursesByFaculty/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Courses>> viewCoursesByFaculty(@PathVariable String employeeId){
        List<Courses> coursesList=facultyService.viewCoursesByFaculty(employeeId);
        return new ResponseEntity<>(coursesList, HttpStatus.OK);
    }

    @GetMapping("/viewStudentsByCourse/{courseId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewStudentsByCourse(@PathVariable Long courseId){
        List<Students> students=facultyService.viewStudentsByCourse(courseId);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/gradeStudent/{courseId}/{studentId}/{gradeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Student_Courses> gradeStudent(@PathVariable Long courseId, @PathVariable Long studentId,@PathVariable Long gradeId){
        Student_Courses studentCourses=facultyService.gradeStudent(courseId,studentId,gradeId);
        return new ResponseEntity<>(studentCourses,HttpStatus.OK);
    }

    @PostMapping("/gradeMultipleStudent/{courseId}/{gradeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> gradeMultipleStudent(@RequestBody Set<Long> studentId, @PathVariable Long courseId, @PathVariable Long gradeId){
        facultyService.gradeMultipleStudent(studentId,courseId,gradeId);
        return ResponseEntity.ok("Students graded successfully");
    }

    @GetMapping("/getSchedulesByCourse/{courseId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<CourseSchedule>> getSchedulesByCourse(@PathVariable Long courseId){
        List<CourseSchedule> courseSchedules=facultyService.getSchedulesByCourse(courseId);
        return new ResponseEntity<>(courseSchedules,HttpStatus.OK);
    }

    @GetMapping("/getSpecialisationByCourse/{courseId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Specialisation> getSpecialisationByCourse(@PathVariable Long courseId){
        Specialisation specialisation=facultyService.getSpecialisationByCourse(courseId);
        return new ResponseEntity<>(specialisation,HttpStatus.OK);
    }


    @GetMapping("/getScheduleByFaculty/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<CourseSchedule>> getScheduleByFaculty(@PathVariable String employeeId){
        List<CourseSchedule> courseSchedules=facultyService.getScheduleByFaculty(employeeId);
        return new ResponseEntity<>(courseSchedules,HttpStatus.OK);
    }

    @GetMapping("/viewSalaryHistoryByEmployee/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployee(@PathVariable String employeeId){
        List<Salary> salaryList=facultyService.viewSalaryHistoryByEmployee(employeeId);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/viewSalaryHistoryByEmployeeForMonth/{employeeId}/{month}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployeeForMonth(@PathVariable String employeeId, @PathVariable Integer month){
        List<Salary> salaryList=facultyService.viewSalaryHistoryByEmployeeForMonth(employeeId,month);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/registerTA/{courseId}/{studentId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> registerTA(@PathVariable Long courseId,@PathVariable String studentId){
        facultyService.registerTA(courseId,studentId);
        return ResponseEntity.ok("TA registered succesfully");
    }

    @GetMapping("/viewTAByCourse/{courseId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewTAByCourse(@PathVariable Long courseId){
        List<Students> students=facultyService.viewTAByCourse(courseId);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/getStudentMessage/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<StudentMessage>> getStudentMessage(@PathVariable String employeeId){
        List<StudentMessage> studentMessages=facultyService.getStudentMessage(employeeId);
        return new ResponseEntity<>(studentMessages,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = facultyService.facultyLogin(credentials);

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
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }






}
