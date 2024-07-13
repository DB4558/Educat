import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewDepartment.css';
import api from './api';

const ViewDepartments = () => {
    const [departments, setDepartments] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchDepartments();
        const intervalId = setInterval(fetchDepartments, 60000); 

        return () => clearInterval(intervalId);
    }, [navigate]);

    const fetchDepartments = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get('http://localhost:8090/admin/viewDepartments');
            setDepartments(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchDepartments();
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching departments:', error);
            }
        }
    };

    const handleDelete = async (departmentId) => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            await api.delete(`http://localhost:8090/admin/deleteDepartment/${departmentId}`);
            alert("Department deleted successfully");
            fetchDepartments(); 
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    handleDelete(departmentId);
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error deleting department:', error);
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
        <div className="view-departments-container">
            <AdminSidebar />
            <div className="departments-table-container">
                <h2>Department List</h2>
                <table className="departments-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Capacity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {departments.map(department => (
                            <tr key={department.departmentId}>
                                <td>{department.name}</td>
                                <td>{department.capacity}</td>
                                <td>
                                    <button onClick={() => navigate(`/editDepartment/${department.departmentId}`)}><i className="fa fa-edit"></i></button>
                                    <button onClick={() => navigate(`/viewEmployeeDept/${department.departmentId}`)}><i className="fa fa-user"></i></button>
                               
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewDepartments;
