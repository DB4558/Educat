import React from 'react';
import { useNavigate } from 'react-router-dom';
import './EmployeeSidebar.css'; 
import { useEmail } from '../../EmailContext';

import estateapi from './estateapi';

const EmployeeSidebar = () => {
    const navigate = useNavigate();
    const { email } = useEmail();

    const handleNavigation = (path) => {
        navigate(path);
    };

    const handleLogout = async () => {
        try {
            const response = await estateapi.get(`/logout/${email}`);
            navigate('/');
        } catch (err) {
            if (err.response && err.response.status === 401) {
                console.error('Failed to Logout');
            }
        }
    };

    return (
        <div className="sidebar">
            <button onClick={() => handleNavigation('/EstateHome')}>Home</button>
            <button onClick={() => handleNavigation('/addRoom')}>Add Room</button>
            <button onClick={() => handleNavigation('/viewRoom')}>View All Room</button>
            <button onClick={() => handleNavigation('/viewSalary')}>Salary History</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default EmployeeSidebar;
