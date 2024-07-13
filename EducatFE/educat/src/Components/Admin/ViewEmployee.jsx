import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewEmployee.css'; 
import api from './api';

const ViewEmployees = () => {
    const [employees, setEmployees] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchEmployees();
        const intervalId = setInterval(fetchEmployees, 60000);

        return () => clearInterval(intervalId);
    }, [location]);

    const fetchEmployees = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get('http://localhost:8090/admin/viewEmployee');
            setEmployees(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchEmployees(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching employees:', error);
            }
        }
    };

    const handleActivate = async (employeeId) => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/activateEmployee/${employeeId}`);
            alert("Employee activated successfully");
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchEmployees(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error activating employee:', error);
            }
        }
    };

    const handleInactivate = async (employeeId) => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/inactivateEmployee/${employeeId}`);
            alert("Employee inactivated successfully");
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchEmployees(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error inactivating employee:', error);
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
        <div className="view-employees-container">
            <AdminSidebar />
            <div className="employees-table-container">
                <h2>Employee List</h2>
                <table className="employees-table">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Title</th>
                            <th>Email</th>
                            <th>Department</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {employees.map(employee => (
                            <tr key={employee.employeeId}>
                                <td>{employee.fname}</td>
                                <td>{employee.lname}</td>
                                <td>{employee.title}</td>
                                <td>{employee.email}</td>
                                <td>{employee.departments.name}</td>
                                <td>
                                    <button onClick={() => navigate(`/editEmployee/${employee.employeeId}`)}><i className="fa fa-edit"></i></button>
                                    <button onClick={() => handleActivate(employee.employeeId)}><i className="fa fa-check-circle"></i></button>
                                    <button onClick={() => handleInactivate(employee.employeeId)}><i className="fa fa-times-circle"></i></button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewEmployees;
