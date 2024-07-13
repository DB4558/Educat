import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useEmail } from '../../EmailContext';
import './EstateHome.css';
import { useNavigate, useLocation } from 'react-router-dom';
import EmployeeSidebar from './EmployeeSidebar';

import estateapi from './estateapi';

const EstateHome = () => {
    const { email } = useEmail();
    const location = useLocation();
    const [employeeData, setEmployeeData] = useState({ fname: '', lname: '', title: '', email: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEmployeeData = async () => {
            try {
                const response = await estateapi.get(`/viewEmployee/${email}`);
                setEmployeeData(response.data);
            } catch (err) {
                if (err.response && err.response.status === 401) {
                    setError('Session expired. Please log in again.');
                    alert('Session expired. Please log in again.');
                    navigate('/');
                } else {
                    setError('Failed to fetch employee data.');
                }
            }
        };

        fetchEmployeeData();
    }, [email, location]); 

    return (
        <>
        <div className="main-content">
        <EmployeeSidebar/>
        <div className="employee-card">
            <h2>Employee Details</h2>
            {error ? <p>{error}</p> : (
                <div>
                    <p><strong>First Name:</strong> {employeeData.fname}</p>
                    <p><strong>Last Name:</strong> {employeeData.lname}</p>
                    <p><strong>Title:</strong> {employeeData.title}</p>
                    <p><strong>Email:</strong> {employeeData.email}</p>
                </div>
            )}
        </div>
       </div>
       </>
    );
};

export default EstateHome;
