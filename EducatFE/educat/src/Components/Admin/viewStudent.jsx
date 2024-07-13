import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewStudent.css'; 
import api from './api';

const ViewStudents = () => {
    const [students, setStudents] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchStudents();
        const intervalId = setInterval(fetchStudents, 60000);

        return () => clearInterval(intervalId);
    }, [location]);

    const fetchStudents = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get('http://localhost:8090/admin/viewStudents');
            setStudents(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchStudents(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching students:', error);
            }
        }
    };

    const handleactivate = async (studentId) => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/activateStudent/${studentId}`);
            alert("Student activated successfully")
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchStudents(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error activating students:', error);
            }
        }
    };

    const handleinactivate = async (studentId) => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/inactivateStudent/${studentId}`);
            alert("Student inactivated successfully")
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchStudents(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error inactivating students:', error);
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
        <div className="view-students-container">
            <AdminSidebar />
            <div className="students-table-container">
                <h2>Student List</h2>
                <table className="students-table">
                    <thead>
                        <tr>
                        <th>Roll Number</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>CGPA</th>
                            <th>Total Credits</th>
                            <th>Graduation Year</th>
                            <th>Specialisation Name</th>
                            <th>Domain Name</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {students.map(student => (
                            <tr key={student.studentId}>
                                <td>{student.roll_number}</td>
                                <td>{student.first_name}</td>
                                <td>{student.last_name}</td>
                                <td>{student.email}</td>
                                <td>{student.cgpa.toFixed(2)}</td>
                                <td>{student.total_credits}</td>
                                <td>{student.graduation_year}</td>
                                <td>{student.specialisation ? student.specialisation.name : 'N/A'}</td>
                                <td>{student.domain.program}</td>
                                <td>
                                    <button onClick={() => navigate(`/editStudent/${student.student_id}`)}><i className="fa fa-edit"></i></button>
                                    <button onClick={() => handleactivate(student.student_id)}><i className="fa fa-check-circle"></i></button>
                                    <button onClick={() => handleinactivate(student.student_id)}><i className="fa fa-times-circle"></i></button>
                                  
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewStudents;
