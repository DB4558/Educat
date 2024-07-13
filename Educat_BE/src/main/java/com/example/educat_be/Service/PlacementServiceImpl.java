package com.example.educat_be.Service;

import com.example.educat_be.AuthenticationAndAuthorisation.JwtTokenProvider;
import com.example.educat_be.DAO.*;
import com.example.educat_be.DTO.LoginDTO;
import com.example.educat_be.DTO.TokenDTO;
import com.example.educat_be.Entity.*;
import com.example.educat_be.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class PlacementServiceImpl implements PlacementService{

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    OrganisationDAO organisationDAO;

    @Autowired
    PlacementOffer placementOffer;

    @Autowired
    SpecialisationDAO specialisationDAO;

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    PlacementFilterDAO placementFilterDAO;

    @Autowired
    StudentPlacementDAO studentPlacementDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    OrganisationHRDAO organisationHRDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TokenDAO tokenDAO;

    @Transactional
    public Employee viewEmployeeDetail(String email){
        Employee employee=employeeDAO.getEmployeeByEmail(email);
        return employee;
    }

    @Override
    public Organisation addOrganisation(Organisation organisation){
        return organisationDAO.save(organisation);
    }

    @Transactional
    public List<Organisation> viewAllOrganisation(){
        List<Organisation> organisations=organisationDAO.findAll();
        return organisations;
    }

    @Transactional
    public Organisation viewOrganisationById(Long organisationId){
        Organisation organisation=organisationDAO.getOrganisationById(organisationId);
        return  organisation;
    }

    @Override
    public Organisation editOrganisation(Long organisationId,Organisation organisation){
        Organisation saveOrganisation=organisationDAO.getOrganisationById(organisationId);

        if(organisation.getName()!=null && !organisation.getName().isEmpty())
            saveOrganisation.setName(organisation.getName());
        if(organisation.getAddress()!=null && !organisation.getAddress().isEmpty())
            saveOrganisation.setAddress(organisation.getAddress());

        return organisationDAO.save(saveOrganisation);
    }

    @Override
    public Placement addOffer(Placement placement){
        Organisation organisation=organisationDAO.getOrganisationById(placement.getOrganisation().getOrganisationId());
        placement.setOrganisation(organisation);
        if(placement.getMinimumcgpa()==null)
            placement.setMinimumcgpa(0.0);
        return placementOffer.save(placement);
    }

    @Override
    public PlacementFilter addOfferCriteria(PlacementFilter placementFilter){
        Placement placement=placementOffer.getPlacementById(placementFilter.getPlacement().getPlacementId());
        placementFilter.setPlacement(placement);
        if(placementFilter.getSpecialisation()!=null) {
            Specialisation specialisation = specialisationDAO.findBySpecialisationId(placementFilter.getSpecialisation().getSpecialisationId());
            placementFilter.setSpecialisation(specialisation);
        }
        if(placementFilter.getDomain()!=null) {
            Domain domain = domainDAO.findByDomainId(placementFilter.getDomain().getDomainId());
            placementFilter.setDomain(domain);
        }
        return placementFilterDAO.save(placementFilter);

    }

    @Transactional
    public List<PlacementFilter> viewAllOffers(){
        List<PlacementFilter> placementList=placementFilterDAO.findAll();
        return placementList;
    }

    @Transactional
    public List<Students> viewEligibleStudents(Long placementId){
        List<Students> studentsList=placementFilterDAO.getEligibleStudents(placementId);
        return studentsList;
    }

    @Transactional
    public List<PlacementFilter> viewAllOffersByOrganisationId(Long organisationId){
        List<PlacementFilter> placementFilterList=placementFilterDAO.viewAllOffersByOrganisationId(organisationId);
        return placementFilterList;
    }

    @Transactional
    public List<Students> viewAppliedStudents(Long placementId){
        List<Students> studentsList=studentPlacementDAO.getAppliedStudent(placementId);
        return studentsList;
    }
    @Transactional
    public List<Students> viewNotAppliedStudents(Long placementId){
        List<Students> studentsList=studentPlacementDAO.getNotAppliedStudent(placementId);
        return studentsList;
    }

    @Override
   public void StudentPlaced(Long placementId,String studentId){
        StudentPlacement studentPlacement=studentPlacementDAO.getRequiredStudent(placementId,studentId);
        studentPlacement.setAcceptance("OFFERED");
        studentPlacementDAO.save(studentPlacement);
   }

   @Transactional
   public List<Students> viewPlacedStudents(){
        List<Students> studentsList=studentDAO.findPlacedStudents();
        return studentsList;
   }

    @Transactional
    public List<Students> viewUnplacedStudents(){
        List<Students> studentsList=studentDAO.findUnPlacedStudents();
        return studentsList;
    }

    @Transactional
    public List<Students> viewPlacedStudentsByOrganisation(Long organisationId){
        List<Students> studentsList=studentDAO.getPlacedStudentsByOrganisation(organisationId);
        return studentsList;
    }

    @Transactional
    public List<Students> viewPlacedStudentsByYear(Year year){
        List<Students> studentsList=studentDAO.getPlacedStudentsByYear(year);
        return studentsList;
    }

    @Transactional
    public List<Students> viewPlacedStudentsByYearDomain(Year year,Long domainId){
        List<Students> studentsList=studentDAO.getPlacedStudentsByYearDomain(year,domainId);
        return studentsList;
    }
    @Transactional
    public List<Students> viewUnPlacedStudentsByYearDomain(Year year,Long domainId){
        List<Students> studentsList=studentDAO.getUnPlacedStudentsByYearDomain(year,domainId);
        return studentsList;
    }

    @Transactional
    public Integer countPlacedStudentsByYearDomain(Year year,Long domainId){
        Integer studentsList=studentDAO.countPlacedStudentsByYearDomain(year,domainId);
        return studentsList;
    }

    @Transactional
    public Integer countUnPlacedStudentsByYearDomain(Year year,Long domainId){
        Integer studentsList=studentDAO.countUnPlacedStudentsByYearDomain(year,domainId);
        return studentsList;
    }

    @Override
    public OrganisationHR addOrganisationHR(Long organisationId,OrganisationHR organisationHR){
        Organisation organisation=organisationDAO.getOrganisationById(organisationId);
        organisationHR.setOrganisation(organisation);
        return organisationHRDAO.save(organisationHR);
    }

    @Override
    public OrganisationHR editOrganisationHR(Long organisationHRId,OrganisationHR organisationHR){
        Optional<OrganisationHR> optionalOrganisationHR=organisationHRDAO.findById(organisationHRId);
        OrganisationHR SaveorganisationHR=optionalOrganisationHR.orElse(null);
        if(organisationHR.getFname()!=null && !organisationHR.getFname().isEmpty())
            SaveorganisationHR.setFname(organisationHR.getFname());
        if(organisationHR.getLname()!=null && !organisationHR.getLname().isEmpty())
            SaveorganisationHR.setLname(organisationHR.getLname());
        if(organisationHR.getEmail()!=null && !organisationHR.getEmail().isEmpty())
            SaveorganisationHR.setEmail(organisationHR.getEmail());
        if(organisationHR.getPhone()!=null)
            SaveorganisationHR.setPhone(organisationHR.getPhone());

        return organisationHRDAO.save(SaveorganisationHR);
    }

    @Transactional
    public OrganisationHR viewOrganisationHR(Long organisationHRId){
        Optional<OrganisationHR> organisationHR=organisationHRDAO.findById(organisationHRId);
        OrganisationHR organisationHR1=organisationHR.orElse(null);
        return organisationHR1;
    }

    @Transactional
    public List<Students> viewPlacedStudentsByDomain(Long domainId){
        List<Students> studentsList=studentDAO.getPlacedStudentsByDomain(domainId);
        return studentsList;
    }


    @Transactional
    public List<Students> viewUnPlacedStudentsByYear(Year year){
        List<Students> studentsList=studentDAO.getUnPlacedStudentsByYear(year);
        return studentsList;
    }

    @Transactional
    public Integer countPlacedStudentsByYear(Year year){
        Integer studentsList=studentDAO.countPlacedStudentsByYear(year);
        return studentsList;
    }

    @Transactional
    public Integer countPlacedStudentsByDomain(Long domainId){
        Integer studentsList=studentDAO.countPlacedStudentsByDomain(domainId);
        return studentsList;
    }

    @Transactional
    public Integer countUnPlacedStudentsByYear(Year year){
        Integer studentsList=studentDAO.countUnPlacedStudentsByYear(year);
        return studentsList;
    }

    @Transactional
    public List<Students> viewUnPlacedStudentsByDomain(Long domainId){
        List<Students> studentsList=studentDAO.getUnPlacedStudentsByDomain(domainId);
        return studentsList;
    }

    @Transactional
    public Integer countUnPlacedStudentsByDomain(Long domainId){
        Integer studentsList=studentDAO.countUnPlacedStudentsByDomain(domainId);
        return studentsList;
    }


    @Override
    public LoginResponse placementLogin(LoginDTO credentials) {
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
