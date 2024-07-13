package com.example.educat_be.Service;

import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.Entity.Employee;
import com.example.educat_be.Entity.Hostel;
import com.example.educat_be.Entity.Salary;
import com.example.educat_be.Entity.Students;
import com.example.educat_be.Response.LoginResponse;

import java.util.List;

public interface EstateService {

    Hostel addRoom(Hostel hostel);

    Hostel editRoom(Long hostelId,Hostel hostel);

    List<Hostel> viewAllRoom();

    List<Hostel> viewAvailableRooms();

    List<Hostel> viewNotAvailableRooms();

    LoginResponse estateLogin(LoginDTO loginDTO);

    Employee viewEmployeeDetail(String email);

    List<Salary> viewSalaryHistoryByEmployee(String email);

    List<Students> viewStudents();

    void Logout(String email);

    Hostel viewRoomById(Long hostelId);

    List<Salary> viewSalaryHistoryByEmployeeForMonth(String employeeId, Integer month);

}
