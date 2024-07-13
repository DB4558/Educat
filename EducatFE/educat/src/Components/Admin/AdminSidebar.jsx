import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminSidebar.css'; 
import { useEmail } from '../../EmailContext';
import api from './api';

const AdminSidebar = () => {
    const navigate = useNavigate();
    const {email}=useEmail();

    const handleNavigation = (path) => {
        navigate(path);
    };

    const handleLogout= async()=>{
        try{
        const response = await api.get(`/logout/${email}`);
        navigate('/');
    } catch (err) {
        if (err.response && err.response.status === 401) {
            console.error('Failed to Logout');
        }
    }

    };

    return (
        <div className="sidebar">
            <button onClick={() => handleNavigation('/AdminHome')}>Home</button>
            <button onClick={() => handleNavigation('/admin/courses')}>Courses</button>
            <button onClick={() => handleNavigation('/addGrade')}>Grade</button>
            <button onClick={() => handleNavigation('/admin/domains')}>Domains</button>
            <button onClick={() => handleNavigation('/admin/specialisations')}>Specialisations</button>
            <button onClick={() => handleNavigation('/admin/students')}>Students</button>
            <button onClick={() => handleNavigation('/addDepartment')}>Departments</button>
            <button onClick={() => handleNavigation('/addEmployee')}>Employees</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default AdminSidebar;

