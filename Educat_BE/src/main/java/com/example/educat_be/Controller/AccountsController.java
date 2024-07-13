package com.example.educat_be.Controller;

import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AccountsService;
import com.example.educat_be.Service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenDAO tokenDAO;

    @PostMapping("/addSalary")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Salary> addSalary(@RequestBody Salary salary){
        Salary salary1=accountsService.addSalary(salary);
        return new ResponseEntity<>(salary1, HttpStatus.CREATED);
    }

    @PutMapping("/editSalary/{salaryId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Salary> editSalary(@PathVariable Long salaryId,@RequestBody Salary salary){
        Salary salary1=accountsService.editSalary(salaryId,salary);
        return new ResponseEntity<>(salary1, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllSalary")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewAllsalary(){
        List<Salary> salaryList=accountsService.viewAllSalary();
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/viewAlldepartments")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Departments>> viewAlldepartments(){
        List<Departments> departmentsList=accountsService.viewAllDepartments();
        return new ResponseEntity<>(departmentsList,HttpStatus.OK);
    }

    @GetMapping("/viewAllEmployeeByDepartment/{departmentId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Employee>> viewAllEmployeeDepartment(@PathVariable Long departmentId){
        List<Employee> employeeList=accountsService.viewAllEmployeeByDepartment(departmentId);
        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }

    @GetMapping("/disburseSalaryByDepartment/{departmentId}/{salaryId}/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseSalaryByDepartment(@PathVariable Long departmentId,@PathVariable Long salaryId,@PathVariable String employeeId){
        accountsService.disburseSalaryByDepartment(departmentId,salaryId,employeeId);
        return ResponseEntity.ok("Salary Disbursed succesfully");
    }

    @PostMapping("/disburseSalaryByEmployees/{salaryId}/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseSalaryByEmployees(@RequestBody Set<Long> Ids, @PathVariable Long salaryId, @PathVariable String employeeId){
        accountsService.disburseSalaryByEmployees(Ids,salaryId,employeeId);
        return ResponseEntity.ok("Salary Disbursed succesfully");
    }

    @GetMapping("/disburseSalaryByEmployeeId/{employeeId}/{salaryId}/{currentEmployeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseSalaryByEmployeeId(@PathVariable String employeeId,@PathVariable Long salaryId,@PathVariable String currentEmployeeId){
        accountsService.disburseSalaryByEmployeeId(employeeId,salaryId,currentEmployeeId);
        return ResponseEntity.ok("Salary Disbursed succesfully");
    }

    @GetMapping("/viewSalaryHistoryByEmployee/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployee(@PathVariable String employeeId){
        List<Salary> salaryList=accountsService.viewSalaryHistoryByEmployee(employeeId);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @GetMapping("/viewSalaryHistoryByEmployeeForMonth/{employeeId}/{month}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Salary>> viewSalaryHistoryByEmployeeForMonth(@PathVariable String employeeId, @PathVariable Integer month){
        List<Salary> salaryList=accountsService.viewSalaryHistoryByEmployeeForMonth(employeeId,month);
        return new ResponseEntity<>(salaryList,HttpStatus.OK);
    }

    @PostMapping("/addBill")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Bills> addBill(@RequestBody Bills bills){
        Bills bills1=accountsService.addBill(bills);
        return new ResponseEntity<>(bills1,HttpStatus.CREATED);
    }

    @PutMapping("/editBill/{billId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Bills> editBill(@PathVariable Long billId,@RequestBody Bills bills){
        Bills bills1=accountsService.editBill(billId,bills);
        return new ResponseEntity<>(bills1,HttpStatus.CREATED);
    }

    @GetMapping("/viewAllBill")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Bills>> viewAllBill(){
        List<Bills> bills=accountsService.viewAllBill();
        return new ResponseEntity<>(bills,HttpStatus.OK);
    }

    @GetMapping("/viewAllDomain")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Domain>> viewAllDomain(){
        List<Domain> domains=accountsService.viewAllDomain();
        return new ResponseEntity<>(domains,HttpStatus.OK);
    }

    @GetMapping("/viewStudentsByDomain/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewStudentsByDomain(@PathVariable Long domainId){
        List<Students> students=accountsService.viewStudentsByDomain(domainId);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/disburseBillsByDomain/{domainId}/{billId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseBillsByDomain(@PathVariable Long domainId,@PathVariable Long billId){
        accountsService.disburseBillsByDomain(domainId,billId);
        return ResponseEntity.ok("Bill Disbursed successfully");
    }

    @PostMapping("/disburseBillsByStudents/{billId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseBillsByStudents(@RequestBody Set<Long> studentId,@PathVariable Long billId){
        accountsService.disburseBillsByStudents(studentId,billId);
        return ResponseEntity.ok("Bill Disbursed successfully");
    }

    @GetMapping("/disburseBillsByStudentId/{studentId}/{billId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> disburseBillsByStudentId(@PathVariable String studentId,@PathVariable Long billId){
        accountsService.disburseBillsByStudentId(studentId,billId);
        return ResponseEntity.ok("Bill Disbursed successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = accountsService.accountLogin(credentials);

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
