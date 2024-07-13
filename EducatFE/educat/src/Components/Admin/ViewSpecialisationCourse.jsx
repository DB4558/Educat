import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewCourse.css';
import api from './api';

const ViewSpecialisationCourse = () => {
    const {specialisationId}=useParams();
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
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewCoursesBySpecialisation/${specialisationId}`
               );
            setCourses(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchCourses();
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching courses:', error);
            }
        }
    };

    const getNewAccessToken = async () => {
        try {
            const response = await axios.post('http://localhost:8090/admin/refresh-token', {}, {
                withCredentials: true
            });
            return response.data.accesstoken;
        } catch (error) {
            throw new Error('Unable to refresh token');
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
                               
                                
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewSpecialisationCourse;
