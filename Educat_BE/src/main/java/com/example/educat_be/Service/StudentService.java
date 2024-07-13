package com.example.educat_be.Service;

import com.example.educat_be.DTO.CourseScheduleDTO;
import com.example.educat_be.DTO.GradeDTO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface StudentService {

    Students viewStudentByEmail(String email);

    List<Courses> viewCourseList();

    List<Courses> viewPrerequisiteListForCourse(Long courseId);

    Specialisation viewSpecialisationByCourse(Long courseId);

    Student_Courses selectCourse(Long courseId,Long studentId);
    List<Courses> viewCoursesByStudent(Long studentId);

    List<CourseScheduleDTO> viewScheduleByStudent(Long studentId);

    List<GradeDTO> viewGrade(Long studentId);

    Students changePassword(String studentId,String password);

    List<Bills> viewDueBills(String studentId);

    Student_Payment makePayment(String studentId, Long billId,Student_Payment studentPayment);

    List<Bills> viewPaidBills(String studentId);

    List<Student_Payment> viewPaymentsForaBillByStudent(Long billId,String studentId);

    List<Student_Payment> viewAllPaymentByStudent(String studentId);

    Double viewDueAmountForaBill(Long billId,String studentId);

    Hostel viewHostel(String studentId);

    SwapApplication makeRequest(String applicantId,String recipientId,SwapApplication swapApplication);

    List<SwapApplication> getRequest(String recipientId);

    void SwapApplication(Long applicationId,String recipientId,SwapApplication swapApplication);

    List<Placement> viewEligibleOffers(String studentId);

    StudentPlacement addApplication(Long placementId,String studentId,StudentPlacement studentPlacement);

    List<StudentPlacement> viewApplication(String studentId);

    List<StudentPlacement> viewOfferedApplication(String studentId);

    void acceptOffer(String studentId,Long placementId);

     void saveStudentMessage(StudentMessage studentMessage);

     LoginResponse studentLogin(LoginDTO loginDTO);
}
