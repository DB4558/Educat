package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.AuthenticationAndAuthorisation.ServiceDetails;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.*;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Exception.CapacityExceededException;
import com.example.educat_be.Response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class AdminServiceImpl implements AdminService, ServiceDetails {

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    SpecialisationDAO specialisationDAO;

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    CoursePrerequisiteDAO coursePrerequisiteDAO;

    @Autowired
    SpecialisationCourseDAO specialisationCourseDAO;

    @Autowired
    CourseScheduleDAO courseScheduleDAO;

    @Autowired
    CourseDomainDAO courseDomainDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    Student_CoursesDAO studentCoursesDAO;

    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    AdminDAO adminDAO;

    @Autowired
    CourseTADAO courseTADAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    GradeDAO gradeDAO;

    @Override
    public Domain saveDomain(Domain domain){
        return domainDAO.save(domain);
    }

    @Override
    public Domain modifyDomain(Long domainId,Domain domain){
        Optional<Domain> optionalDomain=domainDAO.findById(domainId);
        Domain newdomain=optionalDomain.orElse(null);
        if(domain.getProgram()!=null && !domain.getProgram().isEmpty()){
            newdomain.setProgram(domain.getProgram());
        }
        if(domain.getBatch()!=null && !domain.getBatch().isEmpty()){
            newdomain.setBatch(domain.getBatch());
        }
        if(domain.getCapacity()!=null){
            newdomain.setCapacity(domain.getCapacity());
        }
        if(domain.getQualification()!=null && !domain.getQualification().isEmpty()){
            newdomain.setQualification(domain.getQualification());
        }
        return domainDAO.save(newdomain);
    }

    @Override
    public Grade editGrade(Long gradeId, Grade grade) {
        Optional<Grade> optionalGrade = gradeDAO.findById(gradeId);
        Grade existingGrade = optionalGrade.orElse(null);

        if (existingGrade == null) {
            throw new RuntimeException("Grade not found with id: " + gradeId);
        }

        if (grade.getLetterGrade() != null && !grade.getLetterGrade().isEmpty()) {
            existingGrade.setLetterGrade(grade.getLetterGrade());
        }
        if (grade.getGradePoints() != null) {
            existingGrade.setGradePoints(grade.getGradePoints());
        }
        if (grade.getComments() != null) {
            existingGrade.setComments(grade.getComments());
        }

        return gradeDAO.save(existingGrade);
    }


    @Transactional
    public Domain viewDomainById(Long domainId){
        return domainDAO.findByDomainId(domainId);
    }

    @Transactional
    public List<Domain> viewDomain(){
        return domainDAO.findAll();
    }

    @Transactional
    public Grade viewGradeById(Long gradeId){

        Optional<Grade> grade=gradeDAO.findById(gradeId);
        Grade grade1=grade.orElse(null);
        return grade1;
    }

    @Transactional
    public List<Grade> viewGrade(){

        return gradeDAO.findAll();
    }


    @Override
    public Specialisation saveSpecialisation(Specialisation specialisation){
            return specialisationDAO.save(specialisation);

    }

    @Override
    public Specialisation editSpecialisation(Long specialisationId,Specialisation specialisation) {
        Optional<Specialisation> optionalSpecialisation=specialisationDAO.findById(specialisationId);
        Specialisation specialisation1=optionalSpecialisation.orElse(null);
        if(specialisation.getCode()!=null && !specialisation.getCode().isEmpty())
            specialisation1.setCode(specialisation.getCode());
        if(specialisation.getName()!=null && !specialisation.getName().isEmpty())
            specialisation1.setName(specialisation.getName());
        if(specialisation.getDescription()!=null && !specialisation.getDescription().isEmpty())
            specialisation1.setDescription(specialisation.getDescription());
        if(specialisation.getCredits_required()!=null)
            specialisation1.setCredits_required(specialisation.getCredits_required());
        if(specialisation.getYear()!=null)
            specialisation1.setYear(specialisation.getYear());
        return specialisationDAO.save(specialisation1);
    }

    @Transactional
    public Specialisation viewSpecialisationById(Long specialisationId){
        return specialisationDAO.findBySpecialisationId(specialisationId);
    }

    @Transactional
    public List<Specialisation> viewSpecialisation() {
        return specialisationDAO.findAll();
    }


    @Transactional
    public void deleteSpecialisation(Long specialisationId){
        List<Specialisation_Course> specialisationCourses=specialisationCourseDAO.findBySpecialisationId(specialisationId);
        specialisationCourseDAO.deleteAll(specialisationCourses);
        specialisationCourseDAO.flush();
        studentDAO.updateSpecialisation(specialisationId);
        studentDAO.flush();
        specialisationDAO.deleteSpecialisationById(specialisationId);
    }

    @Override
    public Courses saveCourse(Courses courses) {
        return courseDAO.save(courses);
    }

    @Transactional
    public void deleteCourse(Long courseId){
        List<CoursePrerequisite> coursePrerequisites=coursePrerequisiteDAO.findByCourseId(courseId);
        if(coursePrerequisites!=null)
            coursePrerequisiteDAO.deleteAll(coursePrerequisites);
        coursePrerequisiteDAO.flush();
        List<Specialisation_Course> specialisationCourses=specialisationCourseDAO.findByCourseId(courseId);
        if(specialisationCourses!=null)
            specialisationCourseDAO.deleteAll(specialisationCourses);
        specialisationCourseDAO.flush();
        List<CourseSchedule> courseSchedules=courseScheduleDAO.findScheduleByCourse(courseId);
        if(courseSchedules!=null)
            courseScheduleDAO.deleteAll(courseSchedules);
        courseScheduleDAO.flush();
        List<CourseDomain> courseDomains=courseDomainDAO.findDomainByCourse(courseId);
        if(courseDomains!=null)
            courseDomainDAO.deleteAll(courseDomains);
        courseDomainDAO.flush();
        List<Student_Courses> studentCourses=studentCoursesDAO.findStudentByCourse(courseId);
        if(studentCourses!=null)
            studentCoursesDAO.deleteAll(studentCourses);
        studentCoursesDAO.flush();

        List<CourseTA> courseTAS=courseTADAO.getTAByCourse(courseId);
        if(courseTAS!=null)
            courseTADAO.deleteAll(courseTAS);
        courseTADAO.flush();
        courseDAO.deleteCourseById(courseId);
    }

    @Override
    public Courses editCourses(Long courseId,Courses courses){
        Optional<Courses> optionalcourses=courseDAO.findById(courseId);
        Courses savecourse=optionalcourses.orElse(null);

        if(courses.getCourseCode()!=null && !courses.getCourseCode().isEmpty())
            savecourse.setCourseCode(courses.getCourseCode());
        if(courses.getName()!=null && !courses.getName().isEmpty())
            savecourse.setName(courses.getName());

        if(courses.getDescription()!=null && !courses.getDescription().isEmpty())
            savecourse.setDescription(courses.getDescription());
        if(courses.getCapacity()!=null)
            savecourse.setCapacity(courses.getCapacity());
        if(courses.getCredits()!=null)
            savecourse.setCredits(courses.getCredits());
        if(courses.getTerm()!=null && !courses.getTerm().isEmpty())
            savecourse.setTerm(courses.getTerm());
        if(courses.getYear()!=null)
            savecourse.setYear(courses.getYear());

        return courseDAO.save(savecourse);
    }

    @Transactional
    public Courses viewCourseById(Long courseId){
       Courses courses=courseDAO.findByCourseId(courseId);
       return courses;
    }

    @Transactional
    public List<Courses> viewCourseList(){
        List<Courses> coursesList=courseDAO.findAll();
        return coursesList;
    }

    @Override
    public CoursePrerequisite addprerequisite(Long courseId,Long prerequisiteId,CoursePrerequisite coursePrerequisite){
       Courses courses=courseDAO.findByCourseId(courseId);
       coursePrerequisite.setCourses(courses);
       Courses prerequisite=courseDAO.findByCourseId(prerequisiteId);
       coursePrerequisite.setPrerequisite(prerequisite);
       return coursePrerequisiteDAO.save(coursePrerequisite);
    }

    @Transactional
    public List<Courses> viewPrerequisiteListByCourse(Long courseId){
        List<Courses> coursePrerequisites=coursePrerequisiteDAO.findPrerequisiteList(courseId);
        return coursePrerequisites;

    }

    @Override
    public Specialisation_Course addCourseToSpecialisation(Long courseId,Long specialisationId){
        Specialisation_Course specialisationCourse=new Specialisation_Course();
        Courses courses=courseDAO.findByCourseId(courseId);
        specialisationCourse.setCourses(courses);
        Specialisation specialisation=specialisationDAO.findBySpecialisationId(specialisationId);
        specialisationCourse.setSpecialisation(specialisation);
        return specialisationCourseDAO.save(specialisationCourse);
    }

    @Transactional
    public List<Courses> viewCoursesBySpecialisation(Long specialisationId){
        List<Courses> coursesList=specialisationCourseDAO.findCoursesBySpecialisationId(specialisationId);
        return coursesList;
    }

    @Override
    public CourseSchedule addCourseSchedule(Long courseId,CourseSchedule courseSchedule){
        Courses courses=courseDAO.findByCourseId(courseId);
        courseSchedule.setCourses(courses);
        return courseScheduleDAO.save(courseSchedule);
    }

    @Transactional
    public List<CourseSchedule> viewScheduleByCourse(Long courseId){
        List<CourseSchedule> courseSchedules=courseScheduleDAO.findScheduleByCourse(courseId);
        return courseSchedules;
    }

    @Override
    public CourseSchedule editCourseSchedule(Long Id,CourseSchedule courseSchedule){
        Optional<CourseSchedule> optionalCourseSchedule=courseScheduleDAO.findById(Id);
        CourseSchedule courseSchedule1=optionalCourseSchedule.orElse(null);
        if(courseSchedule.getTime()!=null)
            courseSchedule1.setTime(courseSchedule.getTime());
        if(courseSchedule.getDay()!=null)
            courseSchedule1.setDay(courseSchedule.getDay());
        if(courseSchedule.getRoom()!=null && !courseSchedule.getRoom().isEmpty())
            courseSchedule1.setRoom(courseSchedule.getRoom());
        return courseScheduleDAO.save(courseSchedule1);
    }

    @Transactional
    public void deleteCourseSchedule(Long Id){
        courseScheduleDAO.deleteByScheduleId(Id);
    }

    @Override
    public CourseDomain addCourseToDomain(Long courseId,Long domainId){
        CourseDomain courseDomain=new CourseDomain();
        Courses courses=courseDAO.findByCourseId(courseId);
        courseDomain.setCourses(courses);
        Domain domain=domainDAO.findByDomainId(domainId);
        courseDomain.setDomain(domain);
        return courseDomainDAO.save(courseDomain);
    }

    @Transactional
    public List<Courses> viewCoursesByDomain(Long domainId){

            List<Courses> courseDomains=courseDomainDAO.findCourseByDomain(domainId);
            return courseDomains;

    }

    @Transactional
    public List<CourseScheduleDTO> findCourseDetailsandScheduleByDomainId(Long domainId) {
        List<Object[]> results = courseScheduleDAO.findCourseDetailsandScheduleByDomainId(domainId);
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

    private String generateUniqueRollNumber(Domain domain, String batch) {
        if (batch == null || batch.trim().isEmpty()) return null;

        String prefix;
        switch (domain.getProgram()) {
            case "MTech CSE":
            case "MTech ECE":
                prefix = "MT" + batch;
                break;
            case "MS CSE":
            case "MS ECE":
                prefix = "MS" + batch;
                break;
            case "IMTech CSE":
            case "IMTech ECE":
                prefix = "IMT" + batch;
                break;
            default:
                return null;
        }


        int count = studentDAO.countByRollNumberStartingWith(prefix) + 1;
        System.out.println(prefix + String.format("%03d", count));
        return prefix + String.format("%03d", count);
    }



    @Override
    public Students addStudent(Students students,Long domainId){
        Domain domain=domainDAO.findByDomainId(domainId);
        students.setDomain(domain);
        students.setRoll_number(generateUniqueRollNumber(students.getDomain(), students.getDomain().getBatch()));
        students.setActive(true);
        students.setPassword("student123");
        Float defaultcgpa=0.0F;
        if(students.getCgpa()==null)
            students.setCgpa(defaultcgpa);
        return studentDAO.save(students);

    }

    @Override
    public Students editStudent(String student_id,Students students){
        Students saveStudent=studentDAO.findByStudentId(student_id);

        if(students.getFirst_name()!=null && !students.getFirst_name().isEmpty())
            saveStudent.setFirst_name(students.getFirst_name());
        if(students.getLast_name()!=null && !students.getLast_name().isEmpty())
            saveStudent.setLast_name(students.getLast_name());
        if(students.getCgpa()!=null)
            saveStudent.setCgpa(students.getCgpa());
        if(students.getGraduation_year()!=null)
            saveStudent.setGraduation_year(students.getGraduation_year());
        if(students.getTotal_credits()!=null)
            saveStudent.setTotal_credits(students.getTotal_credits());
        if(students.getPhoto()!=null && !students.getPhoto().isEmpty())
            saveStudent.setPhoto(students.getPhoto());
        if(students.getDomain()!=null){
            Domain domain=domainDAO.findByDomainId(students.getDomain().getDomainId());
            saveStudent.setDomain(domain);}
        return studentDAO.save(saveStudent);
    }

    @Transactional
    public Students viewStudentById(String studentId){
        Students students=studentDAO.findByStudentId(studentId);
        return students;
    }

    @Transactional
    public List<Students> viewStudent(){
        List<Students> students=studentDAO.findAll();
        return students;
    }


    @Transactional
    public List<Students> viewStudentsByDomain(Long domainId){
        List<Students> studentsList=studentDAO.findStudentByDomainId(domainId);
        return studentsList;
    }

    @Transactional
    public List<Students> viewStudentsByCourse(Long courseId){
        List<Students> studentsList=studentCoursesDAO.viewStudentByCourse(courseId);
        return studentsList;
    }

    @Override
    public Departments addDepartment(Departments departments){
        return departmentDAO.save(departments);
    }

    @Override
    public Departments editDepartment(Long departmentId,Departments departments){
        Optional<Departments> departments1=departmentDAO.findById(departmentId);
        Departments saveDepartment=departments1.orElse(null);

        if(departments.getName()!=null && !departments.getName().isEmpty())
            saveDepartment.setName(departments.getName());
        if(departments.getCapacity()!=null)
            saveDepartment.setCapacity(departments.getCapacity());
        return departmentDAO.save(saveDepartment);
    }

    @Transactional
    public Departments viewDepartmentById(Long departmentId){
        Departments departments=departmentDAO.findByDepartmentId(departmentId);
        return departments;
    }

    @Transactional
    public List<Departments> viewDepartments(){
        List<Departments> departmentsList=departmentDAO.findAll();
        return departmentsList;
    }

    @Transactional
    public void deleteDepartment(Long departmentId) {
       employeeDAO.updateDepartmentOfEmployee(departmentId);
        employeeDAO.flush();
        departmentDAO.deleteByDepartmentId(departmentId);
    }

    @Override
    public Employee addEmployee(Long departmentId,Employee employee){
        Departments departments=departmentDAO.findByDepartmentId(departmentId);
        employee.setActive(true);
        employee.setPassword("employee123");
        Integer capacity=employeeDAO.countEmployeeByDepartment(departmentId);
        if(capacity==null)
            capacity=0;
        if(departments.getCapacity() > capacity) {
            employee.setDepartments(departments);
            return employeeDAO.save(employee);
        }
        else {
            throw new CapacityExceededException("Cannot add employee: Department capacity exceeded.");
        }
    }

    @Override
    public Employee editEmployee(String employeeId,Employee employee){
       System.out.println(employee);

        Employee employee1=employeeDAO.getEmployeeById(employeeId);
        if(employee.getFname()!=null && !employee.getFname().isEmpty())
            employee1.setFname(employee.getFname());
        if(employee.getLname()!=null && !employee.getLname().isEmpty())
            employee1.setLname(employee.getLname());
        if(employee.getTitle()!=null && !employee.getTitle().isEmpty())
            employee1.setTitle(employee.getTitle());
        if(employee.getPassword()!=null && !employee.getPassword().isEmpty())
            employee1.setPassword(employee.getPassword());
        if(employee.getPhoto()!=null && !employee.getPhoto().isEmpty())
            employee1.setPhoto(employee.getPhoto());
        System.out.println(employee.getDepartments());
        if(employee.getDepartments()!=null){
            Departments departments=departmentDAO.findByDepartmentId(employee.getDepartments().getDepartmentId());
            Integer capacity=employeeDAO.countEmployeeByDepartment(departments.getDepartmentId());
            if(capacity==null)
                capacity=0;
            if(departments.getCapacity() > capacity){
            employee1.setDepartments(departments);
           }
        }

        return employeeDAO.save(employee1);
    }

    @Transactional
    public Employee viewEmployeeById(String employeeId){
        Employee employee=employeeDAO.getEmployeeById(employeeId);
        return employee;
    }

    @Transactional
    public List<Employee> viewEmployee(){
        List<Employee> employeeList=employeeDAO.findallEmployee();
        return employeeList;
    }

    @Transactional
    public List<Employee> viewEmployeeByDepartment(Long departmentId){
        List<Employee> employeeList=employeeDAO.findEmployeeByDepartment(departmentId);
        return employeeList;
    }


    @Override
    public void computeSpecialisation(Long Id){
        Specialisation specialisation=studentCoursesDAO.computeSpecialisation(Id);
        Optional<Students> optionalStudents=studentDAO.findById(Id);
        Students students=optionalStudents.orElse(null);
        if(specialisation==null)
            students.setSpecialisation(null);
        else
            students.setSpecialisation(specialisation);

        studentDAO.save(students);

    }

    @Transactional
    public List<SpecialisationAll> viewSpecialisationAll(){
        List<Object[]> results=studentDAO.viewSpecialisation();
        return results.stream().map(result -> new SpecialisationAll(
                (String) result[0],
                (String) result[1],
                (String) result[2],
                (String) result[3],
                (String) result[4],
                (Year) result[5],
                (String) result[6],
                (String) result[7]
        )).collect(Collectors.toList());
    }



    @Transactional
    public List<Students> viewStudentsBySpecialisation(Long specialisationId){
        List<Students> studentsList=studentDAO.viewStudentsBySpecialisation(specialisationId);
        return studentsList;
    }



    @Transactional
    public void removeCourseFromDomain(Long courseId,Long domainId){
        courseDomainDAO.removeCourseFromDomain(courseId,domainId);
    }

    @Transactional
    public void  removePrerequisiteForCourse(Long courseId,Long prerequisiteId){
        coursePrerequisiteDAO.removePrerequisiteForCourse(courseId,prerequisiteId);
    }

    @Transactional
    public void inactivateStudent(String studentId){
        studentDAO.inactivateStudent(studentId);
    }

    @Transactional
    public void activateStudent(String studentId){
        studentDAO.activateStudent(studentId);
    }

    @Transactional
    public void inactivateEmployee(String employeeId){
        employeeDAO.inactivateEmployee(employeeId);
    }

    @Override
    public Admin addAdmin(Admin admin){
        return adminDAO.save(admin);
    }

    @Transactional
    public Admin viewAdmin(String email){
        return adminDAO.findByEmail(email);
    }

    @Override
    public Admin changePassword(String email,String password){
        Admin admin=adminDAO.findByEmail(email);
        admin.setPassword(password);
        return adminDAO.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminDAO.findByEmail(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return admin;
    }

    @Override
    public LoginResponse adminLogin(LoginDTO credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        Admin admin = adminDAO.findByEmail(email);
        if (admin != null) {
            if (admin.isPasswordMatch(password)) {
                String token = tokenDAO.findByUsername(email);
                if (token != null) {
                    return new LoginResponse("User Already logged in", false, null);
                }
                TokenDTO tokens = jwtTokenProvider.generateToken(admin);
                return new LoginResponse("Login Successful", true, tokens);
            } else {
                return new LoginResponse("Password not matched", false, null);
            }
        } else {
            return new LoginResponse("Invalid User.", false, null);
        }
    }

    @Transactional
    public CountDTO getCounts(){
        CountDTO countDTO=new CountDTO();
        Integer courseCount=courseDAO.getCourseCount();
        countDTO.setCourses(courseCount);
        Integer studentCount=studentDAO.getStudentCount();
        countDTO.setStudents(studentCount);
        Integer employeeCount=employeeDAO.getEmployeeCount();
        countDTO.setEmployees(employeeCount);
        countDTO.setDomains(domainDAO.getDomainCount());
        countDTO.setSpecialisations(specialisationDAO.getSpecialisationCount());
        return countDTO;
    }

    @Transactional
    public CourseSchedule viewCourseScheduleById(Long Id){
        Optional<CourseSchedule> opcourseSchedule=courseScheduleDAO.findById(Id);
        CourseSchedule courseSchedule=opcourseSchedule.orElse(null);
        return courseSchedule;
    }

    @Override
    public  void activateEmployee(String employeeId){
        Employee employee=employeeDAO.getEmployeeById(employeeId);
        employee.setActive(true);
        employeeDAO.save(employee);
    }

    @Override
    public Grade addGrade(Grade grade){
        Grade grade1=gradeDAO.save(grade);
        return grade1;
    }

    @Transactional
    public void Logout(String email){
        tokenDAO.removeToken(email);
    }
}


