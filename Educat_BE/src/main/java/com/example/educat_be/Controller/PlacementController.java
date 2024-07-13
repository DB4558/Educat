package com.example.educat_be.Controller;

import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.FinalResponse;
import com.example.educat_be.Response.LoginResponse;
import com.example.educat_be.Service.AuthenticationService;
import com.example.educat_be.Service.PlacementService;
import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/placement")
public class PlacementController {

    @Autowired
    PlacementService placementService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenDAO tokenDAO;

    @GetMapping("/viewEmployee/{email}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Employee> viewEmployee(@PathVariable String email){
        Employee employee=placementService.viewEmployeeDetail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/addOrganisation")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Organisation> addOrganisation(@RequestBody Organisation organisation){
        Organisation organisation1=placementService.addOrganisation(organisation);
        return new ResponseEntity<>(organisation1,HttpStatus.CREATED);
    }

    @GetMapping("/viewAllOrganisation")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Organisation>> viewAllOrganisation(){
        List<Organisation> organisationList=placementService.viewAllOrganisation();
        return new ResponseEntity<>(organisationList,HttpStatus.OK);
    }

    @GetMapping("/viewOrganisationById/{organisationId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Organisation> viewOrganisationById(@PathVariable Long organisationId){
        Organisation organisation=placementService.viewOrganisationById(organisationId);
        return new ResponseEntity<>(organisation,HttpStatus.OK);
    }

    @PutMapping("/editOrganisation/{organisationId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Organisation> editOrganisation(@PathVariable Long organisationId,@RequestBody Organisation organisation){
        Organisation organisation1=placementService.editOrganisation(organisationId,organisation);
        return new ResponseEntity<>(organisation1,HttpStatus.OK);
    }

    @PostMapping("/addOffer")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Placement> addOffer(@RequestBody Placement placement){
        Placement placement1=placementService.addOffer(placement);
        return new ResponseEntity<>(placement1,HttpStatus.CREATED);
    }

    @PostMapping("/addOfferCriteria")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<PlacementFilter> addOfferCriteria(@RequestBody PlacementFilter placementFilter){
        PlacementFilter placementFilter1=placementService.addOfferCriteria(placementFilter);
        return new ResponseEntity<>(placementFilter1,HttpStatus.CREATED);
    }

    @GetMapping("/viewAllOffers")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<PlacementFilter>> viewAllOffers(){
        List<PlacementFilter> placementList=placementService.viewAllOffers();
        return new ResponseEntity<>(placementList,HttpStatus.OK);
    }

