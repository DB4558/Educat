import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewCourse.css';
import api from './api';

const ViewCourses = () => {
    const [courses, setCourses] = useState([]);
    const navigate = useNavigate();
    const location=useLocation();

    useEffect(() => {

        fetchCourses();
        const intervalId = setInterval(fetchCourses, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      
    const fetchCourses = async () => {
        try {
        
            const response = await api.get('/viewCourseList'
               );
            setCourses(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
               
                    alert("Session Expired,Login again");
                    navigate('/login');
                
            } else {
                console.error('Error fetching department:', error);
            }
        }
    };

   

    const handleDelete = (courseId) => async (e) => {
        e.preventDefault();
        try {
            const response = await api.delete(`/deleteCourse/${courseId}`);
            alert("Course deleted successfully");
            fetchCourses(); 
        } catch (error) {
           
                console.error('Error deleting prerequisite:', error);
                alert('Failed to delete the prerequisite');
            
        }
    };

    return (
        <div className="view-courses-container">
            <AdminSidebar />
            <div className="courses-table-container">
            <h2>Course List</h2>
                <table className="courses-table">
                    <thead>
                        <tr>
                            <th>Course Code</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Year</th>
                            <th>Term</th>
                            <th>Capacity</th>
                            <th>Credits</th>
                            <th>Faculty First name</th>
                            <th>Faculty Last name</th>
                            <th>Faculty Email</th>
                            <th>Delete Course</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {courses.map(course => (
                            <tr key={course.courseId}>
                                <td>{course.courseCode}</td>
                                <td>{course.name}</td>
                                <td>{course.description}</td>
                                <td>{course.year}</td>
                                <td>{course.term}</td>
                                <td>{course.capacity}</td>
                                <td>{course.credits}</td>
                                <td>{ course.employee ? course.employee.fname:'N/A'}</td>
                                <td>{course.employee ? course.employee.lname: 'N/A'}</td>
                                <td>{course.employee ? course.employee.email:'N/A'}</td>
                                <td>
                                        <button onClick={handleDelete(course.courseId)}>
                                            <i className="fa fa-trash" />
                                        </button>
                                    </td>
                                <td><button onClick={() => navigate(`/viewCourseId/${course.courseId}`)}>Click Here</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewCourses;
