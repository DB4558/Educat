package com.example.educat_be.Service;

import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;

import java.time.Year;
import java.util.List;

public interface PlacementService {

    Employee viewEmployeeDetail(String email);

    Organisation addOrganisation(Organisation organisation);

    List<Organisation> viewAllOrganisation();

    Organisation viewOrganisationById(Long organisationId);

    Organisation editOrganisation(Long organisationId,Organisation organisation);

    Placement addOffer(Placement placement);

    PlacementFilter addOfferCriteria(PlacementFilter placementFilter);

    List<PlacementFilter> viewAllOffers();

    List<PlacementFilter> viewAllOffersByOrganisationId(Long organisationId);

    List<Students> viewEligibleStudents(Long placementId);

    List<Students> viewAppliedStudents(Long placementId);

    List<Students> viewNotAppliedStudents(Long placementId);

   void StudentPlaced(Long placementId,String studentId);

   List<Students> viewPlacedStudents();

   List<Students>  viewUnplacedStudents();

   List<Students> viewPlacedStudentsByOrganisation(Long organisationId);

   List<Students> viewPlacedStudentsByYear(Year year);
    List<Students> viewPlacedStudentsByDomain(Long domainId);

    Integer countPlacedStudentsByDomain(Long domainId);

    Integer countPlacedStudentsByYear(Year year);

    List<Students> viewUnPlacedStudentsByYear(Year year);

    Integer countUnPlacedStudentsByYear(Year year);

    List<Students> viewUnPlacedStudentsByDomain(Long domainId);
    Integer countUnPlacedStudentsByDomain(Long domainId);

    List<Students> viewPlacedStudentsByYearDomain(Year year,Long domainId);

    List<Students> viewUnPlacedStudentsByYearDomain(Year year,Long domainId);

    Integer countPlacedStudentsByYearDomain(Year year,Long domainId);

    Integer countUnPlacedStudentsByYearDomain(Year year,Long domainId);

    OrganisationHR addOrganisationHR(Long organisationId,OrganisationHR organisationHR);

    OrganisationHR viewOrganisationHR(Long organisationId);

    OrganisationHR editOrganisationHR(Long organisationHRId,OrganisationHR organisationHR);

    LoginResponse placementLogin(LoginDTO loginDTO);







}