    @GetMapping("/viewEligibleStudents/{placementId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewEligibleStudents(@PathVariable Long placementId){
        List<Students> studentsList=placementService.viewEligibleStudents(placementId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewAllOffersByOrganisationId/{organisationId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<PlacementFilter>> viewAllOffersByOrganisationId(@PathVariable Long organisationId){
        List<PlacementFilter> placementFilterList=placementService.viewAllOffersByOrganisationId(organisationId);
        return new ResponseEntity<>(placementFilterList,HttpStatus.OK);
    }

    @GetMapping("/viewAppliedStudents/{placementId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewAppliedStudents(@PathVariable Long placementId){
        List<Students> studentsList=placementService.viewAppliedStudents(placementId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewNotAppliedStudents/{placementId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewNotAppliedStudents(@PathVariable Long placementId){
        List<Students> studentsList=placementService.viewNotAppliedStudents(placementId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/studentPlaced/{placementId}/{studentId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> StudentPlaced(@PathVariable Long placementId,@PathVariable String studentId){
        placementService.StudentPlaced(placementId,studentId);
        return ResponseEntity.ok("Student Offered Placement successfully");
    }

    @GetMapping("/viewPlacedStudents")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewPlacedStudents(){
        List<Students> studentsList=placementService.viewPlacedStudents();
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewUnplacedStudents")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewUnplacedStudents(){
        List<Students> studentsList=placementService.viewUnplacedStudents();
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewPlacedStudentsByOrganisation/{organisationId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewPlacedStudentsByOrganisation(@PathVariable Long organisationId){
        List<Students> studentsList=placementService.viewPlacedStudentsByOrganisation(organisationId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewPlacedStudentsByYear/{year}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewPlacedStudentsByYear(@PathVariable Year year){
        List<Students> studentsList=placementService.viewPlacedStudentsByYear(year);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewUnPlacedStudentsByYear/{year}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewUnPlacedStudentsByYear(@PathVariable Year year){
        List<Students> studentsList=placementService.viewUnPlacedStudentsByYear(year);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }
    @GetMapping("/countPlacedStudentsByYear/{year}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countPlacedStudentsByYear(@PathVariable Year year){
        Integer studentsList=placementService.countPlacedStudentsByYear(year);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/countUnPlacedStudentsByYear/{year}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countUnPlacedStudentsByYear(@PathVariable Year year){
        Integer studentsList=placementService.countUnPlacedStudentsByYear(year);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewPlacedStudentsByDomain/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewPlacedStudentsByDomain(@PathVariable Long domainId){
        List<Students> studentsList=placementService.viewPlacedStudentsByDomain(domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewUnPlacedStudentsByDomain/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewUnPlacedStudentsByDomain(@PathVariable Long domainId){
        List<Students> studentsList=placementService.viewUnPlacedStudentsByDomain(domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/countPlacedStudentsByDomain/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countPlacedStudentsByDomain(@PathVariable Long domainId){
        Integer studentsList=placementService.countPlacedStudentsByDomain(domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/countPlacedStudentsByYearDomain/{year}/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countPlacedStudentsByYearDomain(@PathVariable Year year,@PathVariable Long domainId){
        Integer studentsList=placementService.countPlacedStudentsByYearDomain(year,domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewPlacedStudentsByYearDomain/{year}/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewPlacedStudentsByYearDomain(@PathVariable Year year,@PathVariable Long domainId){
        List<Students> studentsList=placementService.viewPlacedStudentsByYearDomain(year,domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/viewUnPlacedStudentsByYearDomain/{year}/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Students>> viewUnPlacedStudentsByYearDomain(@PathVariable Year year,@PathVariable Long domainId){
        List<Students> studentsList=placementService.viewUnPlacedStudentsByYearDomain(year,domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @GetMapping("/countUnPlacedStudentsByYearDomain/{year}/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countUnPlacedStudentsByYearDomain(@PathVariable Year year,@PathVariable Long domainId){
        Integer studentsList=placementService.countUnPlacedStudentsByYearDomain(year,domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @PostMapping("/addOrganisationHR/{organisationId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<OrganisationHR> addOrganisationHR(@PathVariable Long organisationId,@RequestBody OrganisationHR organisationHR){
        OrganisationHR organisationHR1=placementService.addOrganisationHR(organisationId,organisationHR);
        return new ResponseEntity<>(organisationHR1,HttpStatus.CREATED);
    }

    @PutMapping("/editOrganisationHR/{organisationHRId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<OrganisationHR> editOrganisationHR(@PathVariable Long organisationHRId,@RequestBody OrganisationHR organisationHR){
        OrganisationHR organisationHR1=placementService.editOrganisationHR(organisationHRId,organisationHR);
        return new ResponseEntity<>(organisationHR1,HttpStatus.CREATED);
    }

    @GetMapping("/viewOrganisationHR/{organisationHRId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<OrganisationHR> viewOrganisationHR(@PathVariable Long organisationHRId){
        OrganisationHR organisationHR1=placementService.viewOrganisationHR(organisationHRId);
        return new ResponseEntity<>(organisationHR1,HttpStatus.CREATED);
    }

    @GetMapping("/countUnPlacedStudentsByDomain/{domainId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Integer> countUnPlacedStudentsByDomain(@PathVariable Long domainId){
        Integer studentsList=placementService.countUnPlacedStudentsByDomain(domainId);
        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<FinalResponse> login(@RequestBody LoginDTO credentials, HttpServletResponse response) {
        LoginResponse loginResponse = placementService.placementLogin(credentials);

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
