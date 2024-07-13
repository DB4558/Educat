import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewStudent.css'; 
import api from './api';

const ViewDomainStudent = () => {
    const {domainId}=useParams();
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
            const response = await api.get(`/viewStudentByDomain/${domainId}`);
            setStudents(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                
                    navigate('/login');
                
            } else {
                console.error('Error fetching students:', error);
            }
        }
    };

    const handleBack = () => {
        navigate(`/viewDomainId/${domainId}`);
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
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button onClick={handleBack} className="back-btn">Back to Domain</button>
        </div>
    );
};

export default ViewDomainStudent;
