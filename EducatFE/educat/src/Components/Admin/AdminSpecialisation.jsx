import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useEmail } from '../../EmailContext';
import './AdminDomain.css';
import { useNavigate,useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api';

const AdminSpecialisation = () => {
    const { email } = useEmail(); 
    const location=useLocation();
    const [adminData, setAdminData] = useState({ fname: '', lname: '', email: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();

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

    useEffect(() => {
        const fetchAdminData = async () => {
            try {
            
                const response = await api.get(`/viewAdmin/${email}`);
                setAdminData(response.data);
            } catch (err) {
                if (err.response && err.response.status === 401) {
                   
                        setError('Session expired. Please log in again.');
                        alert('Session expired. Please log in again.');
                        navigate('/');
                    
                } else {
                    setError('Failed to fetch admin data.');
                }
            }
        };

        fetchAdminData();
    }, [location]);

    return (
        <>
            <div className="main-content">
                <AdminSidebar />
                <div className="admin-card">
                    <h2>Admin Details</h2>
                    {error ? <p>{error}</p> : (
                        <div>
                            <p><strong>First Name:</strong> {adminData.fname}</p>
                            <p><strong>Last Name:</strong> {adminData.lname}</p>
                            <p><strong>Email:</strong> {adminData.email}</p>
                        </div>
                    )}
                </div>
                <div className="domain-container">
                    <div className="domain-btn-container">
                        <button className="domain-btn-add" onClick={() => navigate('/addSpecialisation')}>Add Specialisation</button>
                    </div>
                    <div className="domain-btn-container">
                        <button className="domain-btn-view" onClick={() => navigate('/viewSpecialisation')}>View Specialisation</button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default AdminSpecialisation;
