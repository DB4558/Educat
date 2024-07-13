package com.example.educat_be.Controller;


import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.Employee;
import com.example.educat_be.Entity.Hostel;
import com.example.educat_be.Entity.Salary;
import com.example.educat_be.Entity.Students;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AuthenticationService;
import com.example.educat_be.Service.EstateService;
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
@CrossOrigin
@RequestMapping("/estate")
public class EstateController {


    @Autowired
    private EstateService estateService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenDAO tokenDAO;

    @GetMapping("/viewEmployee/{email}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Employee> viewEmployee(@PathVariable String email){
        Employee employee=estateService.viewEmployeeDetail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/viewSalaryHistoryByEmployee/{email}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployee(@PathVariable String email){
        List<Salary> salaryList=estateService.viewSalaryHistoryByEmployee(email);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/viewSalaryHistoryByEmployeeForMonth/{employeeId}/{month}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployeeForMonth(@PathVariable String employeeId, @PathVariable Integer month){
        List<Salary> salaryList=estateService.viewSalaryHistoryByEmployeeForMonth(employeeId,month);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/logout/{email}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> logout(@PathVariable String email){
        estateService.Logout(email);
        return ResponseEntity.ok("Logout succeed");
    }


    @PostMapping("/addRoom")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Hostel> addRoom(@RequestBody Hostel hostel){
        Hostel hostel1=estateService.addRoom(hostel);
        return new ResponseEntity<>(hostel1, HttpStatus.CREATED);
    }

    @PutMapping("/editRoom/{hostelId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Hostel> editRoom(@PathVariable Long hostelId,@RequestBody Hostel hostel){
        Hostel hostel1=estateService.editRoom(hostelId,hostel);
        return new ResponseEntity<>(hostel1, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllRooms")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Hostel>> viewAllRooms(){
        List<Hostel> hostels=estateService.viewAllRoom();
        return new ResponseEntity<>(hostels,HttpStatus.OK);
    }

    @GetMapping("/viewStudent")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewStudents(){
        List<Students> hostels=estateService.viewStudents();
        return new ResponseEntity<>(hostels,HttpStatus.OK);
    }

    @GetMapping("/viewRoomById/{hostelId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Hostel> viewRoomById(@PathVariable Long hostelId){
        Hostel hostels=estateService.viewRoomById(hostelId);
        return new ResponseEntity<>(hostels,HttpStatus.OK);
    }

    @GetMapping("/viewAvailableRooms")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Hostel>> viewAvailableRooms(){
        List<Hostel> hostels=estateService.viewAvailableRooms();
        return new ResponseEntity<>(hostels,HttpStatus.OK);
    }

    @GetMapping("/viewNotAvailableRooms")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Hostel>> viewNotAvailableRooms(){
        List<Hostel> hostels=estateService.viewNotAvailableRooms();
        return new ResponseEntity<>(hostels,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = estateService.estateLogin(credentials);

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
