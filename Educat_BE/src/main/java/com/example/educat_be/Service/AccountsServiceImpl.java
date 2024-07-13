package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountsServiceImpl implements AccountsService{

    @Autowired
    SalaryDAO salaryDAO;

    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmployeeSalaryDAO employeeSalaryDAO;

    @Autowired
    BillDAO billDAO;

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    StudentBillsDAO studentBillsDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TokenDAO tokenDAO;

    @Override
    public Salary addSalary(Salary salary){
        return salaryDAO.save(salary);
    }

    @Override
    public Salary editSalary(Long salaryId,Salary salary){
        Optional<Salary> optionalSalary=salaryDAO.findById(salaryId);
        Salary saveSalary=optionalSalary.orElse(null);

        if(salary.getAmount()!=null)
            saveSalary.setAmount(salary.getAmount());
        if(salary.getPaymentDate()!=null)
            saveSalary.setPaymentDate(salary.getPaymentDate());
        if(salary.getDescription()!=null && !salary.getDescription().isEmpty())
            saveSalary.setDescription(salary.getDescription());
        return salaryDAO.save(saveSalary);
    }

    @Transactional
    public List<Salary> viewAllSalary(){
        List<Salary> salaryList=salaryDAO.findAll();
        return salaryList;
    }

    @Transactional
    public List<Departments> viewAllDepartments(){
        List<Departments> departmentsList=departmentDAO.findAll();
        return departmentsList;
    }

    @Transactional
    public List<Employee> viewAllEmployeeByDepartment(Long departmentId){
        List<Employee> employeeList=employeeDAO.findEmployeeByDepartment(departmentId);
        return employeeList;
    }


    @Transactional
    public void disburseSalaryByDepartment(Long departmentId,Long salaryId,String employeeId){
        List<Employee> employeeList=employeeDAO.findEmployeeByDepartment(departmentId);
        Salary salary=salaryDAO.findSalaryById(salaryId);

        employeeList.forEach(employee -> {
            if(!employee.getEmployeeId().matches(employeeId)){
                EmployeeSalary employeeSalary=new EmployeeSalary();
                employeeSalary.setSalary(salary);
                employeeSalary.setEmployee(employee);
                employeeSalaryDAO.save(employeeSalary);
            }
        });
    }


    @Transactional
    public void disburseSalaryByEmployees(Set<Long> Ids,Long salaryId,String currentEmployeeId){
        List<Employee> employeeList=employeeDAO.findAllById(Ids);
        Salary salary=salaryDAO.findSalaryById(salaryId);
        employeeList.forEach(employee -> {
            if(!employee.getEmployeeId().matches(currentEmployeeId)){
                EmployeeSalary employeeSalary=new EmployeeSalary();
                employeeSalary.setSalary(salary);
                employeeSalary.setEmployee(employee);
                employeeSalaryDAO.save(employeeSalary);
            }
        });
    }

    @Transactional
    public void disburseSalaryByEmployeeId(String employeeId,Long salaryId,String currentEmployeeId){
        Employee employee=employeeDAO.getEmployeeById(employeeId);
        Salary salary=salaryDAO.findSalaryById(salaryId);
        if(!employee.getEmployeeId().matches(currentEmployeeId)){
            EmployeeSalary employeeSalary=new EmployeeSalary();
            employeeSalary.setSalary(salary);
            employeeSalary.setEmployee(employee);
            employeeSalaryDAO.save(employeeSalary);
        }
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
   public Bills addBill(Bills bills){
        return billDAO.save(bills);
   }

   @Override
    public Bills editBill(Long billId, Bills updatedBill) {
        Optional<Bills> optionalBill = billDAO.findById(billId);
        Bills saveBill = optionalBill.orElse(null);

        if (saveBill == null) {
            throw new RuntimeException("Bill not found with id: " + billId); // or any other exception handling as per your design
        }

        if (updatedBill.getDescription() != null && !updatedBill.getDescription().isEmpty())
            saveBill.setDescription(updatedBill.getDescription());
        if (updatedBill.getAmount() != null)
            saveBill.setAmount(updatedBill.getAmount());
        if (updatedBill.getBillDate() != null)
            saveBill.setBillDate(updatedBill.getBillDate());
        if (updatedBill.getDeadline() != null)
            saveBill.setDeadline(updatedBill.getDeadline());

        return billDAO.save(saveBill);
    }

    @Transactional
    public List<Bills> viewAllBill(){
        List<Bills> salaryList=billDAO.findAll();
        return salaryList;
    }

    @Transactional
    public List<Domain> viewAllDomain(){
        List<Domain> domains=domainDAO.findAll();
        return domains;
    }

    @Transactional
    public List<Students> viewStudentsByDomain(Long domainId){
        List<Students> students=studentDAO.findStudentByDomainId(domainId);
        return students;
    }

    @Transactional
    public void disburseBillsByDomain(Long domainId,Long billId){
        List<Students> employeeList=studentDAO.findStudentByDomainId(domainId);
        Bills bills=billDAO.findBillById(billId);

        employeeList.forEach(employee -> {
          Student_Bills studentBills=new Student_Bills();
          studentBills.setBills(bills);
          studentBills.setStudents(employee);
          studentBillsDAO.save(studentBills);
        });
    }


    @Transactional
    public void disburseBillsByStudents(Set<Long> studentId,Long billId){
        List<Students> employeeList=studentDAO.findAllById(studentId);
        Bills bills=billDAO.findBillById(billId);
        employeeList.forEach(employee -> {
            Student_Bills studentBills=new Student_Bills();
            studentBills.setBills(bills);
            studentBills.setStudents(employee);
            studentBillsDAO.save(studentBills);
        });
    }

    @Transactional
    public void disburseBillsByStudentId(String studentId,Long billId){
        Students students=studentDAO.findByStudentId(studentId);
        Bills bills=billDAO.findBillById(billId);

        Student_Bills studentBills=new Student_Bills();
        studentBills.setBills(bills);
        studentBills.setStudents(students);
        studentBillsDAO.save(studentBills);
    }

    @Override
    public LoginResponse accountLogin(LoginDTO credentials) {
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
