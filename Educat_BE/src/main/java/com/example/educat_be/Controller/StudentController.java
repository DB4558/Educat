package com.example.educat_be.Controller;

import com.example.educat_be.ApacheKafka.KafkaProducer;
import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.CourseScheduleDTO;
import com.example.educat_be.DTO.GradeDTO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AuthenticationService;
import com.example.educat_be.Service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenDAO tokenDAO;

    @GetMapping("/viewStudentByEmail/{email}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Students> viewStudentByEmail(@PathVariable String email){
        Students students=studentService.viewStudentByEmail(email);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/viewCourseList")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Courses>> viewCourseList(){
        List<Courses> coursesList=studentService.viewCourseList();
        return new ResponseEntity<>(coursesList,HttpStatus.OK);

    }

    @GetMapping("/viewSpecialisationByCourse/{courseId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Specialisation> viewSpecialisationByCourse(@PathVariable Long courseId){
        Specialisation specialisation=studentService.viewSpecialisationByCourse(courseId);
        return new ResponseEntity<>(specialisation,HttpStatus.OK);
    }

    @GetMapping("/viewPrerequisiteListForCourse/{courseId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Courses>> viewPrerequisiteListForCourse(@PathVariable  Long courseId){
        List<Courses> coursesList=studentService.viewPrerequisiteListForCourse(courseId);
        return new ResponseEntity<>(coursesList,HttpStatus.OK);
    }

    @PostMapping("/selectCourse/{courseId}/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Student_Courses> selectCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        Student_Courses studentCourses=studentService.selectCourse(courseId,studentId);
        return new ResponseEntity<>(studentCourses,HttpStatus.OK);
    }

    @GetMapping("/viewCoursesByStudent/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Courses>> viewCoursesByStudent(@PathVariable Long studentId){
        List<Courses> coursesList=studentService.viewCoursesByStudent(studentId);
        return new ResponseEntity<>(coursesList,HttpStatus.OK);
    }

    @GetMapping("/viewScheduleByStudent/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<CourseScheduleDTO>> viewScheduleByStudent(@PathVariable Long studentId){
        List<CourseScheduleDTO> courseScheduleDTOS=studentService.viewScheduleByStudent(studentId);
        return new ResponseEntity<>(courseScheduleDTOS,HttpStatus.OK);
    }

    @GetMapping("/viewGrade/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<GradeDTO>> viewGrade(@PathVariable Long studentId){
        List<GradeDTO> gradeDTOS=studentService.viewGrade(studentId);
        return new ResponseEntity<>(gradeDTOS,HttpStatus.OK);
    }

    @PutMapping("/changePassword/{email}/{password}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Students> changePassword(@PathVariable String email, @PathVariable String password){
        Students students=studentService.changePassword(email, password);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/viewDueBills/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Bills>> viewDueBills(@PathVariable String studentId){
        List<Bills> bills=studentService.viewDueBills(studentId);
        return new ResponseEntity<>(bills,HttpStatus.OK);
    }

    @PostMapping("/makePayment/{studentId}/{billId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Student_Payment> makePayment(@PathVariable String studentId,@PathVariable Long billId,@RequestBody Student_Payment studentPayment){
        Student_Payment studentPayment1=studentService.makePayment(studentId,billId,studentPayment);
        return new ResponseEntity<>(studentPayment1,HttpStatus.OK);
    }

    @GetMapping("/viewPaidBills/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Bills>> viewPaidBills(@PathVariable String studentId){
        List<Bills> billsList=studentService.viewPaidBills(studentId);
        return new ResponseEntity<>(billsList,HttpStatus.OK);
    }

    @GetMapping("/viewPaymentsForaBillByStudent/{billId}/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Student_Payment>> viewPaymentsForaBillByStudent(@PathVariable Long billId,@PathVariable String studentId){
        List<Student_Payment> studentPayments=studentService.viewPaymentsForaBillByStudent(billId,studentId);
        return new ResponseEntity<>(studentPayments,HttpStatus.OK);
    }

    @GetMapping("/viewAllPaymentByStudent/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Student_Payment>> viewAllPaymentsByStudent(@PathVariable String studentId){
        List<Student_Payment> studentPayments=studentService.viewAllPaymentByStudent(studentId);
        return new ResponseEntity<>(studentPayments,HttpStatus.OK);
    }

    @GetMapping("/viewDueAmountForBill/{billId}/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Double> viewDueAmountForBill(@PathVariable Long billId,@PathVariable String studentId){
        Double dueamount=studentService.viewDueAmountForaBill(billId,studentId);
        return ResponseEntity.ok(dueamount);
    }

    @GetMapping("/viewHostel/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Hostel> viewHostel(@PathVariable String studentId){
        Hostel hostel=studentService.viewHostel(studentId);
        return new ResponseEntity<>(hostel,HttpStatus.OK);
    }

    @PostMapping("/makeRequest/{applicantId}/{recipientId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<SwapApplication> makeRequest(@PathVariable String applicantId,@PathVariable String recipientId,@RequestBody SwapApplication swapApplication){
        SwapApplication swapApplication1=studentService.makeRequest(applicantId,recipientId,swapApplication);
        return new ResponseEntity<>(swapApplication1,HttpStatus.CREATED);
    }

    @GetMapping("/getRequest/{recipientId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<SwapApplication>> getRequest(@PathVariable String recipientId){
        List<SwapApplication> swapApplicationList=studentService.getRequest(recipientId);
        return new ResponseEntity<>(swapApplicationList,HttpStatus.OK);
    }

    @PostMapping("/swapApplication/{applicationId}/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> SwapApplication(@PathVariable Long applicationId,@PathVariable String studentId,@RequestBody SwapApplication swapApplication){
        studentService.SwapApplication(applicationId,studentId,swapApplication);
        return ResponseEntity.ok("Hostel Exchanged successfully");
    }

    @GetMapping("/viewEligibleOffers/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Placement>> viewEligibleOffers(@PathVariable String studentId){
        List<Placement> placementList=studentService.viewEligibleOffers(studentId);
        return new ResponseEntity<>(placementList,HttpStatus.OK);
    }

    @PostMapping("/addApplication/{placementId}/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<StudentPlacement> addApplication(@PathVariable Long placementId,@PathVariable String studentId,@RequestBody StudentPlacement studentPlacement){
        StudentPlacement studentPlacement1=studentService.addApplication(placementId,studentId,studentPlacement);
        return new ResponseEntity<>(studentPlacement1,HttpStatus.CREATED);
    }

    @GetMapping("/viewApplication/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<StudentPlacement>> viewApplication(@PathVariable String studentId){
        List<StudentPlacement> placementList=studentService.viewApplication(studentId);
        return new ResponseEntity<>(placementList,HttpStatus.OK);
    }

    @GetMapping("/viewOfferedApplication/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<StudentPlacement>> viewOfferedApplication(@PathVariable String studentId){
        List<StudentPlacement> placementList=studentService.viewOfferedApplication(studentId);
        return new ResponseEntity<>(placementList,HttpStatus.OK);
    }

    @GetMapping("/acceptOffer/{studentId}/{placementId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> acceptOffer(@PathVariable String studentId,@PathVariable Long placementId){
        studentService.acceptOffer(studentId,placementId);
        return ResponseEntity.ok("Offer accepted");
    }

//    @PostMapping("/publish")
//    @PreAuthorize("hasAuthority('STUDENT')")
//    public ResponseEntity<String> publish(@RequestBody StudentMessage studentMessage){
//       kafkaProducer.sendMessage(studentMessage);
//       return ResponseEntity.ok("Message sent sucessfully");
//    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = studentService.studentLogin(credentials);

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
