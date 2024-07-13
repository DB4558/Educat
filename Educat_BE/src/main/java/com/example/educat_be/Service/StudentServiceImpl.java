package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.AuthenticationAndAuthorisation.ServiceDetails;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.CourseScheduleDTO;
import com.example.educat_be.DTO.GradeDTO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService, ServiceDetails {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    SpecialisationCourseDAO specialisationCourseDAO;

    @Autowired
    CoursePrerequisiteDAO coursePrerequisiteDAO;

    @Autowired
    Student_CoursesDAO studentCoursesDAO;

    @Autowired
    CourseScheduleDAO courseScheduleDAO;

    @Autowired
    StudentBillsDAO studentBillsDAO;

    @Autowired
    BillDAO billDAO;

    @Autowired
    StudentPayementDAO studentPayementDAO;

    @Autowired
    HostelDAO hostelDAO;

    @Autowired
    SwapApplicationDAO swapApplicationDAO;

    @Autowired
    PlacementOffer placementOffer;

    @Autowired
    StudentPlacementDAO studentPlacementDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    StudentMessageDAO studentMessageDAO;

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Students viewStudentByEmail(String email){
        Students students=studentDAO.findByEmail(email);
        return students;
    }

    @Transactional
    public List<Courses> viewCourseList(){
        List<Courses> courses=courseDAO.findAll();
        return courses;
    }

    @Transactional
    public Specialisation viewSpecialisationByCourse(Long courseId){
        Specialisation specialisation=specialisationCourseDAO.findSpecialisationByCourse(courseId);
        return specialisation;

    }

    @Transactional
    public List<Courses> viewPrerequisiteListForCourse(Long courseId){
        List<Courses> coursesList=coursePrerequisiteDAO.findPrerequisiteList(courseId);
        return coursesList;
    }

    @Override
    public Student_Courses selectCourse(Long courseId,Long studentId){
        Courses courses=courseDAO.findByCourseId(courseId);
        Students students=studentDAO.findStudentById(studentId);
        Student_Courses studentCourses=new Student_Courses();
        Integer selectedCount=studentCoursesDAO.countCourseByStudent(studentId);
        if(selectedCount==null)
            selectedCount=0;
        if(selectedCount<6 && studentCoursesDAO.selectCourse(courseId,studentId)==true) {
            studentCourses.setCourse(courses);
            studentCourses.setStudent(students);
            return studentCoursesDAO.save(studentCourses);
        }
        else
            return null;
    }

    @Transactional
    public List<Courses> viewCoursesByStudent(Long studentId){
        List<Courses> coursesList=studentCoursesDAO.viewCoursesByStudent(studentId);
        return coursesList;
    }

    @Transactional
    public List<CourseScheduleDTO> viewScheduleByStudent(Long studentId){
        List<Object[]> results = courseScheduleDAO.viewScheduleByStudent(studentId);
        return results.stream().map(result -> new CourseScheduleDTO(
                (String) result[0],   // courseCode
                (String) result[1],   // name
                (Integer) result[2],  // credits
                (Integer) result[3],  // capacity
                (Time) result[4],     // time
                (DayOfWeek) result[5],// day
                (String) result[6]    // room
        )).collect(Collectors.toList());
    }

    @Transactional
    public List<GradeDTO> viewGrade(Long studentId){
        List<Object[]> results = studentCoursesDAO.viewGrade(studentId);
        return results.stream().map(result -> new GradeDTO(
                (String) result[0],   // courseCode
                (String) result[1],   // name
                (String) result[2],
                (Double) result[3]
        )).collect(Collectors.toList());
    }

    @Override
    public Students changePassword(String email,String password){
        Students students=studentDAO.findByEmail(email);
        students.setPassword(password);
        return studentDAO.save(students);
    }

    @Transactional
    public List<Bills> viewDueBills(String studentId){
        List<Bills> billsList=studentBillsDAO.viewDueBills(studentId);
        return billsList;
    }

    @Override
    public Student_Payment makePayment(String studentId,Long billId,Student_Payment studentPayment){
        Bills bill=billDAO.findBillById(billId);
        studentPayment.setBills(bill);
        Students students=studentDAO.findByStudentId(studentId);
        studentPayment.setStudents(students);
        if(studentPayementDAO.willPay(billId,studentId)) {
            studentPayementDAO.save(studentPayment);
            return studentPayment;
        }
        else
            return null;

    }


    @Transactional
   public List<Bills> viewPaidBills(String studentId){
        List<Bills> billsList=studentBillsDAO.viewPaidBills(studentId);
        return billsList;
    }

    @Transactional
    public List<Student_Payment> viewPaymentsForaBillByStudent(Long billId,String studentId){
        List<Student_Payment> studentPayments=studentPayementDAO.viewPaymentsForaBillByStudent(billId,studentId);
        return studentPayments;
    }

    @Transactional
    public List<Student_Payment> viewAllPaymentByStudent(String studentId){
        List<Student_Payment> studentPayments=studentPayementDAO.viewAllPaymentsByStudent(studentId);
        return studentPayments;
    }

    @Transactional
    public Double viewDueAmountForaBill(Long billId,String studentId){
        Double amount=billDAO.viewAmountForBill(billId);
        Double paidamount=0.0;
         paidamount=studentPayementDAO.getdueAmount(billId,studentId);
        if(paidamount==null)
            paidamount=0.0;
        return amount-paidamount;
    }

    @Transactional
    public Hostel viewHostel(String studentId){
        Hostel hostel=hostelDAO.viewHostel(studentId);
        return hostel;
    }

    @Override
    public SwapApplication makeRequest(String applicantId,String recipientId,SwapApplication swapApplication){
        Students students=studentDAO.findByStudentId(applicantId);
        swapApplication.setApplicant(students);
        Students students1=studentDAO.findByStudentId(recipientId);
        swapApplication.setRecipient(students1);
        swapApplication.setStatus("PENDING");
        swapApplicationDAO.save(swapApplication);
        return swapApplication;
    }

    @Transactional
    public List<SwapApplication> getRequest(String recipientId){
        List<SwapApplication> swapApplicationList=swapApplicationDAO.getRequest(recipientId);
        return swapApplicationList;
    }

    @Override
    public void SwapApplication(Long applicationId,String recipientId,SwapApplication swapApplication){
        SwapApplication swapApplication1=swapApplicationDAO.findByApplicationId(applicationId);
        swapApplication1.setStatus(swapApplication.getStatus());
        if(swapApplication.getRecipientMessage()!=null && !swapApplication.getRecipientMessage().isEmpty())
            swapApplication1.setRecipientMessage(swapApplication.getRecipientMessage());
        swapApplicationDAO.save(swapApplication1);
        String status="ACCEPT";
        if(swapApplication.getStatus().matches(status)){
        String applicantId=swapApplicationDAO.getApplicantId(applicationId,recipientId);
        Hostel applicationHostel=hostelDAO.viewHostel(applicantId);
        Hostel recipientHostel=hostelDAO.viewHostel(recipientId);
        String room=applicationHostel.getRoom();
        String floor=applicationHostel.getFloor();
        String name=applicationHostel.getName();

        applicationHostel.setName(recipientHostel.getName());
        applicationHostel.setFloor(recipientHostel.getFloor());
        applicationHostel.setRoom(recipientHostel.getRoom());
        recipientHostel.setRoom(room);
        recipientHostel.setName(name);
        recipientHostel.setFloor(floor);
        hostelDAO.save(recipientHostel);
        hostelDAO.save(applicationHostel);
        }
    }

    @Transactional
   public  List<Placement> viewEligibleOffers(String studentId){
        List<Placement> offers=studentDAO.getEligibleOffers(studentId);
        return offers;
   }

   @Override
   public StudentPlacement addApplication(Long placementId,String studentId,StudentPlacement studentPlacement){
        studentPlacement.setAcceptance("PENDING");
        Placement placement=placementOffer.getPlacementById(placementId);
        studentPlacement.setPlacement(placement);
        Students students=studentDAO.findByStudentId(studentId);
        studentPlacement.setStudents(students);
        return studentPlacementDAO.save(studentPlacement);

   }

   @Transactional
   public List<StudentPlacement> viewApplication(String studentId){
        List<StudentPlacement> studentPlacements=studentPlacementDAO.findApplicationById(studentId);
        return studentPlacements;
   }

    @Transactional
    public List<StudentPlacement> viewOfferedApplication(String studentId){
        List<StudentPlacement> studentPlacements=studentPlacementDAO.findOfferedApplication(studentId);
        return studentPlacements;
    }

    @Override
    public void acceptOffer(String studentId,Long placementId){
        Students students=studentDAO.findByStudentId(studentId);
        Placement placement=placementOffer.getPlacementById(placementId);
        students.setPlacement(placement);
        studentDAO.save(students);
    }

    @Override
    public void saveStudentMessage(StudentMessage studentMessage){
        studentMessageDAO.save(studentMessage);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Students students= studentDAO.findByEmail(username);
        if (students == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return students;
    }

    @Override
    public LoginResponse studentLogin(LoginDTO credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        Students students=studentDAO.findByEmail(email);
        if (students != null && students.getActive()) {
            if (students.isPasswordMatch(password)) {
                String token = tokenDAO.findByUsername(email);
                if (token != null) {
                    return new LoginResponse("User Already logged in", false, null);
                }
                TokenDTO tokens = jwtTokenProvider.generateToken(students);
                return new LoginResponse("Login Successful", true, tokens);
            } else {
                return new LoginResponse("Password not matched", false, null);
            }
        } else {
            return new LoginResponse("Invalid User.", false, null);
        }
    }
}

