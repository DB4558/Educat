import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './Home';
import {EmailProvider} from './EmailContext'
import { Login } from './Login';
import AdminHome from './Components/Admin/AdminHome';
import AdminCourse from './Components/Admin/AdminCourse';
import AdminDomain from './Components/Admin/AdminDomain';
import AdminSpecialisation from './Components/Admin/AdminSpecialisation';
import AdminStudent from './Components/Admin/AdminStudent';
import AddDomain from './Components/Admin/addDomain';
import ViewDomains from './Components/Admin/viewDomain';
import AddSpecialisation from './Components/Admin/AddSpecialisation';
import ViewSpecialisations from './Components/Admin/ViewSpecialisation';
import AddCourse from './Components/Admin/AddCourse';
import ViewCourses from './Components/Admin/ViewCourse';
import ViewCourseId from './Components/Admin/ViewCourseId';
import EditCourse from './Components/Admin/EditCourse';
import AddSchedule from './Components/Admin/AddSchedule';
import ViewSchedules from './Components/Admin/ViewSchedule';
import EditSchedule from './Components/Admin/EditSchedule';
import AddPrerequisite from './Components/Admin/AddPrerequisite';
import ViewPrerequisites from './Components/Admin/ViewPrerequisite';
import AddCourseDomain from './Components/Admin/AddCourseDomain';
import AddCourseSpecialisation from './Components/Admin/AddCourseSpecialisation';
import AddStudent from './Components/Admin/AddStudent';
import ViewStudents from './Components/Admin/viewStudent';
import EditStudent from './Components/Admin/EditStudent';
import AddDepartment from './Components/Admin/AddDepartment';
import ViewDepartments from './Components/Admin/ViewDepartment';
import EditDepartment from './Components/Admin/EditDepartment';
import AddEmployee from './Components/Admin/AddEmployee';
import ViewEmployees from './Components/Admin/ViewEmployee';
import EditEmployee from './Components/Admin/EditEmployee';
import ViewEmployeeDept from './Components/Admin/ViewEmployeeDept';
import ViewStudentCourse from './Components/Admin/ViewStudentCourse';
import ViewDomainId from './Components/Admin/ViewDomainId';
import EditDomain from './Components/Admin/EditDomain';
import ViewDomainStudent from './Components/Admin/ViewDomainStudent';
import ViewDomainCourse from './Components/Admin/ViewDomainCourse';
import ViewDomainSchedule from './Components/Admin/ViewDomainSchedule';
import ViewSpecialisationId from './Components/Admin/ViewSpecialisationId';
import EditSpecialisation from './Components/Admin/EditSpecialisation';
import ViewSpecialisationStudent from './Components/Admin/ViewSpecialisationStudent';
import ViewSpecialisationCourse from './Components/Admin/ViewSpecialisationCourse';
import AddGrade from './Components/Admin/AddGrade';
import ViewGrades from './Components/Admin/ViewGrade';
import EditGrade from './Components/Admin/EditGrade';
import EstateHome from './Components/Estate/EstateHome';
import AddRoom from './Components/Estate/AddRoom';
import ViewRooms from './Components/Estate/ViewRoom';
import EditRoom from './Components/Estate/EditRoom';
import ViewSalary from './Components/Estate/ViewSalary';


function App() {
  return (
    <EmailProvider>
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login/:role" element={<Login />}></Route>
          <Route path="/AdminHome" element={<AdminHome/>}></Route>
          <Route path="/EstateHome" element={<EstateHome/>}></Route>
          <Route path="/admin/courses" element={<AdminCourse />} />
          <Route path="/admin/domains" element={<AdminDomain />} />
          <Route path="/addDomain" element={<AddDomain/>} />
          <Route path="/addDepartment" element={<AddDepartment/>} />
          <Route path="/addCourse" element={<AddCourse/>} />
          <Route path="/addGrade" element={<AddGrade/>} />
          <Route path="/addStudent" element={<AddStudent/>} />
          <Route path="/addRoom" element={<AddRoom/>} />
          <Route path="/editRoom/:hostelId" element={<EditRoom/>} />
          <Route path="/addEmployee" element={<AddEmployee/>} />
          <Route path="/addSpecialisation" element={<AddSpecialisation/>} />
          <Route path="/addSchedule/:courseId/:courseCode" element={<AddSchedule/>} />
          <Route path="/addPrerequisite/:courseId/:courseCode" element={<AddPrerequisite/>} />
          <Route path="/addCourseDomain/:courseId/:courseCode" element={<AddCourseDomain/>} />
          <Route path="/addCourseSpecialisation/:courseId/:courseCode" element={<AddCourseSpecialisation/>} />
          <Route path="/viewSchedule/:courseId/:courseCode" element={<ViewSchedules/>} />
          <Route path="/viewPrerequisite/:courseId/:courseCode" element={<ViewPrerequisites/>} />
          <Route path="/editSchedule/:Id/:courseId/:courseCode" element={<EditSchedule/>} />
          <Route path="/viewDomain" element={<ViewDomains />} />
          <Route path="/viewRoom" element={<ViewRooms />} />
          <Route path="/viewSalary" element={<ViewSalary/>} />
          <Route path="/viewDomainId/:domainId" element={<ViewDomainId/>} />
          <Route path="/viewCourse" element={<ViewCourses />} />
          <Route path="/viewCourseId/:courseId" element={<ViewCourseId />} />
          <Route path="/viewEmployeeDept/:departmentId" element={<ViewEmployeeDept />} />
          <Route path="/viewStudentCourse/:courseId" element={<ViewStudentCourse />} />
          <Route path="/editCourse/:courseId" element={<EditCourse/>} />
          <Route path="/editGrade/:gradeId" element={<EditGrade/>} />
          <Route path="/editDomain/:domainId" element={<EditDomain/>} />
          <Route path="/editStudent/:studentId" element={<EditStudent/>} />
          <Route path="/editSpecialisation/:specialisationId" element={<EditSpecialisation/>} />
          <Route path="/editDepartment/:departmentId" element={<EditDepartment/>} />
          <Route path="/editEmployee/:employeeId" element={<EditEmployee/>} />
          <Route path="/viewSpecialisation" element={<ViewSpecialisations />} />
          <Route path="/viewSpecialisationId/:specialisationId" element={<ViewSpecialisationId/>} />
          <Route path="/viewStudent" element={<ViewStudents/>} />
          <Route path="/viewGrade" element={<ViewGrades/>} />
          <Route path="/viewDomainStudent/:domainId" element={<ViewDomainStudent/>} />
          <Route path="/viewSpecialisationStudent/:specialisationId" element={<ViewSpecialisationStudent/>} />
          <Route path="/viewSpecialisationCourse/:specialisationId" element={<ViewSpecialisationCourse/>} />
          <Route path="/viewDomainCourse/:domainId" element={<ViewDomainCourse/>} />
          <Route path="/viewDomainTimeTable/:domainId" element={<ViewDomainSchedule/>} />
          <Route path="/viewEmployee" element={<ViewEmployees/>} />
          <Route path="/viewDepartment" element={<ViewDepartments/>} />
         
          <Route path="/admin/specialisations" element={<AdminSpecialisation />} />
          <Route path="/admin/students" element={<AdminStudent />} />
        </Routes>
      </div>
    </Router>
    </EmailProvider>
  );
}

export default App;
