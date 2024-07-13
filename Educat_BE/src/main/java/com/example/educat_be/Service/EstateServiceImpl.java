package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.Employee;
import com.example.educat_be.Entity.Hostel;
import com.example.educat_be.Entity.Salary;
import com.example.educat_be.Entity.Students;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EstateServiceImpl implements EstateService{

    @Autowired
    HostelDAO hostelDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmployeeSalaryDAO employeeSalaryDAO;

    @Override
    public Hostel addRoom(Hostel hostel){
        return hostelDAO.save(hostel);
    }

    @Transactional
    public Employee viewEmployeeDetail(String email){
        Employee employee=employeeDAO.getEmployeeByEmail(email);
        return employee;
    }

    @Transactional
    public List<Salary> viewSalaryHistoryByEmployee(String email){
        List<Salary> salaryList=employeeSalaryDAO.findSalaryHistoryByEmail(email);
        return salaryList;
    }

    @Transactional
    public List<Students> viewStudents(){
        List<Students> students=studentDAO.findAll();
        return students;
    }

    @Transactional
    public Hostel viewRoomById(Long hostelId){
       Optional <Hostel> hostel=hostelDAO.findById(hostelId);
       Hostel hostel1=hostel.orElse(null);
       return hostel1;
    }

    @Transactional
    public List<Salary> viewSalaryHistoryByEmployeeForMonth(String employeeId, Integer month){
        List<Salary> salaryList=employeeSalaryDAO.findSalaryHistoryByIdandMonth(employeeId,month);
        return salaryList;
    }

    @Override
    public Hostel editRoom(Long hostelId,Hostel updatedHostel){
        Optional<Hostel> optionalHostel = hostelDAO.findById(hostelId);

        Hostel existingHostel = optionalHostel.orElse(null);

        if (updatedHostel.getName() != null) {
            existingHostel.setName(updatedHostel.getName());
        }
        if (updatedHostel.getRoom() != null) {
            existingHostel.setRoom(updatedHostel.getRoom());
        }
        if (updatedHostel.getFloor() != null) {
            existingHostel.setFloor(updatedHostel.getFloor());
        }

        if (updatedHostel.getStudents() != null) {
            Students students=studentDAO.findByStudentId(updatedHostel.getStudents().getStudent_id());
            existingHostel.setStudents(students);
        }
        return hostelDAO.save(existingHostel);
    }

    @Transactional
    public void Logout(String email){
        tokenDAO.removeToken(email);
    }

    @Transactional
    public List<Hostel> viewAllRoom(){
        List<Hostel> hostels=hostelDAO.findAll();
        return hostels;
    }

    @Transactional
    public List<Hostel> viewAvailableRooms(){
        List<Hostel> hostels=hostelDAO.viewAvailableRooms();
        return hostels;
    }

    @Transactional
    public List<Hostel> viewNotAvailableRooms(){
        List<Hostel> hostels=hostelDAO.viewNotAvailableRooms();
        return hostels;
    }

    @Override
    public LoginResponse estateLogin(LoginDTO credentials) {
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
